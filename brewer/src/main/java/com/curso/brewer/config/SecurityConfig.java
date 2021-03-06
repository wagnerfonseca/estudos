package com.curso.brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.curso.brewer.security.AppUserDetailsService;

/*
 * WebSecurityConfigurerAdapter proporciona alguns métodos para serem sobrescritos com relação a segurança 
 * */

@EnableWebSecurity // Ja tem a anotacao @Configuration
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true) // Habilita as anotações pre post podem ser acrescentadas nos diversos metodos do sistema brewer
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* O sistema fica responsavel por buscar as informações de autenticação
		 * mas é o spring security que fica responsavel por verificar a senha */
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
			/*.inMemoryAuthentication()
				.withUser("admin")
				.password("123456")
				.roles("CADASTRO_CLIENTE");	*/
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/layout/**") 
				.antMatchers("/images/**"); // essas configurações tambem podem ficar no objeto  "HttpSecurity"
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// a ordem das declarações influência
		http
			.authorizeRequests()			
				.antMatchers("/cidades/novo").hasRole("CADASTRAR_CIDADE")
				// (/**) expressao ANT
				.antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")
				.anyRequest().authenticated() // qualquer requisicao deve estar autenticado				
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll() // para evitar erro de ERR_TOO_MANY_REDIRECTS um looping infinito para acessar a tela de login, permitAll não precisa estar autenticado 
				.and()
			.logout()
			// Página ncessária para funcionar o Logout depois que habilita o "CSRF"
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			// Informar qual a Pagina de Erro	
			.exceptionHandling()
				.accessDeniedPage("/403") // Acesso negado
				.and()
			// Configurações referente a sessao
			.sessionManagement()
				//.maximumSessions(1)
				//.expiredUrl("/login") //pagina para redirecionar*/
				.invalidSessionUrl("/login")
				
			;
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}
