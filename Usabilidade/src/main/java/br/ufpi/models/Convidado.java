/**
 *
 */
package br.ufpi.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Cleiton
 *
 */
@Entity
@NamedQueries({@NamedQuery(name="Convidado.Teste",query="SELECT c.chaveComposta.teste FROM Convidado AS c Where c.realizou= :realizou  And c.chaveComposta.participante.id= :usuario")})
public class Convidado {

    private Boolean realizou;
    @EmbeddedId
    private UsuarioTestePK chaveComposta;

    public boolean isRealizou() {
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
}
