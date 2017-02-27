package com.curso.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.Cervejas;
import com.curso.brewer.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@GetMapping("/nova")
	public String nova() {
		return "venda/CadastroVenda";
	}
	
	/**
	 * Adionar um produto para os itens da venda
	 * */	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		
		return getModelAndViewTabelaItens();
	}

	/**
	 * alterar Quantidade
	 * */
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(
			@PathVariable Long codigoCerveja, // vem pela path de requisicao
			Integer quantidade) { // vem no corpo da requisição
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItensVenda.alterarQuantidadeItens(cerveja, quantidade);
		
		return getModelAndViewTabelaItens();
	}

	private ModelAndView getModelAndViewTabelaItens() {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		return mv;
	}
	
}
