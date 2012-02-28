package br.ufpi.repositories;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

@Component
public class TesteRepositoryImpl extends Repository<Teste, Long> implements
		TesteRepository {

	protected TesteRepositoryImpl(Session session) {
		super(session);
	}
	@Override
	public Teste testCriado(Long idUsuario, Long idTeste) {
		Usuario usuario= new Usuario();
		usuario.setId(idUsuario);
		Criteria criteria = session.createCriteria(Teste.class);
		criteria.add(Restrictions.eq("usuarioCriador", usuario));
		criteria.add(Restrictions.eq("id", idTeste));
		return (Teste) criteria.uniqueResult();
	}
}
