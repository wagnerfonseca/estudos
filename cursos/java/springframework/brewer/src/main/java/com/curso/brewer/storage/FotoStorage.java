package com.curso.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	String salvarTemporariamente(MultipartFile[] files);

	byte[] recuperaFotoTemporaria(String nome);
	
	byte[] recuperaFoto(String nome);
	
	void apagarFotoTemporaria(String nome);

	void salvar(String foto);
	
	byte[] recuperar(String foto);

	byte[] recuperarThumbnail(String foto);	
	
}
