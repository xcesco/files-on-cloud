package org.abubusoft.foc.web.annotation;

/**
 * Rappresenta un utente profilato, quindi un 
 * 
 * <beans:bean id="customMetadataSource" class="it.insiel.stt.interrogazioni.web.security.CustomMetadataSource">
 * 
 * @author Francesco Benincasa (908099)
 *
 */
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Inherited
@java.lang.annotation.Documented
public @interface RoleProfiledUser {
	
	//Secured secured() default @Secured(JwtSecurity.ROLE_AUTHENTICATED_USER);
	
	//PreAuthorize secured() default @PreAuthorize("hasRole('ROLE_AUTHENTICATED_USER')");
	
}