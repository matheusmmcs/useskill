package br.ufpi.componets.vraptor;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@ApplicationScoped
@Component
public class AnnotationConfigurationFactory implements ComponentFactory<Configuration> {

	private static List<Class<?>> entities = new ArrayList<Class<?>>();

	private Configuration configuration;

	public AnnotationConfigurationFactory() {
		configuration = new Configuration();
	}

	@PostConstruct
	public void addEntitiesToConfiguration() {
		configuration.configure();
		for (Class<?> entity : entities) {
			configuration.addAnnotatedClass(entity);
		}
	}

	static void addEntity(Class<?> entity) {
		entities.add(entity);
	}

	static List<Class<?>> getEntities() {
		return entities;
	}

	@Override
	public Configuration getInstance() {
		return null;
	}

}