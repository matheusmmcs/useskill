package br.ufpi.componets;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.controllers.LoginController;
import br.ufpi.controllers.TesteParticiparController;

/**
 * Usado para conter os redirects de objetos que o usuario tentar acessar e não
 * for dono. E outros tipos de validação que sirva para a maioria dos
 * Controllers
 * 
 * @author Cleiton
 * 
 */

@Component
public class ValidateComponente {
	private final Validator validator;

	/**
	 * Instancia o validateComponent passando o validator do Vraptor
	 * 
	 * @param validator
	 */
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

	public void validarString(final String valorCampo, final String nameCampo) {
		validator.checking(new Validations() {

			{
				that((valorCampo!=null && !valorCampo.isEmpty() && !valorCampo.trim().equals("")), "campo." + nameCampo + ".obrigatorio",
						"campo.obrigatorio", i18n(nameCampo));
			}
		});
	}

	public void validarId(final Long idTeste) {
		validator.checking(new Validations() {

			{
				that((idTeste!=null ), "campo.form.alterado",
						"campo.form.alterado");
			}
		});	
		validator.onErrorRedirectTo(LoginController.class).logado();
	}
	/**
	 * Apenas analisa se o teste não é nulo
	 * @param teste Objeto a ser verificado
	 */
	public void validarObjeto(final Object teste) {
		validator.checking(new Validations() {

			{
				that((teste!=null ), "campo.form.alterado",
						"campo.form.alterado");
			}
		});	
		validator.onErrorRedirectTo(LoginController.class).logado();
	}
	/**
	 * Gera erro informando que campo forme foi alterado
	 */
	public void gerarErroCampoAlterado(){
		validator.checking(new Validations() {

			{
				that(false, "campo.form.alterado",
						"campo.form.alterado");
			}
		});	
	}

	public void gerarErro(final String category, final String mensagem) {
		validator.checking(new Validations() {

			{
				that(false, category,
						mensagem);
			}
		});	
		
	}
	
}
