package org.abubusoft.foc.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.abubusoft.foc.business.facades.CloudFileFacade;
import org.abubusoft.foc.business.facades.ConsumerFacade;
import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestAPIV1Controller
@RequestMapping(value = "${api.v1.base-url}/public")
public class PublicController {

	private ConsumerFacade consumerFacade;

	protected CloudFileFacade service;

	private UploaderFacade uploaderService;

	@PostMapping(value = "/consumers/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ConsumerWto> consumerInsert(@Valid @RequestBody ConsumerWto value) {
		return ResponseEntity.ok(consumerFacade.save(value));
	}

	@GetMapping("/consumers/new")
	public ResponseEntity<ConsumerWto> create() {
		return ResponseEntity.ok(consumerFacade.create());
	}

	@GetMapping("/preview-files/{fileUUID}")
	public ResponseEntity<ByteArrayResource> downloadFileNoTrace(HttpServletRequest request,
			@PathVariable(value = "fileUUID") String fileUUID) {
		try {
			ResponseEntity<ByteArrayResource> response = getFileInternal(request, fileUUID, false);
			return response;
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping(value = "/ip",produces=MediaType.TEXT_PLAIN_VALUE )
	public ResponseEntity<String> ip(HttpServletRequest request) {
		String ip = extractIp(request);
        
        return ResponseEntity.ok(ip);
	}


	private String extractIp(HttpServletRequest request) {
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
		return ip;
	}

	private ResponseEntity<ByteArrayResource> getFileInternal(HttpServletRequest request, String fileUUID,
			boolean traceIp) {
		Pair<CloudFile, byte[]> file = service.getFile(fileUUID);
		ByteArrayResource resource = new ByteArrayResource(file.getSecond());

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFirst().getFileName());
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.PRAGMA, "no-cache");
		headers.add(HttpHeaders.EXPIRES, "0");

		ResponseEntity<ByteArrayResource> response = ResponseEntity.ok().headers(headers)
				.contentLength(file.getFirst().getContentLength())
				.contentType(MediaType.valueOf(file.getFirst().getMimeType())).body(resource);

		if (traceIp) {
			String ip = extractIp(request);
			service.updateViewStatus(ip, file.getFirst());
		}

		return response;
	}

	@GetMapping("/files/{fileUUID}")
	public ResponseEntity<ByteArrayResource> getFiles(HttpServletRequest request,
			@PathVariable("fileUUID") String fileUUID) {
		try {
			ResponseEntity<ByteArrayResource> response = getFileInternal(request, fileUUID, true);
			return response;
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/uploaders/{id}/logo")
	public ResponseEntity<ByteArrayResource> getUploaderLogo(@PathVariable("id") long uploaderId) {
		byte[] content = uploaderService.getLogoById(uploaderId);
		ByteArrayResource resource = new ByteArrayResource(content);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logo" + uploaderId + ".png");
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.PRAGMA, "no-cache");
		headers.add(HttpHeaders.EXPIRES, "0");

		return ResponseEntity.ok().headers(headers).contentLength(content.length).contentType(MediaType.IMAGE_PNG)
				.body(resource);
	}

	@Autowired
	public void setService(CloudFileFacade service) {
		this.service = service;
	}

	@Autowired
	public void setService(ConsumerFacade service) {
		this.consumerFacade = service;
	}

	@Autowired
	public void setService(UploaderFacade service) {
		this.uploaderService = service;
	}
}
