/**
 * 
 */
package org.abubusoft.foc.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.abubusoft.foc.repositories.support.LinkedHashSetConverter;

/**
 * @author xcesco
 *
 */
@Entity
@Table(name = "foc_files")
public class CloudFile extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONSUMER_ID", nullable = false, insertable=true, updatable=false)
	protected Consumer consumer;
	
	@Column(nullable = false)
	protected String fileName;

	@Column(nullable = false)
	protected String mimeType;

	protected boolean notified;

	@Column(nullable = false)
	protected String storageName;

	@Convert(converter = LinkedHashSetConverter.class)
	protected Set<String> tags = new LinkedHashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPLOADER_ID", nullable = false, insertable=true, updatable=false)
	protected Uploader uploader;

	@Column(insertable=true, updatable=false)
	protected String uuid;

	protected String viewIp;

	protected LocalDateTime viewTime;

	public Consumer getConsumer() {
		return consumer;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getStorageName() {
		return storageName;
	}

	public Set<String> getTags() {
		return tags;
	}

	public Uploader getUploader() {
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

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
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

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public void setUploader(Uploader uploader) {
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
