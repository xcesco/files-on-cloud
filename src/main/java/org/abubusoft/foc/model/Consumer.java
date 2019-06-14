package org.abubusoft.foc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "foc_consumers")
public class Consumer extends User {

	@Column(nullable = false, unique = true, updatable=false)
	protected String codiceFiscale;

//	@OneToMany
//	protected List<CloudFile> files;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

//	public List<CloudFile> getFiles() {
//		return files;
//	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

//	public void setFiles(List<CloudFile> files) {
//		this.files = files;
//	}
}
