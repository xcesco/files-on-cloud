package org.abubusoft.foc.business.support;

import javax.validation.ConstraintValidatorContext;

public class CodiceFiscaleValidator implements ConstraintValidator<CodiceFiscale, CharSequence> {

	@Override
	public void initialize(CodiceFiscale constraintAnnotation) {
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		FiscalCodeValidator.
		return matcher.matches();
	}
}