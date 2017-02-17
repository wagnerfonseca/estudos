package com.curso.brewer.config.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
	/*
	 * Esse filtro fica responsavel por executar os encoding 
	 * Para resolver os problemas de acentuação
	 * */
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		
		characterEncodingFilter.setInitParameter("encoding", "UTF-8"); // usar UTF-8
		characterEncodingFilter.setInitParameter("forceEncoding", "true"); // Força o encoding
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*"); // para todas as URLS
	}

}
