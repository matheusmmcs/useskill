package br.ufpi.repositories;

import br.ufpi.models.Alternativa;
import java.util.List;


/**
 * Interface para ser usada para manipulação de Alternativas
 * @author Cleiton
 */
public interface AlternativaRepository {
    void create(Alternativa entity);

    Alternativa update(Alternativa entity);

        void destroy(Alternativa entity);

        Alternativa find(Long id);

        List<Alternativa> findAll();

}
