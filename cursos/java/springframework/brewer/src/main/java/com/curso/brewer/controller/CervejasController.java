package com.curso.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.model.Cerveja;

@Controller
public class CervejasController {
	
	private static Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@RequestMapping("/cervejas/novo")
	public String novo(Cerveja cerveja) {
		
		return "cerveja/CadastroCerveja";
	}
	
	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {	
			novo(cerveja);
			return "cerveja/CadastroCerveja";
		}
		
		attributes.addAttribute("mensagem", "Cerveja Salva com Sucesso!");
		return "redirect:/cervejas/novo";
	}
	
	@RequestMapping("/cervejas/cadastro")
	public String cadstro() {
		return "cerveja/cadastro-produto";
	}

}