package br.ufpi.repositories;

import br.ufpi.models.Tarefa;
import java.util.List;

public interface TarefaRepository {

    void create(Tarefa entity);

    Tarefa update(Tarefa entity);

    void destroy(Tarefa entity);

    Tarefa find(Long id);

    List<Tarefa> findAll();

    /**
     *Obtem a Tarefa caso ela pertença a um teste
     * @param idTarefa Identificador da tarefa que esta procurando
     * @param idTeste Identificador do teste ao qual a tarefa pertence
     * @param idUsuario Identificador do usuario que é dono do teste
     * @return
     */
    Tarefa pertenceTeste(Long idTarefa, Long idTeste, Long idUsuario);

    /**
     * Obtem a tarefa caso ela não pertença a um teste não realizado
     *
     * @param idTarefa Identificador da tarefa que esta procurando
     * @param idTeste Identificador do teste ao qual a tarefa pertence e que ainda não tenha sido realizado
     * @param idUsuario Identificador do usuario que é dono do teste
     * @return
     */
    Tarefa perteceTesteNaoRealizado(Long idTarefa, Long idTeste, Long idUsuario);
}
