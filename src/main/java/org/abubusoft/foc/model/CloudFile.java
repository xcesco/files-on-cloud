/**
 * 
 */
package org.abubusoft.foc.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xcesco
 *
 */
@Entity
@Table(name = "foc_files")
public class CloudFile extends AbstractEntity {
	
	@Column(nullable = false)
	protected String fileName;
	
	protected String hashTags;
	
	@Column(nullable = false)
	protected String mimeType;

	protected boolean notified;

	@Column(nullable = false)
	protected String storageName;

	protected LocalDate uploadTime;

	protected String viewIp;

	protected LocalDate viewTime;

	public String getFileName() {
		return fileName;
	}

	public String getHashTags() {
		return hashTags;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getStorageName() {
		return storageName;
	}

	public LocalDate getUploadTime() {
		return uploadTime;
	}

	public String getViewIp() {
		return viewIp;
	}

	public LocalDate getViewTime() {
		return viewTime;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setHashTags(String hashTags) {
		this.hashTags = hashTags;
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
	public void setUploadTime(LocalDate uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	}

	public void setViewTime(LocalDate viewTime) {
		this.viewTime = viewTime;
	}

}
