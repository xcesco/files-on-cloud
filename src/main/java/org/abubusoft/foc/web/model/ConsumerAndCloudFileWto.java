package org.abubusoft.foc.web.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConsumerAndCloudFileWto {
	protected String consumerCodiceFiscale;

	public String getConsumerCodiceFiscale() {
		return consumerCodiceFiscale;
	}

	public void setConsumerCodiceFiscale(String consumerCodiceFiscale) {
		this.consumerCodiceFiscale = consumerCodiceFiscale;
	}

	public String getConsumerDisplayName() {
		return consumerDisplayName;
	}

	public void setConsumerDisplayName(String consumerDisplayName) {
		this.consumerDisplayName = consumerDisplayName;
	}

	public String getConsumerUsername() {
		return consumerUsername;
	}

	public void setConsumerUsername(String consumerUsername) {
		this.consumerUsername = consumerUsername;
	}

	public String getConsumerEmail() {
		return consumerEmail;
	}

	public void setConsumerEmail(String consumerEmail) {
		this.consumerEmail = consumerEmail;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	protected String consumerDisplayName;

	protected String consumerUsername;
	
	protected String consumerEmail;

	protected String fileName;
	
	protected String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	protected Set<String> tags = new LinkedHashSet<>();

}
