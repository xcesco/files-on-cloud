package org.abubusoft.foc.web.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;

public class CloudFileWto {
	
	protected String consumerDisplayName;
	
	protected long consumerId;

	protected String consumerUsername;

	protected long contentLength;

	@ApiModelProperty(required = true, dataType = "org.joda.time.LocalDate")
	protected LocalDateTime creationTime;

	protected String fileName;

	protected String mimeType;

	protected boolean notified;

	protected Set<String> tags = new LinkedHashSet<>();

	protected String uploaderDisplayName;

	protected long uploaderId;

	protected String uploaderUsername;

	protected String uuid;

	protected String viewIp;

	@ApiModelProperty(required = true, dataType = "org.joda.time.LocalDate")
	protected LocalDateTime viewTime;

	public String getConsumerDisplayName() {
		return consumerDisplayName;
	}

	public String getConsumerUsername() {
		return consumerUsername;
	}

	public long getContentLength() {
		return contentLength;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public Set<String> getTags() {
		return tags;
	}

	public String getUploaderDisplayName() {
		return uploaderDisplayName;
	}

	public long getUploaderId() {
		return uploaderId;
	}

	public void setUploaderId(long uploaderId) {
		this.uploaderId = uploaderId;
	}

	public String getUploaderUsername() {
		return uploaderUsername;
	}

	public String getUuid() {
		return uuid;
	}

	public String getViewIp() {
		return viewIp;
	}
	
	public LocalDateTime getViewTime() {
		return viewTime;
	}
	
	public boolean isNotified() {
		return notified;
	}
	
	public void setConsumerDisplayName(String consumerDisplayName) {
		this.consumerDisplayName = consumerDisplayName;
	}
	
	public long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}

	public void setConsumerUsername(String consumerUsername) {
		this.consumerUsername = consumerUsername;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public void setUploaderDisplayName(String uploaderDisplayName) {
		this.uploaderDisplayName = uploaderDisplayName;
	}
	
	public void setUploaderUsername(String uploaderUsername) {
		this.uploaderUsername = uploaderUsername;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	}

	public void setViewTime(LocalDateTime viewTime) {
		this.viewTime = viewTime;
	}

}
