package com.curso.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.controller.page.PageWrapper;
import com.curso.brewer.dto.CervejaDTO;
import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.Origem;
import com.curso.brewer.model.Sabor;
import com.curso.brewer.repository.Cervejas;
import com.curso.brewer.repository.Estilos;
import com.curso.brewer.repository.filter.CervejaFilter;
import com.curso.brewer.service.CadastroCervejaService;
import com.curso.brewer.service.exception.ImpossivelExcluirEntidadeException;

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
	
	/* Para requisições com o mesmo verbo HTTP mas com disposições diferentes */	
	@RequestMapping(value = {"/novo", "{\\d+}"}, 
					method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {			
			return novo(cerveja);
		}
	
		service.salvar(cerveja);
		
		attributes.addFlashAttribute("mensagem", "Cerveja Salva com Sucesso!");
		
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
	/*
	 * para pesquisa a boa prática é utilizar a requisicao GET
	 * 
	 * O objeto declarado dentro de th:object, pode ser passado com parametro utilizando tambem o BindingResult
	 * 
	 * @PageableDefault  Responsavel pelas configurações de requisição da pagina, 
	 * 		size <- tamanho da lista que vai ser retornada
	 * */
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, 
			BindingResult result,
			@PageableDefault(size = 4) 
			Pageable pageable,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
		
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		
		//O objeto Page fica responsavel por retornar a lista de objeto 
		// e controlar qual a pagina esta sendo requisitada 
		PageWrapper<Cerveja> pagina = new PageWrapper<>(cervejas.filtrar(cervejaFilter, pageable), request); 
		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	
	/**
	 * Metodos para pequisar cerveja na tela de venda(Pedido)
	 * @ResponseBody -  para retornar um objeto do tipo JSON
	 * */
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome) {
		return cervejas.porSkuOuNome(skuOuNome);
	}
	
	/**
	 * Metodo responsavel pela excluir a cerveja
	 * */
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cerveja cerveja) {
		try {
			service.excluir(cerveja);
		} catch (ImpossivelExcluirEntidadeException e) {	
			// e.getMessage() vai no corpo e posso capturar no java script atraves do "e.responseText"
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Editar uma cerveja
	 * */
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cerveja cerveja) {
		ModelAndView mv = novo(cerveja);
		mv.addObject(cerveja); // o objeto "cerveja" esta vindo da pagina, é necessário adicionar o objeto		
		return mv;
	}
}
