package com.curso.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.model.Usuario;
import com.curso.brewer.repository.Grupos;
import com.curso.brewer.repository.Usuarios;
import com.curso.brewer.repository.filter.UsuarioFilter;
import com.curso.brewer.service.CadastroUsuariosService;
import com.curso.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.curso.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuariosService service;
	
	@Autowired 
	private Usuarios usuarios;
	
	@Autowired 
	private Grupos grupos;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupos.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		try {
			service.salvar(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}  catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		return new ModelAndView("redirect:/usuarios/novo"); 
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter filtro) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");		
		mv.addObject("usuarios", usuarios.filtrar(filtro));
		mv.addObject("grupos", grupos.findAll());		
		return mv;
	}

}
