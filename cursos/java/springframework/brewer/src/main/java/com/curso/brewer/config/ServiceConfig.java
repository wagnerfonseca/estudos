package com.curso.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.curso.brewer.service.CadastroCervejaService;
import com.curso.brewer.storage.FotoStorage;
import com.curso.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorage(){
		return new FotoStorageLocal();
	}
	
}
