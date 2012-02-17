package br.ufpi.repositories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.util.Criptografa;

@Component
public class UsuarioRepositoryImpl extends Repository<Usuario, Long> implements
		UsuarioRepository {

	UsuarioRepositoryImpl(Session session) {
		super(session);
	}

	@Override
	public Usuario logar(String email, String senha) {
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("senha", Criptografa.criptografar(senha)));
		return (Usuario) criteria.uniqueResult();
	}

	@Override
	public Usuario findConfirmacaoEmail(String confirmacaoEmail) {
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("confirmacaoEmail", confirmacaoEmail));
		return (Usuario) criteria.uniqueResult();
	}

	@Override
	public List<Teste> findTesteCriados(Usuario usuario) {
		Criteria criteria = session.createCriteria(Teste.class);
		criteria.add(Restrictions.eq("usuarioCriador", usuario));
		return criteria.list();
	}

	@Override
	public List<Teste> findTestesParticipados(Usuario usuario) {
		return null;
	}

	@Override
	public Usuario findEmail(String email) {
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("email", email));
		return (Usuario) criteria.uniqueResult();
	}

	@Override
	public boolean isContainsEmail(String email) {
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("email", email));
		return criteria.uniqueResult() == null ? false : true;
	}

	@Override
	public boolean isContainConfirmacaoEmail(String confirmacaoEmail) {
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("confirmacaoEmail", confirmacaoEmail));
		return false;
	}

}
