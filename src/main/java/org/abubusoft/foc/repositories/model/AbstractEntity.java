package org.abubusoft.foc.repositories.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@DynamicUpdate
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	//@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	//@JsonSerialize(using = LocalDateTimeSerializer.class)
	@CreatedDate
	@Column(nullable = false)
	protected LocalDateTime createdDateTime;
	
	
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDate) {
		this.createdDateTime = createdDate;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDate) {
		this.modifiedDateTime = modifiedDate;
	}


	@LastModifiedDate
	//@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	//@JsonSerialize(using = LocalDateTimeSerializer.class)  
	protected LocalDateTime modifiedDateTime;
	
	
}
