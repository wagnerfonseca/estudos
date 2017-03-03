package com.curso.brewer.config.init;

import javax.servlet.Filter;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.curso.brewer.config.JPAConfig;
import com.curso.brewer.config.MailConfig;
import com.curso.brewer.config.SecurityConfig;
import com.curso.brewer.config.ServiceConfig;
import com.curso.brewer.config.WebConfig;

/*
 * Classe de configuração do "FrontController do Spring MVC" que é o 
 *     "DispatcherServlet"
 * */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		/*
		 * Configurações que serão feitas na parte "mais baixa" do seu sistema  
		 * 
		 * */
		return new Class<?>[] {  JPAConfig.class, ServiceConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {	
		/*
		 * Configuração para o "DispatcherServlet", encontrar as minhas classes de controller
		 * 
		 * MailConfig.class
		 * Para usar o template HTML do thymeleaf no envio de email
		 * */
		return new Class<?>[] { WebConfig.class, MailConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		/* Diz o padrão que será delegado para o "DispatcherServlet",
		 * ou seja a URL de inicio da aplicação 
		 * 
		 * Para configuração e XML é o URLMappings*/
		return new String[] {"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {	
		/*
		 * Para registrar o tipo de conteudo Content-Type:multipart/form-data
		 * 
		 * A String vazia, o servidor web fica resposavel por decidir o local onde o arquivo vai ser escrito 
		 * */
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	@Override
	protected Filter[] getServletFilters() {
		/*
		 * Para resolver problemas de Acentuação SEM as CONFIGURAÇÕES de SEGURANÇA
		 * Uma vez que implementado as conficurações de segurança, esse filtro para de funcionar
		 * E deve ser migrado para a inicialização de seguranças
			CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
			characterEncodingFilter.setEncoding("UTF-8");
			characterEncodingFilter.setForceEncoding(true);	
		*/
		
		// permitir parametros em requisicoes pelo metodo http PUT
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		
		return new Filter[] { httpPutFormContentFilter };
	}
	
}
