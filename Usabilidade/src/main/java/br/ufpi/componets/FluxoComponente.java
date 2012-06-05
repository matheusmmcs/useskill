package br.ufpi.componets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;

/**
 * Classe usada para armazena o fluxo de qual tarefa esta sendo usada e tambem
 * para qual pergunta esta sendo respondida e tambem qual sera a proxima
 * 
 * @author Cleiton
 * 
 */
@Component
@SessionScoped
public class FluxoComponente {
	private List<Long> tarefas;
	private List<Long> perguntasInicio;
	private List<Long> perguntasFim;
	/**
	 * Indica se o usuario esta respondendo as perguntas do inicio
	 * True para perguntas do Inicio
	 */
	private boolean respondendoInicio;

	/**
	 * Criar uma lista com o identificador das tarefas a serem realizadas em um
	 * teste;
	 * 
	 * @param teste
	 */
	public void criarLista(Teste teste) {
		tarefas = new ArrayList<Long>();
		if (teste.getTarefas() != null)
			for (Tarefa tarefa : teste.getTarefas()) {
				this.tarefas.add(tarefa.getId());
			}
		setRespondendoInicio(true);
		instanciarListaPergunta(teste);
		System.out.println("Tarefas" + tarefas);

	}

	/**
	 * Instancia a lista de perguntas
	 * 
	 * @param teste
	 *            a ser usado no Teste de usabilidade
	 */
	private void instanciarListaPergunta(Teste teste) {
		perguntasInicio = new ArrayList<Long>();
		perguntasFim = new ArrayList<Long>();
		Questionario satisfacao = teste.getSatisfacao();
		if ( satisfacao!= null) {
			satisfacao = teste.getSatisfacao();
			for (Pergunta pergunta : satisfacao.getPerguntas()) {
				if (pergunta.isResponderFim()) {
					perguntasFim.add(pergunta.getId());
				} else {
					perguntasInicio.add(pergunta.getId());
				}
			}
			System.out.println("Perguntas inicio " + perguntasInicio);
			System.out.println("Perguntas Fim " + perguntasFim);
		}
	}

	/**
	 * Obtem a proxima tarefa que vai ser obtida
	 * 
	 * @return Identificador da tarefa que vai ser obtida
	 */
	public Long getProximaTarefa() {
		System.out.print("MOMENTO: ");
		System.out.println(tarefas.get(0));
		tarefas.remove(0);
		if (tarefas.size() > 0) {
			System.out.println("RETORNO" + tarefas.get(0));
			return tarefas.get(0);
		} else
			return null;
	}

	@PreDestroy
	public void destroy() {
		this.tarefas = null;
		this.perguntasFim = null;
		this.perguntasInicio = null;
	}

	/**
	 * Retorna a tarefa que esta sendo usada no momento
	 * 
	 * @return
	 */
	public Long getTarefaVez() {

		try {
			return this.getTarefas().get(0);
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * Obtem a pergunta que vai ser respondida no momento
	 * 
	 * @param pergunta
	 *            Lista que possui a seguencia de perguntas
	 * 
	 * @return a pergunta a ser respondida no momento
	 */
	public Long getPerguntaVez(List<Long> pergunta) {
		try {
			return pergunta.get(0);
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * Obtem a proxima pergunta que vai ser obtida
	 * 
	 * @return Identificador da pergunta que vai ser obtida
	 */
	public Long getProximaPergunta(List<Long> pergunta) {
		System.out.print(" Pergunta MOMENTO: ");
		System.out.println(pergunta.get(0));
		pergunta.remove(0);
		if (pergunta.size() > 0) {
			System.out.println("RETORNO" + pergunta.get(0));
			return pergunta.get(0);
		} else
			return null;
	}

	public List<Long> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Long> tarefas) {
		this.tarefas = tarefas;
	}

	public List<Long> getPerguntasInicio() {
		return perguntasInicio;
	}

	public void setPerguntasInicio(List<Long> perguntasInicio) {
		this.perguntasInicio = perguntasInicio;
	}

	public List<Long> getPerguntasFim() {
		return perguntasFim;
	}

	public void setPerguntasFim(List<Long> perguntasFim) {
		this.perguntasFim = perguntasFim;
	}

	public boolean isRespondendoInicio() {
		return respondendoInicio;
	}

	public void setRespondendoInicio(boolean respondendoInicio) {
		this.respondendoInicio = respondendoInicio;
	}

}
