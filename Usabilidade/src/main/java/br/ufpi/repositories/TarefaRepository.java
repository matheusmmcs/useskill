package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.vo.TarefaVO;

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
     *Obtem o roteiro da  Tarefa 
     * @param idTarefa Identificador da tarefa que esta procurando
     * @param idTeste Identificador do teste ao qual a tarefa pertence
     * @return
     */
    String getRoteiro(Long idTarefa, Long idTeste);
    
    /**
     *Obtem o roteiro da  Tarefa 
     * @param idTarefa Identificador da tarefa que esta procurando
     * @param idTeste Identificador do teste ao qual a tarefa pertence
     * @return
     */
    TarefaVO getTarefaVO(Long idTarefa, Long idTeste);
    
    

    /**
     * Obtem a tarefa caso ela não pertença a um teste não realizado
     *
     * @param idTarefa Identificador da tarefa que esta procurando
     * @param idTeste Identificador do teste ao qual a tarefa pertence e que ainda não tenha sido realizado
     * @param idUsuario Identificador do usuario que é dono do teste
     * @return
     */
    Tarefa perteceTesteNaoRealizado(Long idTarefa, Long idTeste, Long idUsuario);
    /**
     * Obtem o fluxo de uma determinada tarefa
     * @param testeId identificador do teste que a tarefa pertence
     * @param tarefaId identificador da tarefa
     * @param usarioId identificador do usuario que realizou o fluxo
     * @param usuarioCriadorId identificador do usuario que criou o teste
     * @return o fluxo que o usuario realizou para criar o teste
     */
    Fluxo getFluxo(Long testeId, Long tarefaId, Long usarioId,Long usuarioCriadorId);
}
