package org.abubusoft.foc.repositories.model;

public class AdminReportItem {
	
	long consumerCount;
	
	long fileCount;

	String uploaderDisplayName;

	long uploaderId;
	

	public AdminReportItem() {
		
	}

	public AdminReportItem(Uploader item) {
		uploaderId=item.getId();
		uploaderDisplayName=item.getDisplayName();
	}

	public long getConsumerCount() {
		return consumerCount;
	}

	public long getFileCount() {
		return fileCount;
	}

	public String getUploaderDisplayName() {
		return uploaderDisplayName;
	}

	public long getUploaderId() {
		return uploaderId;
	}

	public void setConsumerCount(long consumerCount) {
		this.consumerCount = consumerCount;
	}

	public void setFileCount(long fileCount) {
		this.fileCount = fileCount;
	}
	
	public void setUploaderDisplayName(String uploaderDisplayName) {
		this.uploaderDisplayName = uploaderDisplayName;
	}
	
	public void setUploaderId(long uploaderId) {
		this.uploaderId = uploaderId;
	}
}

