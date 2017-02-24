package com.curso.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.curso.brewer.service.exception.NomeEstiloJaCadastradoException;

/**
 * Esta classe vai ficar responsavel capturar as exceções/erros lançadas e tratar o exceções/erros e a nivel de Aplicação
 * Deste q tenha um metodo anotado para captura-lo
 * 
 * ControllerAdvice - fica responsavel por ficar monitorando os controllers
 * =>###> O pacote desta classe deve ficar no mesmo pacote dos controllers <###<=
 * */
@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	//O retorno do tipo <String> é o unico tipo que vai ser preenchido o .body
	@ExceptionHandler(NomeEstiloJaCadastradoException.class)
	public ResponseEntity<String> handleNomeEstiloJaCadastradoException(NomeEstiloJaCadastradoException e) {
		return ResponseEntity.badRequest().body(e.getMessage()); // o badrequest vai retorna em codigo http 400
	}
}
