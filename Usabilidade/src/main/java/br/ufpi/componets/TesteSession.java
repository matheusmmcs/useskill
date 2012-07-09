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
    /**
     * Quando terminar o tempo da sessão do Usuario sera setado os atributos como null
     */
    @PreDestroy
    public void garbateColection(){
    this.setTarefa(null);
    this.setTeste(null);
    }
}
