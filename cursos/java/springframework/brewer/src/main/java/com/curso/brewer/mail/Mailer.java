package com.curso.brewer.mail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Mailer {

	/** Este metodo vai ser executado de forma assincrona */
	@Async
	public void enviar() {
		System.out.println(">>>>> enviando e-mail");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(">>>>> e-mail eviado");
		
	}
	
}
