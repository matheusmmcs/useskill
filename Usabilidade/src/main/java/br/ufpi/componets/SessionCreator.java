package br.ufpi.componets;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.vidageek.mirror.dsl.Mirror;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

/**
 * Opens and closes a Session from a SessionFactory and
 * provides it to container
 * @author Lucas Cavalcanti
 */
@RequestScoped
@Component
public class SessionCreator implements ComponentFactory<Session> {

	private final SessionFactory factory;
	private Session session;
	private Session lazySession;
	private final RepositoryInterceptor interceptor;
	private final Proxifier proxifier;

	public SessionCreator(SessionFactory factory, RepositoryInterceptor interceptor, Proxifier proxifier) {
		this.factory = factory;
		this.interceptor = interceptor;
		this.proxifier = proxifier;
	}

	@PostConstruct
	public void create() {
		this.session = proxifier.proxify(Session.class, new MethodInvocation<Session>() {
			public Object intercept(Session proxy, Method method, Object[] args, SuperMethod superMethod) {
				return new Mirror().on(getLazySession()).invoke().method(method).withArgs(args);
			}
		});
	}

	private Session getLazySession() {
		if (lazySession == null) {
			lazySession = factory.openSession(interceptor);
		}
		return lazySession;
	}

	public Session getInstance() {
		return session;
	}

	@PreDestroy
	public void destroy() {
		if (lazySession != null) {
			this.lazySession.close();
			this.lazySession = null;
		}
	}

}