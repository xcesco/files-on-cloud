package org.abubusoft.foc.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "foc_uploaders")
public class Uploader extends User {
	
//	@OneToMany
//	protected List<CloudFile> files;

	@Lob
    @Basic(fetch = FetchType.LAZY)
	private byte[] image;

//	public List<CloudFile> getFiles() {
//		return files;
//	}

	public byte[] getImage() {
		return image;
	}

//	public void setFiles(List<CloudFile> files) {
//		this.files = files;
//	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
