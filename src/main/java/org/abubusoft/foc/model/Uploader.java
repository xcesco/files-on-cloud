package org.abubusoft.foc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "foc_uploaders")
public class Uploader extends AbstractActor {

	@OneToMany
	@JoinColumn(name="UPLOADER_ID", nullable = false)
	protected List<CloudFile> files;

	public List<CloudFile> getFiles() {
		return files;
	}

	public void setFiles(List<CloudFile> files) {
		this.files = files;
	}
}
