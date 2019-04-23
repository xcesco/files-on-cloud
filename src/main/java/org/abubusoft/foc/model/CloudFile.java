/**
 * 
 */
package org.abubusoft.foc.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author xcesco
 *
 */
@Entity
@Table(name = "foc_files", uniqueConstraints = { @UniqueConstraint(columnNames = { "consumer_id", "uploader_id" }) })
public class CloudFile extends AbstractEntity {

	@Column(nullable = false)
	protected String fileName;

	public LocalDate getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDate uploadTime) {
		this.uploadTime = uploadTime;
	}

	public LocalDate getViewTime() {
		return viewTime;
	}

	public void setViewTime(LocalDate viewTime) {
		this.viewTime = viewTime;
	}

	public String getViewIp() {
		return viewIp;
	}

	public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	}

	public String getHashTags() {
		return hashTags;
	}

	public void setHashTags(String hashTags) {
		this.hashTags = hashTags;
	}

	protected LocalDate uploadTime;
	protected LocalDate viewTime;
	protected String viewIp;
	protected String hashTags;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
