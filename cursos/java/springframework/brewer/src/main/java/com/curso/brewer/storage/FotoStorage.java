package com.curso.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	void salvarTemporariamente(MultipartFile[] files);
	
}
