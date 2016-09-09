package com.curso.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.curso.brewer.model.Estilo;

public class EstilosConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String codigo) {
		// Nao da erro quando o codigo é vazio
		Estilo estilo = new Estilo();
		estilo.setCodigo(Long.valueOf(codigo));
		return estilo;
	}

}
