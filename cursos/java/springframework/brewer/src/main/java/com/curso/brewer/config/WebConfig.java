package com.curso.brewer.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.curso.brewer.controller.converter.EstilosConverter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/*
 * Classe que ensina ao "DispatcherServlet" 
 * encontrar os controllers 
 * */

@Configuration 
/* Anotação fica responsavel por encontrar os controladores
 * basePackageClasses: configuro as classes que são controller 
 * basePackages: vetor de string onde posso configurar o pacote de controladores */
@ComponentScan(basePackages = {"com.curso.brewer.controller"})
/* Habilitar este projeto para desenvolvimento WEB */
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext =  applicationContext;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		// Configurações referente ao ViewResolver do Spring
		// Este "resolver" fica responsavel por pegar o "nome" e deveolver o arquivo html
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	@Bean
	public TemplateEngine templateEngine() {
		// O templateEngine é que processa os arquivos html
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		
		// adicionar um novo Dialeto
		engine.addDialect(new LayoutDialect());
		
		return engine;
	}
		
	private ITemplateResolver templateResolver() {
		// para resolver templates do Spring
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		// Onde estão os meus templates
		resolver.setPrefix("classpath:/templates/");
		// Tipo de extensão (Essa configuração é para não haver necessidade de digitar a extensão result do resolver)
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Busca todos os recursos da do classpath na pasta static 
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	// Necessário registar os conversores (Converter)
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// Converter de Estilo
		conversionService.addConverter(new EstilosConverter());
		
		return conversionService;
	}
}
