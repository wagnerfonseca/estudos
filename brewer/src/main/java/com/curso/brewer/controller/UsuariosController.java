package com.curso.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.controller.page.PageWrapper;
import com.curso.brewer.model.Usuario;
import com.curso.brewer.repository.Grupos;
import com.curso.brewer.repository.Usuarios;
import com.curso.brewer.repository.filter.UsuarioFilter;
import com.curso.brewer.service.CadastroUsuariosService;
import com.curso.brewer.service.StatusUsuario;
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
	
	@PostMapping({"/novo", "{\\d+}"})
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
	public ModelAndView pesquisar(UsuarioFilter filtro,
			BindingResult result,
			@PageableDefault(size = 4) 
			Pageable pageable,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");		
		mv.addObject("grupos", grupos.findAll());
		
		
		PageWrapper<Usuario> pagina = new PageWrapper<>(usuarios.filtrar(filtro, pageable), request); 
		
		mv.addObject("pagina", pagina);
		return mv;
	}
	
	/**
	 * Aula: 21.4
	 * Para atualizar alguma coisa usamos o metodo Http PUT 
	 * 
	 * O Spring tem um barramento de segurança que impede qualquer metodo PUT de receber parametros, por isso deve implementar um filter 
	 * */
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK) // para evitar o erro de 500
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario status) { // o Spring MVC ja converte o Enum em String
		//Arrays.asList(codigos).forEach(System.out::println);		
		service.alterarStatus(codigos, status);
	}
	
	
	/** Aula: 25.3 Editar usuario
	 * Ao editar um usuario por ser um cadastro de uma entidade ManyToMany como evitar a exception #LazyInitializationException */
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		// buscar o usario com a lista de grupo e evitar o erro de #LazyInitializer
		// Evitar anotar a propriedade da classe com (fetch "EAGER") 
		Usuario usuario = usuarios.buscaPorCodigoComGrupos(codigo);		
		ModelAndView mv = novo(usuario); 
		mv.addObject(usuario);
		
		return mv;
	}

}
