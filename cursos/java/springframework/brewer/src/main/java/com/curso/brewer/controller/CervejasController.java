package com.curso.brewer.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.Sabor;

@Controller
public class CervejasController {
		
	@RequestMapping("/cervejas/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
				
		mv.addObject("sabores", Sabor.values());		
		
		return mv;
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
