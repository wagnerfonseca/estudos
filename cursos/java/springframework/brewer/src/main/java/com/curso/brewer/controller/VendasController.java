package com.curso.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.Venda;
import com.curso.brewer.repository.Cervejas;
import com.curso.brewer.security.UsuarioSistema;
import com.curso.brewer.service.CadastroVendaService;
import com.curso.brewer.session.TabelaItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelaItensSession tabelaItensVenda;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		venda.setUuid(UUID.randomUUID().toString());
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(Venda venda, RedirectAttributes attr, @AuthenticationPrincipal UsuarioSistema usuario) {
				
		// @AuthenticationPrincipal recebendo o usuario logado
		venda.setUsuario(usuario.getUsuario());
		
		// Recuperando os itens da venda
		venda.adicionarItens(tabelaItensVenda.getItens(venda.getUuid()));
		
		cadastroVendaService.salvar(venda);
		attr.addFlashAttribute("mensagem", "Venda salva com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	/**
	 * Adionar um produto para os itens da venda 
	 * */	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItensVenda.adicionarItem(uuid, cerveja);
		
		return getModelAndViewTabelaItens(uuid);
	}

	/**
	 * Alterar Quantidade do produto no item de venda
	 * 
	 * * Como o JPA tem um integração com Spring MVC, você pode eliminar uma linha de codigo 
	 * convertendo o codigo da entidade direto em objeto
	 * 
	 * Injetar um Bean que fica responsavel por fazer essa conversão no "WebConfig.java"
	 * 
	 * remove a linha: Cerveja cerveja = cervejas.findOne(codigoCerveja);
	 * e confira o path variable
	 * 
	 * */
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(
			@PathVariable("codigoCerveja") Cerveja cerveja, // vem através de uma variavel declarada na URI de requisicao
			Integer quantidade,
			String uuid) { // vem no corpo da requisição		
		tabelaItensVenda.alterarQuantidadeItens(uuid, cerveja, quantidade);
		
		return getModelAndViewTabelaItens(uuid);
	}
	
	/**
	 * Excluir o item de venda
	 * 
	 * Dentro do protocolo HTTP, quando o verbo é "DELETE"
	 * não tem como colocar o parametro dentro do corpo da requisicao
	 * */
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(
			@PathVariable("codigoCerveja") Cerveja cerveja,
			@PathVariable String uuid) {
		tabelaItensVenda.excluirItem(uuid, cerveja);
		
		return getModelAndViewTabelaItens(uuid);
	}

	private ModelAndView getModelAndViewTabelaItens(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens(uuid));
		mv.addObject("valorTotal", tabelaItensVenda.getValorTotal(uuid));
		return mv;
	}
	
}
