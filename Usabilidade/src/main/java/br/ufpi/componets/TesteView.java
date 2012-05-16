package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.ufpi.models.Teste;
/**
 * Usado para gerenciar um teste que deve ser alterado em varios locais em uma mesma requisição
 * Não sendo preciso instanciar objeto em todos os metodos
 * @author Cleiton
 *
 */
@RequestScoped
@Component
public class TesteView {
private Teste teste;

public Teste getTeste() {
	return teste;
}

public void setTeste(Teste teste) {
	this.teste = teste;
}

}
