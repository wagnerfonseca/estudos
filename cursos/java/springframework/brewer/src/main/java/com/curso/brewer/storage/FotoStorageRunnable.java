package com.curso.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files; 
	private DeferredResult<String> result;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<String> result) {
		super();
		this.files = files;
		this.result = result;
	}

	@Override
	public void run() {
		System.out.println(" Original File Name -> " + this.files[0].getOriginalFilename());
		
		//TODO: Salvar a Foto no sistema de arquivos
		this.result.setResult("Ok! Foto Recebida!");
	}

}
