package org.abubusoft.foc.web.support;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.repositories.model.Administrator;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.Consumer;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.apache.commons.io.IOUtils;
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
		
		result.setViewIp(value.getViewIp());
		result.setViewTime(value.getViewTime());
		
		result.getTags().addAll(value.getTags());
		
		result.setFileName(value.getFileName());			
		result.setMimeType(value.getMimeType());
		result.setContentLength(value.getContentLength());
		
		result.setConsumer(convertConsumerToWto(value.getConsumer()));
		result.setUploader(convertUploaderToWto(value.getUploader()));
		
		return result;
    }
	
	
	/**
	 * Non tiene conto del view ip/time e dell'uploader e consumer
	 * @param value
	 * @param file
	 * @param uploader
	 * @param consumer
	 * @param fileContent
	 * @return
	 */
	default CloudFile convertToFileDto(Uploader uploader, Consumer consumer, LocalDateTime creationTime, Set<String> tags, String fileName, String contentType, long fileSize, InputStream fileContent) {
		CloudFile result=new CloudFile();
		
		result.setConsumer(consumer);
		result.setUploader(uploader);
		
		result.setCreatedDateTime(creationTime);
		
		result.setViewIp(null);
		result.setViewTime(null);
		
		if (tags!=null) {
			result.getTags().addAll(tags);
		}
		
		result.setFileName(fileName);			
		result.setMimeType(contentType);
		result.setContentLength(fileSize);

		byte[] content = new byte[0];
		try {
			content = IOUtils.toByteArray(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		result.setContent(content);
					
		return result;
    }
	 
	default CloudFile convertToFileDto(CloudFileWto value) {
		CloudFile result=new CloudFile();
		
		result.setUuid(value.getUuid());
		
		result.setCreatedDateTime(value.getCreationTime());
		
		result.setViewIp(value.getViewIp());
		result.setViewTime(value.getViewTime());
		
		if (value.getTags()!=null) {
			result.getTags().addAll(value.getTags());
		}
		
		result.setFileName(value.getFileName());			
		result.setMimeType(value.getMimeType());
		result.setContentLength(value.getContentLength());
		
		result.setConsumer(convertConsumerToDto(value.getConsumer()));
		result.setUploader(convertUploaderToDto(value.getUploader()));
		
		return result;
    }

	List<UploaderWto> convertUploaderListToDto(Iterable<Uploader> iterable);

	List<UploaderWto> convertUploaderListToWto(Iterable<Uploader> iterable);

	Uploader convertUploaderToDto(UploaderWto wto);

	UploaderWto convertUploaderToWto(Uploader result);
	
}