package com.curso.brewer.config.init;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.curso.brewer.config.WebConfig;

/*
 * Classe de configuração do "FrontController do Spring MVC" que é o 
 *     "DispatcherServlet"
 * */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {	
		/*
		 * Configuração para o "DispatcherServlet", encontrar as minhas classes de controller
		 * */
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		/* Diz o padrão que será delegado para o "DispatcherServlet",
		 * ou seja a URL de inicio da aplicação 
		 * 
		 * Para configuração e XML é o URLMappings*/
		return new String[] {"/"};
	}

}
