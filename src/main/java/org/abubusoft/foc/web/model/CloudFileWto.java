package org.abubusoft.foc.web.model;

import java.time.LocalDateTime;

import org.abubusoft.foc.web.support.LocalDateTimeDeserializer;
import org.abubusoft.foc.web.support.LocalDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CloudFileWto extends CloudFileInfoWto {

	protected long contentLength;

	protected String fileName;

	protected String mimeType;

	protected boolean notified;

	protected UploaderWto uploader;

	protected String uuid;

	protected String viewIp;

	@JsonFormat(timezone = "Europe/Rome")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	protected LocalDateTime viewTime;

	public long getContentLength() {
		return contentLength;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public UploaderWto getUploader() {
		return uploader;
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

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
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

	public void setUploader(UploaderWto uploader) {
		this.uploader = uploader;
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
