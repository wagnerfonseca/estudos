package com.curso.brewer.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

// Onde essa anotação pode ser aplicada
// FIELD =  Nos campos
// METHOD = Metodos
// ANNOTATION_TYPE = em outra anotação
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME) // Essa Anotação habilita nossa anotação ser avaliada em tempo de execução
@Constraint(validatedBy = {}) // Essa anotação habilita essa classe para validações  
//"?" no REGEX para identificar se houver dados 
@Pattern(regexp = "([a-zA-Z]{2}\\d{4})?")
public @interface SKU {

	// Para sobrescrever a messagem padrão do "Pattern"
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "SKU deve seguir o padrão DD9999(2 Digitos e 4 Caracteres)";
	
	// Propriedades obrigatórias em uma anotação
	Class<?>[] groups() default {}; // para agrupar anotações
	Class<? extends Payload>[] payload() default {}; // auxilia para carregar mais informações sobre a anotações
}
