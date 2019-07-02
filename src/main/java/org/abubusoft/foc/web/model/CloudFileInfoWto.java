package org.abubusoft.foc.web.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.abubusoft.foc.web.support.LocalDateTimeDeserializer;
import org.abubusoft.foc.web.support.LocalDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CloudFileInfoWto {
	
	protected ConsumerWto consumer;

	// @ApiModelProperty(required = true, dataType = "org.joda.time.LocalDate")
	//@JsonFormat(timezone="Europe/Rome")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	protected LocalDateTime creationTime;

	protected Set<String> tags = new LinkedHashSet<>();

	public ConsumerWto getConsumer() {
		return consumer;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setConsumer(ConsumerWto consumer) {
		this.consumer = consumer;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

}
