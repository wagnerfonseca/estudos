package com.curso.brewer.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.curso.brewer.validation.validator.AtributoConfirmacaoValidator;

//Onde essa anotação pode ser aplicada
//TYPE =  Em cima de uma classe
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME) // Essa Anotação habilita nossa anotação ser avaliada em tempo de execução
@Constraint(validatedBy = { AtributoConfirmacaoValidator.class }) // Essa anotação habilita uma classe para validações  
public @interface AtributoConfirmacao {
	
	// Para sobrescrever a messagem padrão do "Pattern"
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Atributos não conferem";
	
	// Propriedades obrigatórias em uma anotação
	Class<?>[] groups() default {}; // para agrupar anotações
	Class<? extends Payload>[] payload() default {}; // auxilia para carregar mais informações sobre a anotações
	
	/*
	 * Atributos da minha anotacao
	 * */
	String atributo();
	
	String atributoConfirmacao();

}
