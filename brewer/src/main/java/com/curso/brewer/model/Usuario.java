package com.curso.brewer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.curso.brewer.validation.AtributoConfirmacao;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere")
@Entity
@Table(name = "usuario")
@DynamicUpdate // Ele so faz o update para o campo alterado
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;	
	
	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;
	
	//@NotNull(message = "Data de nascimento é obrigatório")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	private String senha;
	@Transient
	private String confirmacaoSenha;	
	private Boolean ativo;
	
	@Size(min = 1, message = "Selecione ao menos um grupo")
	//Todas anotacao ManyToMany é por default fetch lazy
	@ManyToMany // Seguindo sempre na ordem que se lê Um "Usuario" tem "MUITOS" Grupos e um "Grupo" pode ter "MUITOS" usuários
	// Como existe uma tabela para fazer INTERSEÇÃO entre os GRUPO e USUARIO Usa a anotação @JoinTable
	@JoinTable(
			name = "usuario_grupo",  //NOME DA TABELA 
			joinColumns = @JoinColumn(name = "codigo_usuario"), //NOME DA CHAVE/COLUNA NA TABELA (USUARIO_GRUPO) QUE FAZ LIGACAO COM a classe "Usuário"
			inverseJoinColumns = @JoinColumn(name = "codigo_grupo") // NOME DA CHAVE/COLUNA NA TABELA (USUARIO_GRUPO) que faz ligacao com a classe de "Grupo"			
	)
	private List<Grupo> grupos;	
	
	
	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = senha;
	}
	
	public boolean isNovo() {
		return codigo == null;
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
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
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
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
