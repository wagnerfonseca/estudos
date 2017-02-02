package com.curso.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.model.Cliente;
import com.curso.brewer.model.TipoPessoa;
import com.curso.brewer.repository.Estados;
import com.curso.brewer.service.CadastroClienteService;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Estados estados; 
	
	@Autowired
	private CadastroClienteService cadastroClienteService;

	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return novo(cliente);
		}
		
		cadastroClienteService.salvar(cliente);
		
		attr.addAttribute("mensagem", "Cliente salvo com sucesso");		
		
		return new ModelAndView("redirect:/clientes/novo");
	}
	
}