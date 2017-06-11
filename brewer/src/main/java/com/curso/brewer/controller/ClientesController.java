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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.controller.page.PageWrapper;
import com.curso.brewer.model.Cliente;
import com.curso.brewer.model.TipoPessoa;
import com.curso.brewer.repository.Clientes;
import com.curso.brewer.repository.Estados;
import com.curso.brewer.repository.filter.ClienteFilter;
import com.curso.brewer.service.CadastroClienteService;
import com.curso.brewer.service.exception.CpfCnpjClienteCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Estados estados; 
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@Autowired
	private Clientes clientes;

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
		
		try {
			cadastroClienteService.salvar(cliente);
		} catch(CpfCnpjClienteCadastradoException e) {
			// Enviando uma mensagem de erro para a camada de "view"
			// Utilizar quando você faz uma verificação e deseja enviar a mensagem de erro de uma exceção para a "View" 
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		
		attr.addFlashAttribute("mensagem", "Cliente salvo com sucesso");		
		
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult result
			, @PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		
		PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(clientes.filtrar(clienteFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	/** Aula 22.3 */
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		return clientes.findByNomeStartingWithIgnoreCase(nome);
	}

	/* Como Capturar uma execao em um método separado */
	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3)
			throw new IllegalArgumentException();
	}
	
	/** Este método é utilizado para capturar todas as exceções do tipo 
	 * "IllegalArgumentException" lançados  dentro DESTE controller 
	 * Para Capturar uma exceção no nivel de aplicação existe uma classe anotada com @ControllerAdvice 
	 * 
	 * Outra vantagem deste tipo de implementação, que o erro lançado não vai ser listado no log do Tomcat(WebServer)*/
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
}