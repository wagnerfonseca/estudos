package com.curso.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class OrderElementTagProcessor extends AbstractElementTagProcessor {
	
	private static final String NOME_TAG = "order";	
	private static final int PRECEDENCIA = 1000;
	
	public OrderElementTagProcessor(String dialectPrefix) {
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
		
		// Receber os dados dos atributos declarados na pagina HTML
		IAttribute page = tag.getAttribute("page");
		IAttribute field = tag.getAttribute("field");
		IAttribute text = tag.getAttribute("text");
		
		IModel model = modelFactory.createModel();
		// o que será criado na pagina HTML - Por exemplo
		// Preciso de criar um elemento th:block dentro dele tenho que adiconar um th:replace com o caminho
		model.add(modelFactory.createStandaloneElementTag(
				"th:block", 
				"th:replace", 
				String.format("fragments/Ordenacao :: order (%s, %s, %s)", page.getValue(), field.getValue(), text.getValue())
		));
		
		
		boolean processable = true; //dentro do model existe comando do Thymealef para ser processados, neste caso é true. Se apenas houvesse HTML, poderia ser false 
		structureHandler.replaceWith(model, processable);
	}

}
