package com.curso.brewer.model;

import java.math.BigDecimal;

/**
 * Aula 23.3 - Itens de venda
 * #CadastroMestreDetalhe
 * */

public class ItemVenda {
	
	private Long codigo;
	private Integer quantidade;
	private BigDecimal valorUnitario;
	private Cerveja cerveja;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public Cerveja getCerveja() {
		return cerveja;
	}
	public void setCerveja(Cerveja cerveja) {
		this.cerveja = cerveja;
	}
	
	/** retorna o valor total de cada item da venda */
	public BigDecimal  getValorTotal() {
		return this.valorUnitario.multiply(new BigDecimal(this.quantidade));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVenda other = (ItemVenda) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	
	
}
