package br.ufpi.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Convidado;

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


}
