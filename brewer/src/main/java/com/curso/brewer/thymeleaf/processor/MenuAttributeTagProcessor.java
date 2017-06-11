package com.curso.brewer.thymeleaf.processor;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class MenuAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NOME_ATRIBUTO = "menu";
	private static final int PRECEDENCIA = 1000;
	
	public MenuAttributeTagProcessor(String dialectPrefix) {
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
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attrName, String attributeValue,
			IElementTagStructureHandler structureHandler) {
			
		IEngineConfiguration configuration = context.getConfiguration(); // obter as configuracao
		IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration); 
		IStandardExpression expression = parser.parseExpression(context, attributeValue);
		String menu = (String) expression.execute(context); // Quero interpretar a String "@/estilos" para o caminho completo "/brewer/estilos"
		
		
		HttpServletRequest request = ((IWebContext) context).getRequest();
		String uri = request.getRequestURI();
	
		/*
		 * Na maioria dos casos eu desejo deixar dois link de pesquisa e cadastro para o mesmo link de menu * 
		 * uri.startsWith(menu)
		 * */
		
		if (uri.matches(menu)) {
			String classesExistentes = tag.getAttributeValue("class"); //pego todos os valores que estão contidos em "class"
			structureHandler.setAttribute("class", classesExistentes + " is-active"); // modifico os valores contidos em "class"
		}

	}

}