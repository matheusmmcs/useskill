package br.ufpi.analise;

import java.util.HashMap;
import java.util.List;

import br.ufpi.models.FluxoUsuario;
import br.ufpi.models.Tarefa;

public class TarefaAnaliseImpl implements TarefaAnalise {
	private Tarefa tarefa;
	private HashMap<EnumTempo, Double> mapTempo;
	
	public TarefaAnaliseImpl(Tarefa tarefa) {
		super();
		this.tarefa = tarefa;
		mapTempo=new HashMap<EnumTempo, Double>();
	}

	@Override
	public HashMap<EnumTempo, Double> tempos() {
		tempoConclusao();
		return mapTempo;
	}
	
	private void tempoConclusao(){
		double somatorio=0,minimo=0,maximo=0;
		int quantidadeFluxoUsuario=0;
		List<FluxoUsuario> fluxoUsuarios = tarefa.getFluxoUsuario();
		for (FluxoUsuario fluxoUsuario : fluxoUsuarios) {
			quantidadeFluxoUsuario++;
		//somatorio=	fluxoUsuario.getDataFim()-fluxoUsuario.getDataInicio();
		}
	}
	

}
