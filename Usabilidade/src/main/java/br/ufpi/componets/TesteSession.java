package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import javax.annotation.PreDestroy;
/**
 * Usado para armazenar o Teste e a Tarefa que esta sendo gravada para não ficar passando como parametro
 * @author Cleiton
 *
 */
@SessionScoped
@Component
public class TesteSession {

    private Teste teste;
    private Tarefa tarefa;

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public  Teste getTeste() {
        return teste;
    }

    public void setTeste(Teste teste) {
        this.teste = teste;
    }
    @PreDestroy
    public void garbateColection(){
    System.out.println("Terminou minha section");	
    	
    this.setTarefa(null);
    this.setTeste(null);
    }
}
