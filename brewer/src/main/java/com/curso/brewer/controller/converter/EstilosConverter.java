package com.curso.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.curso.brewer.model.Estilo;

public class EstilosConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String codigo) {		
		// Essa verificação evita um erro de quando você não informa o estilo para a entidade cerveja
		if (!StringUtils.isEmpty(codigo)){
			Estilo estilo = new Estilo();
			estilo.setCodigo(Long.valueOf(codigo));
			return estilo;
		}
		return null;
	}

}
