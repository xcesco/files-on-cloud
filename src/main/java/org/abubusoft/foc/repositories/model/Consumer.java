package org.abubusoft.foc.repositories.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "foc_consumers")
public class Consumer extends User {

	@Length(max = 16, min=16)
	@Column(nullable = false, unique = true, updatable=false)
	protected String codiceFiscale;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

}
