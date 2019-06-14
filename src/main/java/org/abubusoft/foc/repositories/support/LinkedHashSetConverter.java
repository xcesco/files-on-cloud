package org.abubusoft.foc.repositories.support;

import java.io.IOException;
import java.util.Set;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LinkedHashSetConverter implements AttributeConverter<Set<String>, String> {
	
	ObjectMapper objectMapper=new ObjectMapper();
 
    @Override
    public String convertToDatabaseColumn(Set<String> customerInfo) {
 
        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            //logger.error("JSON writing error", e);
        }
 
        return customerInfoJson;
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public Set<String> convertToEntityAttribute(String customerInfoJSON) {
 
    	Set<String> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(customerInfoJSON, Set.class);
        } catch (final IOException e) {
           // logger.error("JSON reading error", e);
        }
 
        return customerInfo;
    }
 
}