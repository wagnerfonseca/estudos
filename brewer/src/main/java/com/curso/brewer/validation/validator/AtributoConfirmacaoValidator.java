package com.curso.brewer.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.curso.brewer.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object>{

	private String atributo;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConfirmacao annotation) {
		// Inicializar a anotacao
		// Capturar o valor dos atributos
		
		this.atributo = annotation.atributo();
		this.atributoConfirmacao = annotation.atributoConfirmacao();
	}

	@Override
	public boolean isValid(Object source, ConstraintValidatorContext context) {
		boolean valido =false;
		try {
			//  BeanUtils.getProperty um metodo muito util para popular propriedades JavaBeans via reflexão 
			Object valorAtributo = BeanUtils.getProperty(source, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(source, this.atributoConfirmacao);
			
			valido = ambosSaoNull(valorAtributo, valorAtributoConfirmacao) || ambosSaoIguais(valorAtributo, valorAtributoConfirmacao);
			
		} catch (Exception e) {			
			throw new RuntimeException("Erro recuperando valores dos atributos", e); 
		}
		
		// Para o Thymeleaf comprender que existe um erro para o atributo da classe
		if (!valido) {
			// Desabilita a mensagem padrão de erro
			// para a mensagem de erro não ficar repetida
			context.disableDefaultConstraintViolation();  
			
			// buscar a mensagem de erro
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			// colocar ela no atributo informado
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}
		
		return valido;
	}

	private boolean ambosSaoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {		
		return valorAtributo != null && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private boolean ambosSaoNull(Object valorAtributo, Object valorAtributoConfirmacao) {		
		return valorAtributo == null && valorAtributoConfirmacao == null;
	}

}
