package br.ufpi.componets.vraptor;


import java.lang.reflect.Method;
import javax.annotation.PreDestroy;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

/**
* <b>JIT (Just-in-Time) {@link Session} Creator</b> fábrica para o 
* componente {@link Session} gerado de forma LAZY ou JIT(Just-in-Time) 
* a partir de uma {@link SessionFactory}, que normalmente se encontra 
* em um ecopo de aplicativo @{@link ApplicationScoped}.
*
* @author Tomaz Lavieri
* @since 1.0
*/
@Component
@RequestScoped
public class JITSessionCreator implements ComponentFactory<Session> {
       
    private static final Method CLOSE = 
            new Mirror().on(Session.class).reflect().method("close").withoutArgs();
    private static final Method FINALIZE = 
            new Mirror().on(Object.class).reflect().method("finalize").withoutArgs();
            
    private final SessionFactory factory;
    /** Guarda a Proxy Session */
    private final Session proxy;
    /** Guarada a Session real. */
    private Session session;
   
    public JITSessionCreator(SessionFactory factory, Proxifier proxifier) {
        this.factory = factory;
        this.proxy = proxify(Session.class, proxifier); // *1*
    }
   
    /**
     * Cria o JIT Session, que repassa a invocação de qualquer método, exceto
     * {@link Object#finalize()} e {@link Session#close()}, para uma session real, 
     * criando uma se necessário.
     */
    private Session proxify(Class<? extends Session> target, Proxifier proxifier) {
        return proxifier.proxify(target, new MethodInvocation<Session>() {
            @Override // *2*
            public Object intercept(Session proxy, Method method, Object[] args, 
                                                            SuperMethod superMethod) {
                if (method.equals(CLOSE) 
                        || (method.equals(FINALIZE) && session == null)) {
                    return null; //skip
                }
                return new Mirror().on(getSession()).invoke().method(method)
                                    .withArgs(args);
            }
        });
    }
   
    public Session getSession() {
        if (session == null) // *3*
                session = factory.openSession();
        return session;
    }
   
    @Override
    public Session getInstance() {
        return proxy; // *4*
    }
   
    @PreDestroy
    public void destroy() { // *5*
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}