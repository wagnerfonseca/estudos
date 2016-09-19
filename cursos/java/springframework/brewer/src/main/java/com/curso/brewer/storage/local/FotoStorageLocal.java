package com.curso.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

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

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String nomeArquivo = null;
		if (files != null && files.length > 0) {			
			MultipartFile arquivo = files[0];
			nomeArquivo = renomearNomeArquivo(arquivo.getOriginalFilename());
			String diretorioArquivo = 
					this.localTemporario.toAbsolutePath().toString() + 
					getDefault().getSeparator() + 
					nomeArquivo ;			
			try {
				arquivo.transferTo(new File( diretorioArquivo ));			
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar o arquivo no diretório " + this.localTemporario.toAbsolutePath().toString(), e);				
			}			
		}
		
		return nomeArquivo;
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
	
	private String renomearNomeArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
		
		LOG.debug(String.format("Novo nome Foto %s", novoNome));
		
		return novoNome;
	}
	
}
