package br.ufpi.annotation;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.controllers.LoginController;
/**
 * Usado para verificar se método esta anotado com AcessoLivre
 * @author Matheus
 *
 */
@Intercepts
public class LivreIntercerptor implements Interceptor {
	private Result result;
	private UsuarioLogado usuarioLogado;
	

	public LivreIntercerptor(Result result, UsuarioLogado usuarioLogado) {
		super();
		this.result = result;
		this.usuarioLogado = usuarioLogado;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		if (!usuarioLogado.islogado()){
			stack.next(method, resourceInstance);
		} else {
			result.redirectTo(LoginController.class).logado(1);
		}

	}

	/**
	 * 
	 */
	@Override
	public boolean accepts(ResourceMethod method) {
		return (method.getMethod().isAnnotationPresent(AcessoLivre.class) || method.getResource().getType().isAnnotationPresent(AcessoLivre.class));
	}
}
