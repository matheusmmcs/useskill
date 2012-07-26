package br.ufpi.repositories;

import br.ufpi.models.RespostaAlternativa;
import java.util.List;

public interface RespostaAlternativaRepository {

	void create(RespostaAlternativa entity);

	RespostaAlternativa update(RespostaAlternativa entity);

	void destroy(RespostaAlternativa entity);

	RespostaAlternativa find(Long id);

	List<RespostaAlternativa> findAll();
}
