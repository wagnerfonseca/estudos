package com.curso.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForErrorAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NOME_ATRIBUTO = "classforerror";
	private static final int PRECEDENCIA = 1000;
	
	public ClassForErrorAttributeTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, // dialeto sera criado em uma página HTML
				dialectPrefix, // prefixo dialeto
				null, //elementName
				false, //prefixElementName
				ATTR_NOME_ATRIBUTO, //Nome do atributo que estamos criando "SEMPRE MINUSCULO"
				true, //estamos criando um atributo
				PRECEDENCIA, //procedencia, ordem de execucao dos atributos
				true); // true para remover atributo da pagina HTML
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attrName, String attrValue,
			IElementTagStructureHandler structureHandler) {
		/* No caso de ocorrer um erro, eu quero adicionar para o elemento HTML DIV, uma classe o 'has-erro'. 
		 *   que é uma classe do botstrap
		 * */		
		String field = attrValue; // normalmente é o valor que estamos passando como parametro ex. "sku", "nome" ...
		boolean temErro = FieldUtils.hasErrors(context, field); // Context me informa o contexto onde esse atributo esta sendo chamado.
		
		if (temErro) {
			String classesExistentes = tag.getAttributeValue("class"); //pego todos os valores que estão contidos em "class"
			structureHandler.setAttribute("class", classesExistentes + " has-error"); // modifico os valores contidos em "class"  
		}
	}

}
