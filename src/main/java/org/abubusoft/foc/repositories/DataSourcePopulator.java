package org.abubusoft.foc.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
public class DataSourcePopulator {
	@Bean
	public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
	    Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
	    factory.setResources(new Resource[]{new ClassPathResource("db-populators/administrators.json"),
	    		new ClassPathResource("db-populators/uploaders.json"),
	    		new ClassPathResource("db-populators/consumers.json")});
	    return factory;
	}
}

