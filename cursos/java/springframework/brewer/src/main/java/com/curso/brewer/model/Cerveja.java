package com.curso.brewer.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="cerveja")
public class Cerveja {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "O campo SKU é obrigatório''")
	private String sku;
	@NotBlank(message = "O campo nome é obrigatório")
	private String nome;
	@Size(min=1, max=50, message="A tamanho da descrição deve estar entre 1 e 50")
	private String descricao;
	
	private BigDecimal teorAlcoolico;
	
	private BigDecimal comissao;
	
	private Integer quantidadeEstoque;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
