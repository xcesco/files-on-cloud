/**
 * 
 */
package org.abubusoft.foc.repositories.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	protected long contentLength;

	@Column(nullable = false)
	protected String fileName;
	
	@Column(nullable = false)
	protected String mimeType;

	@Column(nullable = false)
	protected String storageName;

	@ElementCollection
	@CollectionTable(name = "foc_file_tags", joinColumns = @JoinColumn(name = "file_id"))	
	@Column(name = "tag") 
	protected Set<String> tags = new LinkedHashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPLOADER_ID", nullable = false, insertable=true, updatable=false)
	protected Uploader uploader;

	@Column(insertable=true, updatable=false)
	protected String uuid;

	protected boolean viewed;
	
	protected String viewIp;
 
	protected LocalDateTime viewTime;


	public LocalDateTime getViewTime() {
		return viewTime;
	}

	public void setViewTime(LocalDateTime viewTime) {
		this.viewTime = viewTime;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public long getContentLength() {
		return contentLength;
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

	public boolean isViewed() {
		return viewed;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
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

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	}

}
