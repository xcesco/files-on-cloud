package org.abubusoft.foc.repositories.support;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.sql.Timestamp;
 
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
     
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
    	if (localDateTime==null) {
    		return null;
    	}
    	LocalDateTime oldDateTime = localDateTime;
        ZoneId oldZone = ZoneId.of("Europe/Rome");

        ZoneId newZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = oldDateTime.atZone(oldZone)
                                               .withZoneSameInstant(newZone)
                                               .toLocalDateTime();
    	
    	Timestamp result=Timestamp.valueOf(newDateTime);
        return result;
    }
 
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    	if (sqlTimestamp==null) {
    		return null;
    	}
    	LocalDateTime newDateTime=sqlTimestamp.toLocalDateTime();
//        LocalDateTime oldDateTime = sqlTimestamp.toLocalDateTime();
//        ZoneId oldZone = ZoneId.of("UTC");
//
//        ZoneId newZone = ZoneId.of("Europe/Rome");
//        LocalDateTime newDateTime = oldDateTime.atZone(oldZone)
//                                               .withZoneSameInstant(newZone)
//                                               .toLocalDateTime();
        return newDateTime;
    }
}