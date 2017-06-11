package com.curso.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.controller.page.PageWrapper;
import com.curso.brewer.model.Cidade;
import com.curso.brewer.repository.Cidades;
import com.curso.brewer.repository.Estados;
import com.curso.brewer.repository.filter.CidadeFilter;
import com.curso.brewer.service.CadastroCidadesService;
import com.curso.brewer.service.exception.NomeCidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroCidadesService service;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade"); // escrever o caminho da página que você deseja renderizar
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	//@Secured("ROLE_CADASTRAR_CIDADE") // essa anotação evitar deste método ser chamado por um usuário que não tem permissão
	
	
	@PostMapping("/novo")
	// Para recompilar o cache quando houver uma atulização nos dados que foram configurados para ficar em cache
	// value = Local onde se encontra o seu cache.
	// allEntries = true -> Refazer todo o cache 
	// condition -> condição para executar o @CacheEvict
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return novo(cidade);
		}		
		
		try {
			service.salvar(cidade);
		} catch(NomeCidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		
		attr.addFlashAttribute("mensagem", "Cidade cadastrada com sucesso!");
		return new ModelAndView("redirect:/cidades/novo"); //apos o sinal de ":" mapear o caminho do método
	}

	// CACHE
	//-----------
	//Para as consultas onde os valores dificilmente são modificados
	// "cidade" é o nome da região onde este cache vai ficar
	// Se os parametros passados para o parametro ja estão no cache, este método não é executado	
	// key -> o valor que será usado como chave, essa chave é implementada para auxiliar qual a parte do cache deve ser atulizado 
	// Sempre com "#" e usando o mesmo nome do parametro que vai ser usado como chave
	@Cacheable(value = "cidades", key = "#codigoEstado")
	
	// RESTFul
	//-----------
	// Este método vai obedecer as recomendações RESTful 
	// consumes = MediaType.APPLICATION_JSON_VALUE -> Para o consumo (request) requisição em formato JSON  
	// @RequestParam(name = "estado", defaultValue = "-1") -> para passar brewer/cidades?estado=6
	// @ResponseBody -> para resposta no formato JSON	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1")
			Long codigoEstado) {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter filter, BindingResult result
			, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
		
		mv.addObject("estados", estados.findAll());
		
		PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidades.filtrar(filter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
}
