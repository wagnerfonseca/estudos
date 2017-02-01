package com.curso.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.curso.brewer.model.Cidade;
import com.curso.brewer.repository.Cidades;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private Cidades cidades;
	
	@RequestMapping("/novo")
	public String  novo() {
		return "cidade/CadastroCidade";
	}

	// Este método vai obedecer as recomendações RESTful
	// consumes = MediaType.APPLICATION_JSON_VALUE -> Para o consumo (request) requsição em formato JSON  
	// @RequestParam(name = "estado", defaultValue = "-1") -> para passar brewer/cidades?estado=6
	// @ResponseBody -> para resposta no formato JSON	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1")
			Long codigoEstado) {
		
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
}
