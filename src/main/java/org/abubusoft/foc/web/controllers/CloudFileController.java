package org.abubusoft.foc.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.business.facades.CloudFileFacade;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.CloudFileInfoWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.security.JwtUser;
import org.abubusoft.foc.web.security.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

@RestAPIV1Controller
//@Secured({UserRoles.ROLE_ADMINISTRATOR_VALUE, UserRoles.ROLE_UPLOADER_VALUE})
@RequestMapping(value = "${api.v1.base-url}/secured", produces = "application/json; charset=utf-8")
public class CloudFileController {
	protected CloudFileFacade service;

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_UPLOADER')")
	@GetMapping("/files/new")
	public ResponseEntity<CloudFileWto> fileCreate() {
		return ResponseEntity.ok(service.create());
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/new")
	public ResponseEntity<CloudFileInfoWto> fileCreate(@PathVariable(value = "uploaderId") long uploaderId,
			@PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.create(uploaderId, consumerId));
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_UPLOADER')")
	@DeleteMapping("/files/{fileUUID}")
	public ResponseEntity<Boolean> fileDeleteByUUID(@PathVariable("fileUUID") String fileUUID) {
		return ResponseEntity.ok(service.deleteByUUID(fileUUID));
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_UPLOADER')")
	@GetMapping("/files/{fileUUID}/notification/send")
	public ResponseEntity<Boolean> fileSendNotificationByUUID(@PathVariable("fileUUID") String fileUUID) {
		return ResponseEntity.ok(service.sendNotificationByUUID(fileUUID));
	}

	// -- ok
	//@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_UPLOADER')")
	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/{fileId}")
	public ResponseEntity<CloudFileInfoWto> fileFindById(@PathVariable(value = "uploaderId") long uploaderId,
			@PathVariable(value = "consumerId") long consumerId, @PathVariable("fileId") long fileId) {
		return ResponseEntity.ok(service.findByUploaderAndConsumerAndFile(uploaderId, consumerId, fileId));
	}
	
	//--ok
	@ApiOperation(notes = "Utilizzato sia da uploader che da consumers, recupera i file condivisi tra un consumer ed un uploader, eventualmente filtrando mediante tags.", value = "")
	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files")
	public ResponseEntity<List<CloudFileWto>> findFiles(
			@PathVariable(value = "consumerId") long consumerId,
			@PathVariable(value = "uploaderId") long uploaderId, 
			@RequestParam(value = "tags", required = false) Set<String> tags, @AuthenticationPrincipal final JwtUser user) {
		
		if (user.isConsumer() && user.getId()!=consumerId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		if (user.isUploader() && user.getId()!=uploaderId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.ok(service.findByConsumerAndUploader(consumerId, uploaderId, tags));
	}

/*	//
	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files")
	public ResponseEntity<List<CloudFileWto>> findFilesByUploaderAndConsumer(
			@PathVariable(value = "uploaderId") long uploaderId, @PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.findByUploaderAndConsumer(uploaderId, consumerId));
	}*/

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/tags")
	public ResponseEntity<List<CloudFileTag>> findTagsByUploaderAndConsumer(
			@PathVariable(value = "uploaderId") long uploaderId, @PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.findTagsByUploaderAndConsumer(uploaderId, consumerId));
	}

	@ApiOperation(notes = "Se utilizzato da consumer, necessita del consumerId e dell'uploaderId. Admin e Uploader non sono limitati.", value = "")
	// @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_UPLOADER')")
	@GetMapping("/files")
	public ResponseEntity<List<CloudFileWto>> getFiles() {
		return ResponseEntity.ok(service.findAll());

	}

	@Autowired
	public void setService(CloudFileFacade service) {
		this.service = service;
	}

	@Secured({ UserRoles.ROLE_UPLOADER_VALUE })
	@PostMapping(value = "/files", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Boolean> uploadFile(@AuthenticationPrincipal final JwtUser user,
			@RequestPart(name = "codiceFiscale") String codiceFiscale,
			@RequestPart(name = "email", required = false) String email,
			@RequestPart(name = "displayName", required = false) String displayName,
			@RequestPart(name = "hashtag", required = false) String hashtag,
			@RequestPart(name = "username", required = false) String username,
			@RequestPart(name = "file") MultipartFile multipartFile) throws IOException {
		ConsumerWto consumer = new ConsumerWto();
		consumer.setCodiceFiscale(codiceFiscale);
		consumer.setDisplayName(displayName);
		consumer.setEmail(email);
		consumer.setUsername(username);

		service.save(user.getId(), consumer, hashtag, multipartFile);

		return ResponseEntity.ok(true);
	}

}
