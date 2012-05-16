package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Teste;

@SessionScoped
@Component
public class TesteSession {
private Teste teste;

public final Teste getTeste() {
	return teste;
}

public final void setTeste(Teste teste) {
	this.teste = teste;
}


}
