package org.abubusoft.foc.web.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.business.facades.CloudFileFacade;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.CloudFileInfoWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.security.AuthUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@RestAPIV1Controller
@Secured({AuthUserRole.ROLE_ADMINISTRATOR, AuthUserRole.ROLE_UPLOADER})
@RequestMapping(value="${api.v1.base-url}/secured", produces = "application/json; charset=utf-8")
public class CloudFileController {
	protected CloudFileFacade service;
	
	@GetMapping("/preview-files/{fileUUID}")
	public ResponseEntity<ByteArrayResource> downloadFileForAdmin(
			@PathVariable(value = "fileUUID") String fileUUID) {
		Pair<CloudFile, byte[]> file = service.getFile(fileUUID);
		ByteArrayResource resource = new ByteArrayResource(file.getSecond());
				
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getFirst().getFileName());
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
		
		 return ResponseEntity.ok()
		            .headers(headers)		            
		            .contentLength(file.getFirst().getContentLength())
		            .contentType(MediaType.valueOf(file.getFirst().getMimeType()))
		            .body(resource);
	}
			
	@GetMapping("/files/new")
	public ResponseEntity<CloudFileWto> fileCreate() {		
		return ResponseEntity.ok(service.create());
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/new")
	public ResponseEntity<CloudFileInfoWto> fileCreate(@PathVariable(value = "uploaderId") long uploaderId,
			@PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.create(uploaderId, consumerId));
	}
	
	@DeleteMapping("/files/{fileUUID}")
	public ResponseEntity<Boolean> fileDeleteByUUID(@PathVariable("fileUUID") String fileUUID) {
		return ResponseEntity.ok(service.deleteByUUID(fileUUID));
	}
	
	@GetMapping("/files/{fileUUID}/notification/send")
	public ResponseEntity<Boolean> fileSendNotificationByUUID(@PathVariable("fileUUID") String fileUUID) {
		return ResponseEntity.ok(service.sendNotificationByUUID(fileUUID));
	}
	
	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/{fileId}")
	public ResponseEntity<CloudFileInfoWto> fileFindById(@PathVariable(value = "uploaderId") long uploaderId,
			@PathVariable(value = "consumerId") long consumerId, @PathVariable("fileId") long fileId) {
		return ResponseEntity.ok(service.findByUploaderAndConsumerAndFile(uploaderId, consumerId, fileId));
	}
	
	@GetMapping("/consumers/{consumerId}/uploaders/{uploaderId}/files")
	public ResponseEntity<List<CloudFileWto>> findFiles(
			@PathVariable(value="consumerId") long consumerId,
			@PathVariable(value="uploaderId") long uploaderId, 
			@RequestParam(value="tags") Set<String> tags) {
		return ResponseEntity.ok(service.findByConsumerAndUploader(consumerId, uploaderId, tags));
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files")
	public ResponseEntity<List<CloudFileWto>> findFilesByUploaderAndConsumer(
			@PathVariable(value = "uploaderId") long uploaderId, @PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.findByUploaderAndConsumer(uploaderId, consumerId));
	}
	
	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/tags")
	public ResponseEntity<List<CloudFileTag>> findTagsByUploaderAndConsumer(
			@PathVariable(value = "uploaderId") long uploaderId, @PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.findTagsByUploaderAndConsumer(uploaderId, consumerId));
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<CloudFileWto>> getFiles() {
		return ResponseEntity.ok(service.findAll());	

	}
	
	@Autowired
	public void setService(CloudFileFacade service) {
		this.service = service;
	}
	
	@PostMapping(value = "/uploaders/{uploaderId}/files", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Boolean> uploadFile(@PathVariable("uploaderId") long uploaderId,
			@RequestPart(name="codiceFiscale") String codiceFiscale,
			@RequestPart(name="email", required=false) String email,
			@RequestPart(name="displayName", required=false) String displayName,
			@RequestPart(name="hashtag", required=false) String hashtag,
			@RequestPart(name="username", required=false) String username,
			@RequestPart(name="file") MultipartFile multipartFile) throws IOException {
		ConsumerWto consumer=new ConsumerWto();
		consumer.setCodiceFiscale(codiceFiscale);
		consumer.setDisplayName(displayName);
		consumer.setEmail(email);
		consumer.setUsername(username);
		
		Set<String> hashTagSet=null;
		if (StringUtils.hasText(hashtag)) {
			hashTagSet=new HashSet<>();			
			List<String> tagValues = Arrays.asList(hashtag.split(","));
			
			for (String item: tagValues) {
				if (StringUtils.hasText(item)) {
					hashTagSet.add(item);
				}
			}	
		}		
		
		CloudFileInfoWto info=new CloudFileInfoWto();
		info.setConsumer(consumer);
		info.setTags(hashTagSet);
		service.save(uploaderId, info, multipartFile);
		
		return ResponseEntity.ok(true);
	}


}
