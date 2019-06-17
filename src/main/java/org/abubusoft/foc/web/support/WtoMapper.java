package org.abubusoft.foc.web.support;

import java.util.List;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WtoMapper {

	WtoMapper INSTANCE = Mappers.getMapper(WtoMapper.class);

	List<AdminWto> convertAdminListToWto(Iterable<Administrator> iterable);

	Administrator convertAdminToDto(AdminWto wto);

	AdminWto convertAdminToWto(Administrator result);	
	
	List<CloudFileWto> convertCloudFileListToWto(Iterable<CloudFile> iterable);

	List<ConsumerWto> convertConsumerListToWto(Iterable<Consumer> findAll);

	Consumer convertConsumerToDto(ConsumerWto value);	
	
	ConsumerWto convertConsumerToWto(Consumer value);
	
	default CloudFileWto convertToFileWto(CloudFile value) {
		CloudFileWto result=new CloudFileWto();
		
		result.setUuid(value.getUuid());
		result.setCreationTime(value.getCreatedDateTime());
		result.setNotified(value.isNotified());
		
		result.setViewIp(value.getViewIp());
		result.setViewTime(value.getViewTime());
		
		result.getTags().addAll(value.getTags());
		
		result.setFileName(value.getFileName());			
		result.setMimeType(value.getMimeType());
		result.setContentLength(value.getContentLength());
		
		result.setConsumerId(value.getConsumer().getId());
		result.setConsumerUsername(value.getConsumer().getUsername());
		result.setConsumerDisplayName(value.getConsumer().getDisplayName());
		
		result.setUploaderId(value.getUploader().getId());
		result.setUploaderDisplayName(value.getUploader().getDisplayName());
		result.setUploaderUsername(value.getUploader().getUsername());
		
		return result;
    }
	 
	default CloudFile convertToFileDto(CloudFileWto value) {
		CloudFile result=new CloudFile();
		
		result.setUuid(value.getUuid());
		//result.setCreationTime(value.getCreatedDateTime());
		result.setNotified(value.isNotified());
		
		result.setViewIp(value.getViewIp());
		result.setViewTime(value.getViewTime());
		
		result.getTags().addAll(value.getTags());
		
		result.setFileName(value.getFileName());			
		result.setMimeType(value.getMimeType());
		result.setContentLength(value.getContentLength());
		
		//result.setConsumerId(value.getConsumer().getId());
		//result.setConsumerUsername(value.getConsumer().getUsername());
		//result.setConsumerDisplayName(value.getConsumer().getDisplayName());
		
		//result.setUploaderId(value.getUploader().getId());
		//result.setUploaderDisplayName(value.getUploader().getDisplayName());
		//result.setUploaderUsername(value.getUploader().getUsername());
		
		return result;
    }

	List<UploaderWto> convertUploaderListToDto(Iterable<Uploader> iterable);

	List<UploaderWto> convertUploaderListToWto(Iterable<Uploader> iterable);

	Uploader convertUploaderToDto(UploaderWto wto);

	UploaderWto convertUploaderToWto(Uploader result);
	
}