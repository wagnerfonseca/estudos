package com.curso.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.curso.brewer.dto.FotoDTO;
import com.curso.brewer.storage.FotoStorage;
import com.curso.brewer.storage.FotoStorageRunnable;

/*
 * Não preciso fazer esse action dentro do controller de cerveja eu posso fazer separado
 * */
@RestController // Adiciona a anotacao @Controller e @ResponseBody para  os retornos de todos os métodos 
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	
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
		
		Thread thread = new Thread(new FotoStorageRunnable(files, result, fotoStorage));
		thread.start();
		
		return result;
	}
	
	/*
	 * Quero mostrar a imagem na pagina web
	 * O nome do parametro pode ser o mesmo da anotação, 
	 * em caso de nomes diferentes deve ser especificado na anotação @PathVariable("nome")
	 * 
	 * (dois pontos)(:) para utilzr um expressão regular
	 * */
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) { 
		return fotoStorage.recuperaFotoTemporaria(nome);	
	}

}
