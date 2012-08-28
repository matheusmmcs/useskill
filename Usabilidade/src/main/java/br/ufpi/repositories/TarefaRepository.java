package br.ufpi.repositories;

import br.ufpi.models.Tarefa;
import br.ufpi.models.vo.TarefaVO;

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
}
