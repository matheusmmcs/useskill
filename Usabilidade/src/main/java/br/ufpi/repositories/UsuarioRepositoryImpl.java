package br.ufpi.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;



import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import javax.persistence.Query;

@Component
public class UsuarioRepositoryImpl extends Repository<Usuario, Long> implements
        UsuarioRepository {

    UsuarioRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Usuario logar(String email, String senha) {
        Query createNativeQuery = entityManager.createNamedQuery("Usuario.EmailSenha", Usuario.class);
        createNativeQuery.setParameter("email", email);
        createNativeQuery.setParameter("senha", senha);

        return (Usuario) createNativeQuery.getSingleResult();
    }

    @Override
    public Usuario findConfirmacaoEmail(String confirmacaoEmail) {
        Query createNativeQuery = entityManager.createNamedQuery("Usuario.findByConfirmacaoEmail", Usuario.class);
        createNativeQuery.setParameter("confirmacaoEmail", confirmacaoEmail);
        return (Usuario) createNativeQuery.getSingleResult();
    }

    @Override
    public List<Teste> findTesteCriados(Usuario usuario) {
        Query createNativeQuery = entityManager.createNativeQuery("SELECT * FROM Teste t WHERE t.usuarioCriador_id= :usuarioCriador", Teste.class);
        createNativeQuery.setParameter("usuarioCriador", usuario);
        return createNativeQuery.getResultList();
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
        Query query = entityManager.createNativeQuery("SELECT * FROM Teste AS t WHERE t.usuarioCriador_id= :usuarioCriador_id ORDER BY t.id DESC ", Teste.class);
        query.setParameter("usuarioCriador_id", usuario);
        return query.getResultList();
    }
}
