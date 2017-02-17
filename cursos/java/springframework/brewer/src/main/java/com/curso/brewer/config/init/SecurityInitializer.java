package com.curso.brewer.config.init;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
	/*
	 * Esse filtro fica responsavel por executar os encoding 
	 * Para resolver os problemas de acentuação
	 * */
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		// Configuração referente a expirar a sessão
		// Mas esse tempo expira mesmo que o usuário estiver usando o sistema
		// é o tempo máximo de uma sessão
		// servletContext.getSessionCookieConfig().setMaxAge(20); // 20 segundos
		
		// para evitar a informação de JSESSIONID que por agumas vezes aparece na url da aplicação
		// Esse comando faz ele enviar isso para um COOKIE e não colocar na URL da pagina
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		
		characterEncodingFilter.setInitParameter("encoding", "UTF-8"); // usar UTF-8
		characterEncodingFilter.setInitParameter("forceEncoding", "true"); // Força o encoding
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*"); // para todas as URLS
	}

}
