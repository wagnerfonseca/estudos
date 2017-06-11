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

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOG = LoggerFactory.getLogger(FotoStorageLocal.class);
	private static final String THUMBNAIL_PREFIX = "thumbnail.";
	
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
	
	@Override
	public byte[] recuperaFotoTemporaria(String nome) {		
		try {
			/* o método resolve acrescenta neste endereço o tipo do separador conforme o sistema operacional  
			 * junto com o path que você esta utilizando. ele ja faz uma concatenacao de enderecos */ 
			Path arquivo = this.localTemporario.resolve(nome); 
			/* Files.readAllBytes le todos o bytes do arquivos ate o final se não houver nenhuma exceção lançada */
			return Files.readAllBytes(arquivo);
		} catch (IOException e) {
			throw new RuntimeException("Erro salvando a foto na pasta temporária", e);
		}
	}
	
	@Override
	public byte[] recuperaFoto(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler a foto", e);
		}
	}
	
	@Override
	public void apagarFotoTemporaria(String nome) {
		try {
			Files.deleteIfExists(this.localTemporario.resolve(nome));
		} catch(IOException e) {
			throw new RuntimeException("Erro ao apagar a foto " + nome + " no diretório " + this.localTemporario.toAbsolutePath(), e);
		}
	}
	
	@Override
	public void salvar(String nome){
		try {
			Files.move(this.localTemporario.resolve(nome), this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao mover a foto para o loca de destino ", e);			
		}
		
		try {
			Thumbnails.of(this.local.resolve(nome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail", e);
		}
	}
	
	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto", e);
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String foto) {
		return recuperar(THUMBNAIL_PREFIX + foto);
	}	
	
	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + foto));
		} catch (IOException e) {
			LOG.warn(String.format("Erro apagando foto '%s'. Mensagem: %s", foto, e.getMessage()));
		}		
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local); // Cria a pasta de fotos
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp"); 
			Files.createDirectories(this.localTemporario); // Cria a pasta temporaria
			
			LOG.debug("pastas criadas para salvar fotos");
			LOG.debug("pastas default: " +  this.local.toAbsolutePath());
			LOG.debug("pasta temporaria: " + this.localTemporario.toAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salva foto", e);
		}
		
	}
	
	private String renomearNomeArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
		return novoNome;
	}

	
}
