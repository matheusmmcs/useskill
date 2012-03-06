package br.ufpi.repositories;


import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Pergunta;
import javax.persistence.EntityManager;
@Component
public class PerguntaRepositoryImpl  extends Repository<Pergunta, Long> implements
PerguntaRepository {

    public PerguntaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
