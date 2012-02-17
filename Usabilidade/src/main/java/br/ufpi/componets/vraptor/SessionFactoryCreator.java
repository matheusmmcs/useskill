package br.ufpi.componets.vraptor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@ApplicationScoped
public class SessionFactoryCreator implements ComponentFactory<SessionFactory> {

	private SessionFactory factory;
	private final Configuration configuration;

	public SessionFactoryCreator(Configuration configuration) {
		this.configuration = configuration;
	}

	@PostConstruct
	public void create() {
		factory = configuration.buildSessionFactory();
	}

	public SessionFactory getInstance() {
		return factory;
	}

	@PreDestroy
	public void destroy() {
		factory.close();
	}

}