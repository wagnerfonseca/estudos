package com.curso.brewer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cidade")
public class Cidade implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	private String nome;
	
	/*fetch = FetchType.LAZY -> não precisa inicializar estado
	 * Mas em determinado momento na aplicação, sera necessário trazer uma consulta com o obejto inicializados 
	 * todos o obejtos que estão relacionados com Cidade.
	 * Para resolver esse problema, crie uma consulta criteria e crie um alias para o realcionamento
	 * */	 
	@ManyToOne(fetch = FetchType.LAZY) // coloca na forma em que se lê (uma cidade tem "many" estados, um estado tem "one" cidade)
	@JoinColumn(name = "codigo_estado") // Nome do campo (na tabela cidade) utilizado para fazer FK com tabela Estado
	@JsonIgnore // ignorar no momento de montar o objeto JSON
	private Estado estado;
	
	public Boolean temEstado() {
		return this.estado != null;
	}
		
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
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
		Cidade other = (Cidade) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
