package br.ufpi.componets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
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
		validator.checking(new Validations(){{
			that(false, "nao.proprietario", mensagem);
		}});
		validator.onErrorRedirectTo(LoginController.class).logado(1);
	}

	/**
	 * Redireciona para termino de Teste
	 */
	public void redirecionarTermino() {
		validator.checking(new Validations(){{
			that(false, "termino", "termino");
		}});
		validator.onErrorRedirectTo(TesteParticiparController.class).termino();
	}
	
	public void validarNotNull(final Object obj, final String msg) {
		validator.checking(new Validations(){{
				that((obj != null), msg, msg);
		}});
	}

	public void validarString(final String valorCampo, final String nameCampo) {
		validator.checking(new Validations(){{
				that((valorCampo!=null && !valorCampo.isEmpty() && !valorCampo.trim().equals("")), "campo." + nameCampo + ".obrigatorio", "campo.obrigatorio", i18n(nameCampo));
		}});
	}

	public void validarId(final Long idTeste) {
		validator.checking(new Validations(){{ 
			that((idTeste!=null ), "campo.form.alterado", "campo.form.alterado"); 
		}});
		validator.onErrorRedirectTo(LoginController.class).logado(1);
	}
	
	public void validarURL(final String url){
		boolean match = false;
		try {
            Pattern patt = Pattern.compile("\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            Matcher matcher = patt.matcher(url);
            match = matcher.matches();
        } catch (RuntimeException e) {
        	match = false;
	    }
		final boolean valida = match;
		validator.checking(new Validations(){{ 
			that(valida, "tarefa.url.invalida", "tarefa.url.invalida"); 
		}});
		
	}
	
	/**
	 * Apenas analisa se o teste não é nulo
	 * @param object Objeto a ser verificado
	 */
	public void validarObjeto(final Object object) {
		validator.checking(new Validations(){{
			that((object!=null ), "campo.form.alterado", "campo.form.alterado");
		}});	
		validator.onErrorRedirectTo(LoginController.class).logado(1);
	}
	
	/**
	 * Apenas analisa se o teste não é nulo
	 * @param object Objeto a ser verificado
	 */
	public void validarObjetoJson(final Object object) {
		validator.checking(new Validations(){{
			that((object!=null ), "campo.form.alterado", "campo.form.alterado");
		}});	
		validator.onErrorUse(Results.json()).from(validator.getErrors(),"errors").serialize();
	}
	
	/**
	 * Gera erro informando que campo forme foi alterado
	 */
	public void gerarErroCampoAlterado(){
		validator.checking(new Validations(){{
			that(false, "campo.form.alterado", "campo.form.alterado");
		}});	
	}

	public void gerarErro(final String category, final String mensagem) {
		validator.checking(new Validations(){{
			that(false, category, mensagem);
		}});	
	}
	
	public void validarEquals(final Object object, final Object object2, final String mensagem) {
		validator.checking(new Validations(){{
			that((object.equals(object2)), mensagem, mensagem);
		}});	
		validator.onErrorUse(Results.json()).from(validator.getErrors(),"errors").serialize();
	}
	
}
