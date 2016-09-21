package com.curso.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.Origem;
import com.curso.brewer.model.Sabor;
import com.curso.brewer.repository.Cervejas;
import com.curso.brewer.repository.Estilos;
import com.curso.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private Estilos estilos;
		
	@Autowired
	private CadastroCervejaService service;
	
	@Autowired
	private Cervejas cervejas;
		
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
				
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {			
			return novo(cerveja);
		}
	
		service.salvar(cerveja);
		
		attributes.addFlashAttribute("mensagem", "Cerveja Salva com Sucesso!");
		
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
	/*
	 * para pesquisa a boa prática é utilizar a requisicao GET
	 * */
	@GetMapping
	public ModelAndView pesquisar() {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
		
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		
		mv.addObject("cervejas", cervejas.findAll());
		
		return mv;
	}
}
