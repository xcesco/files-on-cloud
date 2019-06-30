package org.abubusoft.foc.web;

import org.abubusoft.foc.web.security.JwtAuthenticationEntryPoint;
import org.abubusoft.foc.web.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer  {

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//@formatter:off
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/")
				.resourceChain(true)
				.addResolver(new AngularResourceResolver());
		//@formatter:on
			
	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		// builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		return builder;
	}

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
    	http
    	 	.cors().disable()
    	 	.csrf().disable()
    		.sessionManagement()
    		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)    		
    		.and()
    		.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
    		.exceptionHandling()
    			.authenticationEntryPoint(unauthorizedHandler)
    			.and()
    		.authorizeRequests()
    		//	parte angular
    			.antMatchers("/public/**")
    				.permitAll()
    	
    			// configurazione interfaccia swagger
    			.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
	    		.permitAll()
	    		
	    		// servizi WEB public
	            .antMatchers("/api/v1/public/**")
	            .permitAll()         	            		       
	                
	    		// servizi web secured	    		       
	            .antMatchers("/api/v1/secured/**")
	            .authenticated();
    	
//    	http
//        .csrf().disable()
//        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//        .cors().and()                
        
        
    	//@formatter:on

//        .antMatchers(
//                //HttpMethod.GET,
//                "/",
//                "/*.html",
//                "/favicon.ico",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js"
//        ).permitAll()
//        .antMatchers("/api/v1/**").permitAll()                
//        .anyRequest().authenticated();

//        httpSecurity
//                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                // non abbiamo bisogno di una sessione
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .cors().and()                   
//                .authorizeRequests()
//                .antMatchers(
//                        //HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"
//                ).permitAll()
//                .antMatchers("/api/v1/**").permitAll()                
//                .anyRequest().authenticated();
		// Filtro Custom JWT
//        httpSecurity;	
	}

}