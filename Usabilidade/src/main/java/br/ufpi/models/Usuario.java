/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.Criptografa;

/**
 * 
 * @author Cleiton
 */
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "{campo.obrigatorio}")
	private String nome;
	@ElementCollection
	@Column(length = 15)
	private List<String> telefones;
	@NotNull
	@Column(length = 40)
	private String senha;
	@NotNull
	@Column(length = 150, unique = true)
	private String email;
	private boolean emailConfirmado;
	@Column(length = 32, unique = true)
	private String confirmacaoEmail;
	@OneToMany(mappedBy = "usuarioCriador", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Teste> testesCriados;
	@ManyToMany(mappedBy = "usuariosParticipantes",fetch=FetchType.LAZY)
	private List<Teste> testesParticipados;
	@Transient
	private UsuarioRepository usuarioRepository;


	public Usuario(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	public Usuario(String nome, String email, String senha,
			boolean emailConfirmado, String confirmacaoEmail) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.emailConfirmado = emailConfirmado;
		this.confirmacaoEmail = confirmacaoEmail;
	}
	
	public Usuario() {
	}

	private UsuarioRepository getRepository() {
		if (usuarioRepository == null) {
			throw new IllegalStateException(
					"Repository was not set. You should inject it first");
		}
		return usuarioRepository;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailConfirmado() {
		return emailConfirmado;
	}

	public void setEmailConfirmado(boolean emailConfirmado) {
		this.emailConfirmado = emailConfirmado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	public List<Teste> getTestesCriados() {
		return testesCriados;
	}

	public void setTestesCriados(List<Teste> testesCriados) {
		this.testesCriados = testesCriados;
	}

	public List<Teste> getTestesParticipados() {
		return testesParticipados;
	}

	public void setTestesParticipados(List<Teste> testesParticipados) {
		this.testesParticipados = testesParticipados;
	}

	public String getConfirmacaoEmail() {
		return confirmacaoEmail;
	}

	public void setConfirmacaoEmail(String confirmacaoEmail) {
		this.confirmacaoEmail = confirmacaoEmail;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", telefones="
				+ telefones + ", senha=" + senha + ", email=" + email
				+ ", emailConfirmado=" + emailConfirmado
				+ ", confirmacaoEmail=" + confirmacaoEmail + "]";
	}

	public void criptografarSenhaGerarConfimacaoEmail() {
		senha = Criptografa.criptografar(senha);
		do{
			confirmacaoEmail=Criptografa.criptografar(new Date().toString());
		}while(getRepository().isContainConfirmacaoEmail(confirmacaoEmail));
	}
}
