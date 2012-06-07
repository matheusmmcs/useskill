package br.ufpi.componets;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.controllers.LoginController;
import br.ufpi.controllers.TesteParticiparController;

/**
 * Usado para conter os redirects de objetos que o usuario tentar acessar e não
 * for dono e outros tipos de validação que sirva para a maioria dos Controllers
 * 
 * @author Cleiton
 * 
 */

@Component
public class ValidateComponente {
	private final Validator validator;

	public ValidateComponente(Validator validator) {
		super();
		this.validator = validator;
	}

	/**
	 * Redireciona usuario para a pagina de Logado
	 * 
	 * @param mensagem
	 *            texto que esta na mensagem.properties
	 */
	public void redirecionarHome(final String mensagem) {
		validator.checking(new Validations() {
			{
				that(false, "nao.proprietario", mensagem);
			}
		});
		validator.onErrorRedirectTo(LoginController.class).logado();
	}
/**
 * Redireciona para termino de Teste
 */
	public void redirecionarTermino() {
		validator.checking(new Validations() {
			{
				that(false, "termino", "termino");
			}
		});
		validator.onErrorRedirectTo(TesteParticiparController.class).termino();
	}
}
