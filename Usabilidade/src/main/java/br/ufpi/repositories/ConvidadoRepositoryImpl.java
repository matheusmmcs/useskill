package br.ufpi.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Convidado;
import br.ufpi.models.Teste;

@Component
public class ConvidadoRepositoryImpl extends Repository<Convidado, Long>
		implements ConvidadoRepository {

	protected ConvidadoRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void salvarLista(List<Convidado> convidados) {

		for (Convidado convidado : convidados) {

			entityManager.persist(convidado);
		}

	}

	@Override
	public void convidarUsuarios(List<Long> idUsuarios, Long idTeste) {
		List<Convidado> convidados = criarListaConvidado(idUsuarios, idTeste);
		salvarLista(convidados);
	}

	@Override
	public void desconvidarUsuarios(List<Long> idUsuarios, Long idTeste) {
		List<Convidado> convidados = criarListaConvidado(idUsuarios, idTeste);
		for (Convidado convidado : convidados) {
			super.destroy(convidado);
		}
	}

	private List<Convidado> criarListaConvidado(List<Long> idUsuarios,
			Long idTeste) {
		List<Convidado> convidados = new ArrayList<Convidado>();
		for (Long idUsuario : idUsuarios) {
			Convidado convidado = new Convidado(idUsuario, idTeste);
			convidados.add(convidado);
		}
		return convidados;
	}

	@Override
	public Teste getTesteConvidado(Long testeId, Long usuarioId) {
		Query namedQuery = super.entityManager
				.createNamedQuery("Convidado.UsuarioFoiConvidado");
		namedQuery.setParameter("teste", testeId);
		namedQuery.setParameter("usuario", usuarioId);
		try {
			return (Teste) namedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

}
