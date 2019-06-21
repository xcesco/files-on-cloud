package org.abubusoft.foc.web.controllers;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.services.ConsumerServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestAPIV1Controller
//@RestController
public class ConsumerController {

	private ConsumerServiceFacade service;

	@Autowired
	public void setService(ConsumerServiceFacade service) {
		this.service = service;
	}

	/*
	@PatchMapping("/consumers/{consumerId}")
	public ResponseEntity<ConsumerWto> modify(@PathVariable("consumerId") long consumerId, @RequestBody @Valid ConsumerWto value) {
		value.setId(consumerId);
		return ResponseEntity.ok(service.updateConsumerById(value));
	}*/

	@GetMapping("/consumers/{consumerId}/uploaders-summary")
	public ResponseEntity<List<UploaderDetailSummary>> consumerGetUploaderWithFiles(
			@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.findUploadersWithFileByConsumerId(consumerId));
	}

	@GetMapping("/consumers/{consumerId}/uploaders/{uploaderId}/files")
	public ResponseEntity<List<CloudFileWto>> consumerGetFiles(
			@PathVariable(value="consumerId") long consumerId,
			@PathVariable(value="uploaderId") long uploaderId, 
			@RequestParam(value="tags") Set<String> tags) {
		return ResponseEntity.ok(service.findFilesByConsumerAndUploader(consumerId, uploaderId, tags));
	}
	
	@GetMapping("/consumers/{consumerId}/change-password")
	public ResponseEntity<String> modify(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.consumerGetChangePasswordUrlById(consumerId));
	}
	
	@GetMapping("/user/ip")
	public ResponseEntity<String> ip(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getRemoteAddr();  
        }
        
        return ResponseEntity.ok(ip);
	}
	
	@GetMapping("/cloud/{fileUUID}")
	public ResponseEntity<ByteArrayResource> consumerGetFiles(HttpServletRequest request, @PathVariable("fileUUID") String fileUUID) {
		ResponseEntity.ok();
		
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
}
