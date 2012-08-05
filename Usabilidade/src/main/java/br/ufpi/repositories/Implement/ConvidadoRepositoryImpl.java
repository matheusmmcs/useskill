package br.ufpi.repositories.Implement;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Convidado;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.ConvidadoVO;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.Repository;

@Component
public class ConvidadoRepositoryImpl extends Repository<Convidado, Long>
		implements ConvidadoRepository {

	public ConvidadoRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void salvarLista(List<Convidado> convidados) {

		for (Convidado convidado : convidados) {

			entityManager.persist(convidado);
		}

	}

	@Override
	public void convidarUsuarios(List<Long> idUsuarios, Long idTeste,
			TipoConvidado tipoconvidado) {
		List<Convidado> convidados = criarListaConvidado(idUsuarios, idTeste,
				tipoconvidado);
		salvarLista(convidados);
	}

	@Override
	public void desconvidarUsuarios(List<Long> idUsuarios, Long idTeste) {
		List<Convidado> convidados = criarListaConvidado(idUsuarios, idTeste);
		for (Convidado convidado : convidados) {
			super.destroy(convidado);
		}
	}

	/**
	 * Obtem uma lista de convidados
	 * 
	 * @param idUsuarios
	 *            identificadores que os usuarios foram convidados
	 * @param idTeste
	 *            identificador do teste que os usuarios foram convidados
	 * @param tipoConvidado
	 *            tipo de convite que o usuario foi convidado
	 * @return uma lista de convidados
	 */
	private List<Convidado> criarListaConvidado(List<Long> idUsuarios,
			Long idTeste, TipoConvidado tipoConvidado) {
		List<Convidado> convidados = new ArrayList<Convidado>();
		for (Long idUsuario : idUsuarios) {
			Convidado convidado = new Convidado(idUsuario, idTeste);
			convidado.setTipoConvidado(tipoConvidado);
			convidados.add(convidado);
		}
		return convidados;
	}

	/**
	 * 
	 * @param idUsuarios
	 * @param idTeste
	 * @return
	 */
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
	public ConvidadoVO getTesteConvidado(Long testeId, Long usuarioId) {
		Query namedQuery = super.entityManager
				.createNamedQuery("Convidado.UsuarioFoiConvidado");
		namedQuery.setParameter("teste", testeId);
		namedQuery.setParameter("usuario", usuarioId);
		try {
			return (ConvidadoVO) namedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public Convidado find(Long testeId, Long usuarioId) {
		Query namedQuery = super.entityManager
				.createNamedQuery("Convidado.find");
		namedQuery.setParameter("teste", testeId);
		namedQuery.setParameter("usuario", usuarioId);
		try {
			return (Convidado) namedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
