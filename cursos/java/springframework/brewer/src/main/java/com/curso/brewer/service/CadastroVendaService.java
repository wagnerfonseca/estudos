package com.curso.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.ItemVenda;
import com.curso.brewer.model.Venda;
import com.curso.brewer.repository.Vendas;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas repository;
	
	@Transactional
	public void salvar(Venda venda) {
		if (venda.isNova())
			venda.setDataCriacao(LocalDateTime.now());		
		
		
		//Calcular o valor total
		BigDecimal valorTotalItens = 
				venda.getItens().stream()
					.map(ItemVenda::getValorTotal)
					.reduce(BigDecimal::add)
					.get();
		
		venda.setValorTotal(calcularValorTotal(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto()));
		
		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega()));
		}
				
		repository.save(venda);
	}

	/*
	 * Em um programa de itens de pedido é necessário calcular o valor total antes de gravar 
	 * 
	 * */
	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		BigDecimal valorTotal = 
				valorTotalItens
					.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
					.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		return valorTotal;
	}

}
