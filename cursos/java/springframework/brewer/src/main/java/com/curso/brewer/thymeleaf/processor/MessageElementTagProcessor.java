package com.curso.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MessageElementTagProcessor extends AbstractElementTagProcessor {
	
	private static final String NOME_TAG = "message";	
	private static final int PRECEDENCIA = 1000;
	
	public MessageElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, // Qual o tipo de template que vai ser utilizado 
			  dialectPrefix, // o nome do elemento
			  NOME_TAG, // elemento 
			  true, 
			  null,  
			  false, 
			  PRECEDENCIA);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		
		IModel model = modelFactory.createModel();
		model.add(modelFactory.createStandaloneElementTag("th:block", "th:include", "fragments/MensagemSucesso :: msg-success"));
		model.add(modelFactory.createStandaloneElementTag("th:block", "th:include", "fragments/MensagensErroValidacao :: msg-error"));
		
		boolean processable = true; //dentro do model existe comando do Thymealef para ser processados, neste caso é true. Se apenas houvesse HTML, poderia ser false 
		structureHandler.replaceWith(model, processable);
	}

}
