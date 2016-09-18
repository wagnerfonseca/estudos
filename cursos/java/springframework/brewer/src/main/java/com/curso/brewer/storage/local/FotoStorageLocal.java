package com.curso.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.curso.brewer.storage.FotoStorage;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOG = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		/*
		 * Para capturar a pasta padrão de usuário.
		 * Essa é uma solução temporária, não é ideal para uso em produção 
		 * */		
		this(getDefault().getPath(System.getenv("USERPROFILE"), ".brewerfotos"));
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			LOG.debug("pastas criadas para salvar fotos");
			LOG.debug("pastas default: " +  this.local.toAbsolutePath());
			LOG.debug("pasta temporaria: " + this.localTemporario.toAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salva foto", e);
		}
		
	}

	@Override
	public void salvarTemporariamente(MultipartFile[] files) {
		System.out.println("Salvando temporariamente");		
	}
	
}
