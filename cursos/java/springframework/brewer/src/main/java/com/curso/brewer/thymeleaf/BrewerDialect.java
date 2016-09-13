package com.curso.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.curso.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect  {

	public BrewerDialect() {
		super("AlgaWorks Brewer",  // nome do dialeto
			  "brewer", // Prefixo do dialeto. o nome que será utilizado na página HTML 
			  StandardDialect.PROCESSOR_PRECEDENCE);		
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {	
		final Set<IProcessor> processadores =  new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		return processadores;
	}

}
