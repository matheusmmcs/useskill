package br.ufpi.annotation;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.controllers.LoginController;

@Intercepts
public class LoginIntercerptor implements Interceptor {
	private Result result;
	private UsuarioLogado usuarioLogado;
	

	public LoginIntercerptor(Result result, UsuarioLogado usuarioLogado) {
		super();
		this.result = result;
		this.usuarioLogado = usuarioLogado;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		if (usuarioLogado.islogado()){
			stack.next(method, resourceInstance);
		}else {
			result.redirectTo(LoginController.class).login(null);
		}

	}

	/**
	 * 
	 */
	@Override
	public boolean accepts(ResourceMethod method) {
		return (method.getMethod().isAnnotationPresent(Logado.class) || method.getResource().getType().isAnnotationPresent(Logado.class));
	}
}
