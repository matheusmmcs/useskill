package br.ufpi.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
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
        Query query = entityManager.createNativeQuery("SELECT * FROM Teste t WHERE t.usuarioCriador_id= :usuarioCriador AND t.id= :idteste", Teste.class);
        query.setParameter("idteste", idTeste);
        query.setParameter("usuarioCriador", idUsuario);
        return (Teste) query.getSingleResult();
    }
}
