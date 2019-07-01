package org.abubusoft.foc.repositories.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "foc_uploaders")
public class Uploader extends User {
	
	@Lob
    @Basic(fetch = FetchType.LAZY)
	private byte[] image;


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}
}
