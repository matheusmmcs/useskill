package br.ufpi.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Component
public class TesteRepositoryImpl extends Repository<Teste, Long> implements
        TesteRepository {

    public TesteRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Teste testCriado(Long idUsuario, Long idTeste) {
    	Usuario usuario= new Usuario();
    	usuario.setId(idUsuario);
        Query query = entityManager.createQuery("SELECT t FROM Teste t WHERE t.usuarioCriador= :usuarioCriador AND t.id= :idteste");
        query.setParameter("idteste", idTeste);
        query.setParameter("usuarioCriador", usuario);
        return (Teste) query.getSingleResult();
    }
}
