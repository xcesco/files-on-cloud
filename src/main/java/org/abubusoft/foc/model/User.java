package org.abubusoft.foc.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "foc_users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class User extends AbstractEntity {
	@Column(nullable = false)
	protected String displayName;

	@Column(nullable = false, unique = true)
	protected String email;

	/**
	 * L'email con il quale viene effettuato il login. Per disaccoppiare l'email di
	 * ricezione dalle credenziali di accesso si è scelto di lasciare username ed
	 * email separati.
	 */
	@Column(nullable = false, unique = true, updatable = false)
	protected String username;

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
