package br.ufpi.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Teste;

/**
 * Criado para servir como base para todos os outros controllers
 *
 * @author Cleiton
 */
public class BaseController {
    protected final Result result;
    protected final Validator validator;
    protected final TesteView testeView;
    protected UsuarioLogado usuarioLogado;
    protected final ValidateComponente validateComponente;

    /**
     *Instancia do BaseController.
     * @param result
     * @param validator
     * @param testeView
     * @param usuarioLogado
     * @param validateComponente
     */
    public BaseController(Result result, Validator validator,
            TesteView testeView, UsuarioLogado usuarioLogado,
            ValidateComponente validateComponente) {
        super();
        this.result = result;
        this.validator = validator;
        this.testeView = testeView;
        this.usuarioLogado = usuarioLogado;
        this.validateComponente = validateComponente;
    }

    /**
	 * Instancia testeView para as paginas que não a instanciam. Isto é feito pq
	 * na view precisa saber o identificador do teste que esta sendo usado para
	 * poder redirecionar para o teste que esta sendo usado.
	 * 
	 * @param idTeste
	 *            identificador do teste que esta sendo usado na view
	 */
	public void instanceIdTesteView(Long idTeste) {
		Teste teste = new Teste();
		teste.setId(idTeste);
		testeView.setTeste(teste);
	}
}
