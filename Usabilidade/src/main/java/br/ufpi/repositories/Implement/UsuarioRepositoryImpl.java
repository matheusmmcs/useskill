package br.ufpi.repositories.Implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.UsuarioRepository;

@Component
public class UsuarioRepositoryImpl extends Repository<Usuario, Long> implements
		UsuarioRepository {

	public UsuarioRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Usuario logar(String email, String senha) {
		Query query = entityManager.createNamedQuery("Usuario.EmailSenha");
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		try {

			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Usuario findConfirmacaoEmail(String confirmacaoEmail) {
		Query createNativeQuery = entityManager
				.createNamedQuery("Usuario.findByConfirmacaoEmail");
		createNativeQuery.setParameter("confirmacaoEmail", confirmacaoEmail);
		try {

			return (Usuario) createNativeQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Teste> findTesteCriados(Usuario usuario) {
		Query query = entityManager
				.createQuery("select t from Teste t where t.usuarioCriador= :usuarioCriador");
		query.setParameter("usuarioCriador", usuario);
		try {

			@SuppressWarnings("unchecked")
			List<Teste> list = query.getResultList();
			return list;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Teste> findTestesParticipados(Usuario usuario) {
		return null;
	}

	@Override
	public Usuario findEmail(String email) {
		Query createNamQuery = entityManager
				.createNamedQuery("Usuario.findByEmail");
		createNamQuery.setParameter("email", email);
		try {
			return (Usuario) createNamQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean isContainsEmail(String email) {
		return findEmail(email) == null ? false : true;
	}

	@Override
	public boolean isContainConfirmacaoEmail(String confirmacaoEmail) {
		return this.findConfirmacaoEmail(confirmacaoEmail) == null ? false
				: true;
	}

	@Override
	public Usuario load(Usuario usuario) {
		Query createNativeQuery = entityManager
				.createNamedQuery("Usuario.findById");
		createNativeQuery.setParameter("id", usuario.getId());
		try {
			return (Usuario) createNativeQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teste> findTesteNaoLiberadosOrdenadorData(Usuario usuario) {
		Query query = entityManager
				.createNamedQuery("Usuario.TesteCriado.Nao.Liberado.Organizado.Id");
		query.setParameter("usuarioCriador", usuario);
		try {
			return (List<Teste>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teste> findTestesConvidados(Long idUsuario) {
		Query query = entityManager.createNamedQuery("Convidado.Teste");
		query.setParameter("usuario", idUsuario);
//		query.setParameter("realizou", null);
		return query.getResultList();
	}



	

}
