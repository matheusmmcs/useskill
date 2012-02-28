/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.repositories;

import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import java.util.List;

/**
 *
 * @author Cleiton
 */
public interface QuestionarioRepository {
  void create(Questionario entity);

	Questionario update(Questionario entity);

	void destroy(Questionario entity);

	Questionario find(Long id);

	List<Questionario> findAll();
}
