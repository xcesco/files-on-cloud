package org.abubusoft.foc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "foc_consumers")
public class Consumer extends AbstractActor {
	/**
	 * 4 caratteri alfanumerici
	 */
	@Column(nullable = false,unique = true)
	String username;
	
	@Column(nullable = false,unique = true)
	String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	byte[] logo;
	
	@OneToMany
	@JoinColumn(name="CONSUMER_ID", nullable = false)
	protected List<CloudFile> files;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CloudFile> getFiles() {
		return files;
	}

	public void setFiles(List<CloudFile> files) {
		this.files = files;
	}
}
