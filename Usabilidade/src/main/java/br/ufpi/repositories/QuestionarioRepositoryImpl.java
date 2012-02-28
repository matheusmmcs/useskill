/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Questionario;
import org.hibernate.Session;

/**
 *
 * @author Cleiton
 */
@Component
public class QuestionarioRepositoryImpl extends Repository<Questionario, Long> implements QuestionarioRepository{

    public QuestionarioRepositoryImpl(Session session) {
        super(session);
    }
    
}
