package com.curso.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	String salvarTemporariamente(MultipartFile[] files);

	byte[] recuperaFotoTemporaria(String nome);
	
	void apagarFotoTemporaria(String nome);
	
}
