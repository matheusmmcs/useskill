package br.ufpi.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;

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

  
}
