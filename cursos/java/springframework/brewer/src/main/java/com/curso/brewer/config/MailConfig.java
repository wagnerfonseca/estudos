package com.curso.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.curso.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
/* ${ambiente:local}
 * ambiente = é o nome da variavel
 * local = é o valor string padrão caso a variavel "ambiente" não possuir nenhum valor
 * 
 * Essa variavel ambiente é configurada diretamente no Tomcat
 * 
 *  */
@PropertySource({ "classpath:env/mail-${ambiente:local}.properties" }) // onde se encontra os arquivos de configuração do email
/* No Java 8 Pode repetir a mesma anotacao
 * A anotação que é declarada por ultimo prevalece sobre a primeira anotação
 * sobrescrendo os dados da primeira
 * 
 *  ignoreResourceNotFound = true <- caso o arquivo não existe, ignorar o erro 
 *  Unix/Linux: file://${HOME}/.brewer-mail.properties
 *  Windows: file:\\${USERPROFILE}\\.brewer-mail.properties
 * */
@PropertySource(value = { "file:\\${USERPROFILE}\\.brewer-mail.properties" }, ignoreResourceNotFound = true)
public class MailConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(584);
		
		mailSender.setUsername(env.getProperty("email.username"));
		mailSender.setPassword(env.getProperty("email.password"));
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // miliseconds

		mailSender.setJavaMailProperties(props);
		
		
		
		return mailSender;
	}
	
}
