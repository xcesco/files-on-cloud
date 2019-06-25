package org.abubusoft.foc.business.support;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodiceFiscaleValidator.class)
public @interface CodiceFiscale {
	String message() default "User name must contain all letters";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}