package org.abubusoft.foc;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, 
	scanBasePackages = {"org.abubusoft.foc"})
@EnableJpaAuditing
@EnableJpaRepositories("org.abubusoft.foc.repositories")
public class WebApplicationStartup extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplicationStartup.class);
	}
	
	@PostConstruct
    void started() {
		//http://tutorials.jenkov.com/java-date-time/java-util-timezone.html
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
    }

	public static void main(String[] args) {
		SpringApplication.run(WebApplicationStartup.class, args);
	}

}