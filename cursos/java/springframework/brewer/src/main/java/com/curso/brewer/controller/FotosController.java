package com.curso.brewer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.curso.brewer.dto.FotoDTO;
import com.curso.brewer.storage.FotoStorageRunnable;

/*
 * Não preciso fazer esse action dentro do controller de cerveja eu posso fazer separado
 * */
@RestController // Adiciona a anotacao @Controller e @ResponseBody para  os retornos de todos os métodos 
@RequestMapping("/fotos")
public class FotosController {
	
	
	/*
	 * Parametro MultipartFile[] files: 
	 * Quando você envia um um formulário HTML com metodo POST e o Content-Type:multipart/form-data
	 * sua solicitação retorna uma mensagem com um corpo. "Request payload" que pode ser observado através de Ferramentas de desenvolvimento no seu browser
	 * No momento de submeter esses dados através do ajax, esses dados de entrada tem um nome e deve ser especificado através da anotação @RequestParam
	 * */	
	/* 
	 * DeferredResult proporciona uma alternativa para utilizar uma Função executada para o processamento de uma requisição assincrona.
	 * com um DeferredResult a aplicação pode produzir o resultado em uma thread de sua escolha
	 * */
	@PostMapping // substitui a anaotação @RequestMapping(method = RequestMethod.POST)
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> result = new DeferredResult<>();
		
		Thread thread = new Thread(new FotoStorageRunnable(files, result));
		thread.start();
		
		return result;
	}

}
