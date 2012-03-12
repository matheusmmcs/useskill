package br.ufpi.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

@Component
public class UsuarioRepositoryImpl extends Repository<Usuario, Long> implements
        UsuarioRepository {

    UsuarioRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Usuario logar(String email, String senha) {
        Query createNativeQuery = entityManager.createNamedQuery("Usuario.EmailSenha");
        createNativeQuery.setParameter("email", email);
        createNativeQuery.setParameter("senha", senha);        
        return (Usuario) createNativeQuery.getSingleResult();
    }

    @Override
    public Usuario findConfirmacaoEmail(String confirmacaoEmail) {
        Query createNativeQuery = entityManager.createNamedQuery("Usuario.findByConfirmacaoEmail");
        createNativeQuery.setParameter("confirmacaoEmail", confirmacaoEmail);
        return (Usuario) createNativeQuery.getSingleResult();
    }

    @Override
    public List<Teste> findTesteCriados(Usuario usuario) {
        Query query = entityManager.createQuery("select t from Teste t where t.usuarioCriador= :usuarioCriador");
        query.setParameter("usuarioCriador", usuario);
        return (List<Teste>) query.getResultList();
    }

    @Override
    public List<Teste> findTestesParticipados(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario findEmail(String email) {
        Query createNativeQuery = entityManager.createNativeQuery("Usuario.findByEmail", Usuario.class);
        createNativeQuery.setParameter("email", email);
        return (Usuario) createNativeQuery.getSingleResult();
    }

    @Override
    public boolean isContainsEmail(String email) {
        return findEmail(email) == null ? false : true;
    }

    @Override
    public boolean isContainConfirmacaoEmail(String confirmacaoEmail) {
        return this.findConfirmacaoEmail(confirmacaoEmail) == null ? false : true;
    }

    @Override
    public Usuario load(Usuario usuario) {
        Query createNativeQuery = entityManager.createNativeQuery("Usuario.findById", Usuario.class);
        createNativeQuery.setParameter("id", usuario.getId());
        return (Usuario) createNativeQuery.getSingleResult();
    }

    @Override
    public List<Teste> findTesteCriadosOrderData(Usuario usuario) {
        Query query = entityManager.createQuery("SELECT t FROM Teste AS t WHERE t.usuarioCriador= :usuarioCriador ORDER BY t.id DESC ");
        query.setParameter("usuarioCriador", usuario);
        return query.getResultList();
    }
}
