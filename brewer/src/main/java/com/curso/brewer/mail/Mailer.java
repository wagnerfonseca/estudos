package com.curso.brewer.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.ItemVenda;
import com.curso.brewer.model.Venda;
import com.curso.brewer.storage.FotoStorage;

@Component
public class Mailer {
	
	private static Logger logger = LoggerFactory.getLogger(Mailer.class);	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf; // O thymeleaf fica responsavel por executar o template do email
	
	@Autowired
	private FotoStorage fotoStorage;

	/** Este metodo vai ser executado de forma assincrona */
	@Async
	public void enviar(Venda venda) {
		
		Context context = new Context(new Locale("pt", "BR")); // Contexto 
		context.setVariable("venda", venda); // Alguns objetos que devem ir para o contexto
		context.setVariable("logo", "logo"); // Crio uma variavel com valor em string "logo"
		
		// para buscar a foto da cerveja
		// Executar antes de processar o contexto
		Map<String, String> fotos = new HashMap<>(); // para cada foto tem um cid
		boolean adicionarMockCerveja = false; 
		for (ItemVenda item : venda.getItens()) {
			Cerveja cerveja = item.getCerveja();
			if (cerveja.temFoto()) {
				String cid = "foto-" + cerveja.getCodigo();
				context.setVariable(cid, cid);
				
				fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());
			} else {
				adicionarMockCerveja = true;
				context.setVariable("mockCerveja", "mockCerveja");
			}
		}
		
		try {
			String email = thymeleaf.process("mail/ResumoVenda", context); // processa o template junto com o Contexto
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			boolean adicionarImagens = true; // Trabalhar com o envio de imagens no email
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, adicionarImagens, "UTF-8"); 
			helper.setFrom("wag.b.fonseca@gmail.com");
			
			helper.setTo(venda.getCliente().getEmail());
			helper.setSubject(String.format("Brewer - Venda nº %d", venda.getCodigo()));
			helper.setText(email, true); // Corpo do email -- True porque é um HTML
			
			// "logo" -> nome da variavel
			// ClassPathResource -> retorna o path da imagem
			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
			
			if (adicionarMockCerveja) {
				helper.addInline("mockCerveja", new ClassPathResource("static/images/cerveja-mock.png"));
			}
			
			// Adicionar o caminho da foto para cerveja no email
			for (String cid : fotos.keySet()) {
				String[] fotoContentType = fotos.get(cid).split("\\|");  /// Montando um vetor
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				byte[] arrayFoto = fotoStorage.recuperarThumbnail(foto);
				helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
			}
		
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
		
		
	}
	
	/* Configurações simples para um envio de email 
	 * 
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("wag.b.fonseca@gmail.com");
		message.setTo(venda.getCliente().getEmail());
		message.setSubject("Venda Efetuada"); // Assunto
		message.setText("Obrigado, sua venda foi processada!");
		
		mailSender.send(message);
	 * */
	
}
