/**
 *
 */
package br.ufpi.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Cleiton
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Convidado.UsuarioFoiConvidado", query = "SELECT new br.ufpi.models.vo.ConvidadoVO(c.chaveComposta.teste,c.tipoConvidado) FROM Convidado AS c Where c.chaveComposta.participante.id= :usuario AND c.chaveComposta.teste.id= :teste And c.chaveComposta.teste.liberado= true And c.realizou= null"),
		@NamedQuery(name = "Convidado.find", query = "SELECT c FROM Convidado AS c Where c.chaveComposta.participante.id= :usuario AND c.chaveComposta.teste.id= :teste"),
		@NamedQuery(name = "Convidado.Teste", query = "SELECT c.chaveComposta.teste FROM Convidado AS c Where c.realizou is null  And c.chaveComposta.participante.id= :usuario And c.chaveComposta.teste.liberado= true"),
		@NamedQuery(name = "Convidado.Teste.Count", query = "SELECT count(*) FROM Convidado AS c Where c.realizou is :realiza  And c.chaveComposta.participante.id= :usuario And c.chaveComposta.teste.liberado= true"),
		@NamedQuery(name = "Convidado.Teste.Participado", query = "SELECT c.chaveComposta.teste FROM Convidado AS c Where c.realizou is true  And c.chaveComposta.participante.id= :usuario And c.chaveComposta.teste.liberado= true"),
		@NamedQuery(name = "Convidado.Teste.Participado.Count", query = "SELECT count(*) FROM Convidado AS c Where c.realizou is true  And c.chaveComposta.participante.id= :usuario And c.chaveComposta.teste.liberado= true"),
		@NamedQuery(name = "Convidado.Usuarios.Nao.Convidados", query = "Select u From Usuario as u Where u.id not in(SELECT c.chaveComposta.participante FROM Convidado AS c Where c.chaveComposta.teste.id= :teste)"),
		@NamedQuery(name = "Convidado.Usuarios.Nao.Convidados.Count", query = "Select count(*) From Usuario as u Where u.id not in(SELECT c.chaveComposta.participante FROM Convidado AS c Where c.chaveComposta.teste.id= :teste)"),
		@NamedQuery(name = "Convidado.Usuarios.Convidados", query = "SELECT new br.ufpi.models.vo.ConvidadoVO(c.chaveComposta.participante,c.tipoConvidado) FROM Convidado AS c Where c.chaveComposta.teste.id= :teste"),
		@NamedQuery(name = "Convidado.Usuarios.Convidados.Count", query = "SELECT count(*) FROM Convidado AS c Where c.chaveComposta.teste.id= :teste"),
		})
public class Convidado {

	private Boolean realizou;
	@Enumerated(EnumType.STRING)
	private TipoConvidado tipoConvidado;
	@EmbeddedId
	private UsuarioTestePK chaveComposta;
	public Convidado() {
		super();
	}
	public Convidado( UsuarioTestePK chaveComposta) {
		super();
		this.chaveComposta = chaveComposta;
	}
	public Convidado( Long usuarioId,Long testeId) {
		super();
		Usuario usuario= new Usuario();
		usuario.setId(usuarioId);
		Teste teste= new Teste();
		teste.setId(testeId);
		this.chaveComposta = new UsuarioTestePK(usuario,teste);
	}
	public Boolean isRealizou() {
		return realizou;
	}

	public void setRealizou(boolean realizou) {
		this.realizou = realizou;
	}

	public UsuarioTestePK getChaveComposta() {
		return chaveComposta;
	}

	public void setChaveComposta(UsuarioTestePK chaveComposta) {
		this.chaveComposta = chaveComposta;
	}
	
	@Override
	public String toString() {
		return "Convidado [realizou=" + realizou + ", tipoConvidado="
				+ tipoConvidado + ", chaveComposta=" + chaveComposta + "]";
	}
	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}
	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}
	
}
