package com.curso.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * WebSecurityConfigurerAdapter proporciona alguns métodos para serem sobrescritos com relação a segurança 
 * */

@EnableWebSecurity // Ja tem a anotacao @Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("admin")
				.password("123456")
				.roles("CADASTRO_CLIENTE");		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/layout/**") 
				.antMatchers("/images/**"); // essas configurações tambpem podem ficar no objeto  "HttpSecurity"
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// a ordem das declarações influencia
		http
			.authorizeRequests()				
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll() // para evitar erro de ERR_TOO_MANY_REDIRECTS um looping infinito para acessar a tela de login, permitAll não precisa estar autenticado 
				.and()
			.csrf().disable(); 
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}
