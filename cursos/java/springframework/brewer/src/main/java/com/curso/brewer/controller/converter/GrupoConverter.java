package com.curso.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.curso.brewer.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo> {

	@Override
	public Grupo convert(String value) {
		if(!StringUtils.isEmpty(value)) {
			Grupo grupo = new Grupo();
			grupo.setCodigo(Long.valueOf(value));
			return grupo;
		}
		return null;
	}

}
