package com.curso.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.curso.brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files; 
	private DeferredResult<FotoDTO> result;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result) {		
		this.files = files;
		this.result = result;
	}

	@Override
	public void run() {
		System.out.println("[INFO] " + this.getClass().getName() + " " + this.files[0].getOriginalFilename());
		
		//TODO: Salvar a Foto no sistema de arquivos
		String nome = files[0].getOriginalFilename();
		String contentType = files[0].getContentType();
		this.result.setResult( new FotoDTO(nome, contentType) );
	}

}
