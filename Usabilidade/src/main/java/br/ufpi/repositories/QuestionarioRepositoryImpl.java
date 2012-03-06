/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Questionario;
import javax.persistence.EntityManager;

/**
 *
 * @author Cleiton
 */
@Component
public class QuestionarioRepositoryImpl extends Repository<Questionario, Long> implements QuestionarioRepository{

    public QuestionarioRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

   
    
}
