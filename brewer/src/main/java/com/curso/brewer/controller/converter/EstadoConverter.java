package com.curso.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.curso.brewer.model.Estado;

public class EstadoConverter implements Converter<String, Estado> {

		@Override
		public Estado convert(String codigo) {
			if (!StringUtils.isEmpty(codigo)){
				Estado obj = new Estado();
				obj.setCodigo(Long.valueOf(codigo));
				return obj;
			}	
			return null;
		}	
	
}
