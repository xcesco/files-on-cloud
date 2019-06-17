package org.abubusoft.foc.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.transaction.Transactional;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(value="${api.v1.base-url}", produces = "application/json; charset=utf-8")
@Transactional
//@CrossOrigin(origins = "http://localhost:4200")
public @interface RestAPIV1Controller {
	@AliasFor(annotation = Component.class)
	String value() default "";
}