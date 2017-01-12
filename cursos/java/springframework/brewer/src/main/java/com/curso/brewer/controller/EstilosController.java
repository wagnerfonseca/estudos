package com.curso.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.controller.page.PageWrapper;
import com.curso.brewer.model.Estilo;
import com.curso.brewer.repository.Estilos;
import com.curso.brewer.repository.filter.EstiloFilter;
import com.curso.brewer.service.CadastroEstiloService;
import com.curso.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {
	
	@Autowired
	private CadastroEstiloService service;
	
	@Autowired
	private Estilos estilos;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		return new ModelAndView("estilo/CadastroEstilo");
	}
	
	// ModelAndView Retorna para mim uma página completa, um view completa
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(estilo);
		}
		
		try {
			service.salvar(estilo);
		} catch(NomeEstiloJaCadastradoException e) {
			// Mandar a mensagem para a tela de cadastro de estilo
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}		
		
		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso");
		return new ModelAndView("redirect:/estilos/novo");
	}
	
	// ResponseEntity retorna um objeto que o javascript entende. Através dele, podemos acrescentar erros usando os codigos de resposta HTTP, e também utilizar dos outros codigo
	// @RequestBody fica responsavel pode receber um dado valor em JSON e converter em Objeto
	// @ResponseBody da mais autonomia na resposta. 
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) { //RequestBody convert o JSON em Objeto com ajuda do Jackson JSON
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage() ); //BAD REQUEST ERRO CODIGO HTTP 400
		}
		
		estilo = service.salvar(estilo);		
		
		return ResponseEntity.ok(estilo); //CODIGO HTTP 200 OK
	}
	
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter filter,
			BindingResult result,
			@PageableDefault(size = 4) 
			Pageable pageable,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilos");
		
		PageWrapper<Estilo> pagina = new PageWrapper<>(estilos.filtrar(filter, pageable), request);
		mv.addObject("pagina", pagina);
		return mv;
	}
	

}
