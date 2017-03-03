package com.curso.brewer.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.curso.brewer.controller.CervejasController;
import com.curso.brewer.controller.converter.CidadeConverter;
import com.curso.brewer.controller.converter.EstadoConverter;
import com.curso.brewer.controller.converter.EstilosConverter;
import com.curso.brewer.controller.converter.GrupoConverter;
import com.curso.brewer.session.TabelaItensSession;
import com.curso.brewer.thymeleaf.BrewerDialect;
import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
import com.google.common.cache.CacheBuilder;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/*
 * Classe que ensina ao "DispatcherServlet" 
 * encontrar os controllers 
 * */

@Configuration 
/* Anotação fica responsavel por encontrar os controladores
 * basePackageClasses: configuro as classes que são controller **posso utilizar uma classe como parametro, para configurar o pacote 
 * basePackages: vetor de string onde posso configurar o pacote de controladores
 *  
 * @ComponentScan(basePackages = {"com.curso.brewer.controller"})
 * 
 *  */ 
@ComponentScan(basePackageClasses = {CervejasController.class, TabelaItensSession.class})
/* Habilitar este projeto para desenvolvimento WEB */
@EnableWebMvc
/* Habilitar o Spring para Paginação e ordenação */
@EnableSpringDataWebSupport
/* Habilitar cache em memória de retorno de determinados métodos */
@EnableCaching
/* Habilitar chamadas de métodos assincronos */
@EnableAsync
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
		
		// adicionar o Dialeto customizado
		engine.addDialect(new BrewerDialect());
		
		engine.addDialect(new DataAttributeDialect());
		
		
		// Referente a configurações extras do Thymeleaf com SpringSecurity
		engine.addDialect(new SpringSecurityDialect());
		
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
		// Exmplo para converter um entidade
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// Converter de Estilo
		conversionService.addConverter(new EstilosConverter());
		conversionService.addConverter(new CidadeConverter());
		conversionService.addConverter(new EstadoConverter());
		conversionService.addConverter(new GrupoConverter());
		
		
		//Converter  BigDecimal
		NumberStyleFormatter bigDecimalFomatter = new NumberStyleFormatter("#,##0.00"); //
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFomatter);
		
		// Converter numero inteiros (Informar o pattern no padrão internacional)
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		// API de Datas do Java 8
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		//Para ele converter em horas quando for informado HH:mm
		dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatter.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		// Força o Spring a sempre interpretar todas as entradas de dados com o Padrão pt-BR
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	/* Bean responsavel pelo cachemanento de dados */
	@Bean
	public CacheManager cacheManager() {
		/* retornar um objeto responsavel pelo cacheamento 
		 -> ConcurrentMapCacheManager() um Map de cache [NãO é RECOMENDADO PARA PRODUCAO]
		 * */
		
		// Usando Guava
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
				.maximumSize(3)
				.expireAfterAccess(20, TimeUnit.SECONDS);
		
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		cacheManager.setCacheBuilder(cacheBuilder);
		
		return cacheManager; 
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("classpath:/messages"); //não precisa colocar .properties
		bundle.setDefaultEncoding("UTF-8"); // 
		return bundle;
	}
	
	/* devido a integracao do JPA com Spring MVC. Este Bean fica responsavel por converter o codigo da requisao em 
	 * objeto da entidade, sem a necessidade de implementação própria
	 * evitando uma linha a menos em seu codigo */
	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}
	
}
