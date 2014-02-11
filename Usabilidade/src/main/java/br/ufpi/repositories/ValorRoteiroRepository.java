/**
 * 
 */
package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Tarefa;
import br.ufpi.models.Usuario;
import br.ufpi.models.enums.SituacaoDeUsoEnum;
import br.ufpi.models.roteiro.ValorRoteiro;

/**
 * @author Matheus
 *
 */
public interface ValorRoteiroRepository {
	
	void create(ValorRoteiro entity);

	ValorRoteiro update(ValorRoteiro entity);

	void destroy(ValorRoteiro entity);

	ValorRoteiro find(Long id);

	List<ValorRoteiro> findAll();
	
	List<ValorRoteiro> findValorByVariavelAndUsuarioInSituacao(Long idUsuario, Long idVariavel, SituacaoDeUsoEnum situacao);
	
	List<ValorRoteiro> findValorByTarefaAndUsuarioInSituacao(Long idUsuario, Long idTarefa, SituacaoDeUsoEnum situacao);
	
	List<ValorRoteiro> findValorByTesteAndUsuarioInSituacao(Long idUsuario, Long idTeste, SituacaoDeUsoEnum situacao);
	
	List<ValorRoteiro> findValorByVariavelInSituacao(Long idVariavel, SituacaoDeUsoEnum situacao);
}
