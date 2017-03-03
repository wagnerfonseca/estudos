package com.curso.brewer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.curso.brewer.model.Venda;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;

	/** Este metodo vai ser executado de forma assincrona */
	@Async
	public void enviar(Venda venda) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("wag.b.fonseca@gmail.com");
		message.setTo(venda.getCliente().getEmail());
		message.setSubject("Venda Efetuada"); // Assunto
		message.setText("Obrigado, sua venda foi processada!");
		
		mailSender.send(message);
	}
	
}
