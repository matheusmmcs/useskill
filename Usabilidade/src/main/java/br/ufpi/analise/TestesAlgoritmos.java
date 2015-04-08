package br.ufpi.analise;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import br.ufpi.analise.enums.TipoAlgoritmoPrioridade;
import br.ufpi.models.Action;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.ActionVO;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;
import br.ufpi.util.ApplicationPath;

public class TestesAlgoritmos {

	public static List<ActionVO> getListAcoesObrigatoriasVO(Tarefa tarefa, TarefaRepository tarefaRepository){
		List<Action> listAcoesObrigatorias = getListAcoesObrigatorias(tarefa, tarefaRepository);
		List<ActionVO> acoesObrigatoriasVO = new ArrayList<ActionVO>();
		for(Action a : listAcoesObrigatorias){
			acoesObrigatoriasVO.add(a.toVO());
		}
		return acoesObrigatoriasVO;
	}
	
	public static HashMap<ActionVO, Integer> getHashMapCountAcoesVO(List<ActionVO> acoes){
		HashMap<ActionVO, Integer> mapCountAcoes = new HashMap<ActionVO, Integer>();
		for(ActionVO a : acoes){
			if(!mapCountAcoes.containsKey(a)){
				mapCountAcoes.put(a, 1);
			}else{
				mapCountAcoes.put(a, mapCountAcoes.get(a) + 1);
			}
		}
		return mapCountAcoes;
	}
	
	/**
	 * Método que retorna todas as ações consideradas obrigatórias, que determinam a eficácia da utilização.
	 * 
	 * @param tarefa
	 * @param tarefaRepository
	 * @return
	 */
	public static LinkedList<Action> getListAcoesObrigatorias(Tarefa tarefa, TarefaRepository tarefaRepository){
		//TODO Corrigir o método, levando em consideração a ordem de cada ação realizada. Isso deve impactar em todas as features de comparação de açoes.
		TarefaRepositoryImpl tarefaRepositoryImpl = ((TarefaRepositoryImpl) tarefaRepository);
		LinkedList<Action> acoesObrigatorias = new LinkedList<Action>();
		List<Fluxo> fluxos = tarefaRepositoryImpl.getFluxos(tarefa.getId(), TipoConvidado.EXPERT);
		for(Fluxo fluxo : fluxos){
			List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
			for(Action acao : acoes){
				ActionVO acaoVO = acao.toVO();
				int contFluxos = 0;
				for(Fluxo fluxo2 : fluxos){
					List<Action> acoesFluxo2 = tarefaRepositoryImpl.getAcoesReais(fluxo2.getId());
					for(Action acao2 : acoesFluxo2){
						ActionVO acao2VO = acao2.toVO();
						if(acaoVO.equals(acao2VO)){
							contFluxos++;
							break;
						}
					}
				}
				if(contFluxos == fluxos.size()){
					boolean contains = false;
					for(Action a : acoesObrigatorias){
						if(acaoVO.equals(a.toVO())){
							contains = true;
							break;
						}
					}
					if(!contains){
						acoesObrigatorias.add(acao);
					}
				}
			}
			
		}
		return acoesObrigatorias;
	}
	
	/**
	 * Todas as ações realizadas por especialistas
	 * 
	 * @param tarefa
	 * @param tarefaRepository
	 * @return
	 */
	public static Set<ActionVO> getSetAcoesEspecialistas(Tarefa tarefa, TarefaRepository tarefaRepository) {
		TarefaRepositoryImpl tarefaRepositoryImpl = ((TarefaRepositoryImpl) tarefaRepository);
		List<Fluxo> fluxos = tarefaRepositoryImpl.getFluxos(tarefa.getId(), TipoConvidado.EXPERT);
		Set<ActionVO> acoesEspecialistas = new LinkedHashSet<ActionVO>();
		for(Fluxo fluxo : fluxos){
			List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
			for(Action acao : acoes){
				acoesEspecialistas.add(acao.toVO());
			}
		}
		return acoesEspecialistas;
	}
	
	/**
	 * Retorna uma lista contendo as açoes que fazem parte do melhor caminho percorrido
	 * 
	 * @param tarefa
	 * @param tarefaRepository
	 * @return
	 */
	public static List<ActionVO> getListAcoesMelhorCaminho(Tarefa tarefa, TarefaRepository tarefaRepository) {
		TarefaRepositoryImpl tarefaRepositoryImpl = ((TarefaRepositoryImpl) tarefaRepository);
		List<Fluxo> fluxos = tarefaRepositoryImpl.getFluxos(tarefa.getId(), TipoConvidado.EXPERT);
		List<Action> melhorCaminho = null;
		List<ActionVO> melhorCaminhoVO = new ArrayList<ActionVO>();
		int contMinFluxo = 0;
		Fluxo melhorFluxo = null;
		for(Fluxo fluxo : fluxos){
			List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
			if(contMinFluxo == 0 || contMinFluxo > acoes.size()){
				contMinFluxo = acoes.size();
				melhorCaminho = acoes;
				melhorFluxo = fluxo;
			}
		}
		if(melhorCaminho != null){
			for(Action a : melhorCaminho){
				ActionVO vo = a.toVO();
				vo.setUsuario(melhorFluxo.getUsuario());
				melhorCaminhoVO.add(vo);
			}
		}
		return melhorCaminhoVO;
	}
	
	private static double fuzzyEfficiency(double time, double action) throws IOException {
		HashMap<String, Double> params = new HashMap<String, Double>();
		params.put("time", time);
		params.put("action", action);
        return fuzzyParams(ApplicationPath.getFilePath("files/fuzzy/funcefficiency.fcl"), params, "efficiency", false);
	}
	
	private static double fuzzyPriority(double efficiency, double effectiveness) throws IOException {
		HashMap<String, Double> params = new HashMap<String, Double>();
		params.put("efficiency", efficiency);
		params.put("effectiveness", effectiveness);
        return fuzzyParams(ApplicationPath.getFilePath("files/fuzzy/funcpriority.fcl"), params, "priority", false);
	}
	
	private static double fuzzyPriorityThreeParams(double effectiveness, double time, double action) throws IOException {
		HashMap<String, Double> params = new HashMap<String, Double>();
		params.put("time", time);
		params.put("action", action);
		params.put("effectiveness", effectiveness);
        return fuzzyParams(ApplicationPath.getFilePath("files/fuzzy/funcpriority-time-actions-effectiveness.fcl"), params, "priority", false);
	}	
	
	private static double fuzzyParams(String filePath, HashMap<String, Double> params, String sResult, boolean debug) throws IOException {
        FIS fis = FIS.load(filePath,true);

        if( fis == null ) { 
            System.err.println("Can't load file: '" + filePath + "'");
            throw new IOException("Error reading file '" + filePath + "'");
        }
        
        Set<String> keySet = params.keySet();

        // Set inputs
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
        	String key = it.next();
            fis.setVariable(key, params.get(key));
        }

        // Evaluate
        fis.evaluate();
        double value = fis.getVariable(sResult).getValue();
        
        if(debug){
        	// Show 
            JFuzzyChart.get().chart(fis);
            // Show output variable's chart
            Variable tip = fis.getVariable(sResult);
            JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

            // Print ruleSet
            System.out.println(fis);
            System.out.print("Input value: ");
            it = keySet.iterator();
            while (it.hasNext()) {
            	String key = it.next();
                System.out.print(params.get(key) + "(" + key + "), ");
            }
            System.out.println("\nOutput value: " + fis.getVariable(sResult).getValue()); 
            
            // Show each rule (and degree of support)
            Set<Entry<String, RuleBlock>> ruleBlocksSet = fis.getFunctionBlock("fuzzy").getRuleBlocks().entrySet();
    		for(Entry<String, RuleBlock> entry : ruleBlocksSet){
            	System.out.println("RuleBlock: "+entry.getKey());
            	for(Rule rule : entry.getValue()){
            		System.out.println(rule);
            	}
            }
        }
        
        return value;
	}
	

	public static void main(String[] args) throws IOException {
		long id = 24;//{22}; 24,25,26
		HashMap<String, List<ResultadoPrioridade>> generatedPriority = generatePriority(id, false); //{19, 20}; - 3, 4, 5
		
		System.out.println("\n\n######### ORDENADOS #########");
		
		Set<String> keySet = generatedPriority.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			ArrayList<ResultadoPrioridade> list = (ArrayList<ResultadoPrioridade>) generatedPriority.get(key);
			Collections.sort(list, new Comparator<ResultadoPrioridade>() {
		        @Override
		        public int compare(ResultadoPrioridade o1, ResultadoPrioridade o2) {
		        	if(o1.getPrioridade() < o2.getPrioridade()){
		        		return 1;
		        	}else if(o1.getPrioridade() > o2.getPrioridade()){
		        		return -1;
		        	}else{
		        		return 0;
		        	}
		        }
		    });
			
			System.out.println(key+" Ordenada: ");
			for(ResultadoPrioridade r : list){
				System.out.println(r.toPrintString());
			}
			System.out.println("");
		}
	}
	
	public static HashMap<String, List<ResultadoPrioridade>> generatePriority(long id, boolean debug) throws IOException {
		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = emf.createEntityManager();
		TarefaRepositoryImpl tarefaRepositoryImpl = new TarefaRepositoryImpl(entityManager);
		
		HashMap<String, List<ResultadoPrioridade>> resultado  = new HashMap<String, List<ResultadoPrioridade>>();
		
		RoundingMode roundingMode = RoundingMode.UP;
		int rounding = 3, roundingPlus = 6;
		
		
		Tarefa tarefa = tarefaRepositoryImpl.find(id);
		if(debug){
			System.out.println("Tarefa: "+tarefa.getId());
		}
		
		
		List<ActionVO> acoesObrigatorias = TestesAlgoritmos.getListAcoesObrigatoriasVO(tarefa, tarefaRepositoryImpl);
		//if(debug){
			System.out.println("ACOES OBRIGATORIAS: ");
			for(ActionVO a : acoesObrigatorias){
				System.out.println(a);
			}
			System.out.println(acoesObrigatorias.size());
			System.out.println();
		//}

		List<ActionVO> acoesMelhorCaminho = TestesAlgoritmos.getListAcoesMelhorCaminho(tarefa, tarefaRepositoryImpl);
		//if(debug){
			System.out.println("ACOES MELHOR CAMINHO: "+acoesMelhorCaminho.get(0).getUsuario().getNome());
			for(ActionVO a : acoesMelhorCaminho){
				System.out.println(a);
			}
			System.out.println(acoesMelhorCaminho.size());
			System.out.println();
		//}
		
		long maxTime = 0, maxActions = 0;
		for(Fluxo fluxo : tarefa.getFluxos()){
			if(maxTime < fluxo.getTempoRealizacao()){
				maxTime = fluxo.getTempoRealizacao();
			}
			if(maxActions < fluxo.getAcoes().size()){
				maxActions = fluxo.getAcoes().size();
			}
		}
		
		//valores para normalização
		double minTempoPorAcao = Integer.MAX_VALUE, maxAcaoPorTempo = 0, maxAcoesMelhorCaminho = 0;
		
		BigDecimal qtdAcoesMelhorCaminho = new BigDecimal(acoesMelhorCaminho.size());
		BigDecimal qtdAcoesObrigatorias = new BigDecimal(acoesObrigatorias.size());
		BigDecimal qtdMaxActions = new BigDecimal(maxActions);
		
		for(Fluxo fluxo : tarefa.getFluxos()){
			List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
			
			BigDecimal qtdAcoes = new BigDecimal(acoes.size());
			BigDecimal qtdTempo = new BigDecimal(calculateTimeActions(acoes));
			
			BigDecimal timeAction = qtdAcoes.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdTempo.divide(qtdAcoes, roundingPlus, roundingMode);
			if(minTempoPorAcao > timeAction.doubleValue()){
				minTempoPorAcao = timeAction.doubleValue();
			}
			
			BigDecimal actionTime = qtdTempo.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdAcoes.divide(qtdTempo, roundingPlus, roundingMode);
			if(maxAcaoPorTempo < actionTime.doubleValue()){
				maxAcaoPorTempo = actionTime.doubleValue();
			}

			BigDecimal eficienciaAcoes = qtdAcoes.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdAcoesMelhorCaminho.divide(qtdAcoes, rounding, roundingMode);
			if(maxAcoesMelhorCaminho < eficienciaAcoes.doubleValue()){
				maxAcoesMelhorCaminho = eficienciaAcoes.doubleValue();
			}
		}
		
		if(debug){
			System.out.println("minTempoParaCadaAcao: "+ minTempoPorAcao+", maxAcaoPorTempo: "+maxAcaoPorTempo+", maxAcoesMelhorCaminho: "+maxAcoesMelhorCaminho+", melhorCaminho: "+qtdAcoesMelhorCaminho);
		}
		
		for (TipoAlgoritmoPrioridade type : TipoAlgoritmoPrioridade.values()) {
			if(debug){
				System.out.println("\n\n##### "+type+" #####");
			}
			
			List<ResultadoPrioridade> list = new ArrayList<ResultadoPrioridade>();
			
			for(Fluxo fluxo : tarefa.getFluxos()){
				List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
				
				System.out.println("\n Usuario: "+fluxo.getUsuario().getNome());
				for(Action a : acoes){
					System.out.println(a.toVO());
				}
				
				HashMap<ActionVO,Integer> mapCountAcoesObrigatoriasVO = getHashMapCountAcoesVO(acoesObrigatorias);
				HashMap<ActionVO,Integer> mapCountAcoesMelhorCaminhoVO = getHashMapCountAcoesVO(acoesMelhorCaminho);
				int contAcao = acoes.size(), contAcoesObrigatorias = countEqualsActions(acoes, acoesObrigatorias, mapCountAcoesObrigatoriasVO), contAcoesNoMelhorCaminho = countEqualsActions(acoes, acoesMelhorCaminho, mapCountAcoesMelhorCaminhoVO);
				long timeTotal = calculateTimeActions(acoes);
				
				BigDecimal qtdAcoesObrigatoriasFeitas = new BigDecimal(contAcoesObrigatorias);				
				BigDecimal qtdAcoes = new BigDecimal(contAcao);
				BigDecimal tempoNormalizado = maxTime == 0 ? BigDecimal.ZERO : new BigDecimal(timeTotal).divide(new BigDecimal(maxTime), rounding, roundingMode);
				BigDecimal acoesNormalizadas = qtdMaxActions.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdAcoes.divide(qtdMaxActions, rounding, roundingMode);
				
				//eficacia (completude das acoes obrigatorias) -> fazer o que deve ser feito
				BigDecimal eficacia = qtdAcoesObrigatorias.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdAcoesObrigatoriasFeitas.divide(qtdAcoesObrigatorias, rounding, roundingMode);
				
				
				//eficiencia (qtd eventos) -> fazer da melhor forma					
				if(type.equals(TipoAlgoritmoPrioridade.AcoesPorTempo)){
					BigDecimal time = new BigDecimal(timeTotal * maxAcaoPorTempo);
					BigDecimal eficienciaTempo = time.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdAcoes.divide(time, rounding, roundingMode);
					BigDecimal prioridadeFuzzy = new BigDecimal(fuzzyPriority(eficienciaTempo.doubleValue(), eficacia.doubleValue())).setScale(rounding, roundingMode);

					ResultadoPrioridade r = new ResultadoPrioridade(TipoAlgoritmoPrioridade.AcoesPorTempo, fluxo, prioridadeFuzzy.doubleValue());
					r.addParametro("acoes", qtdAcoes.doubleValue());
					r.addParametro("tempo", time.doubleValue());
					r.addParametro("eficiencia", eficienciaTempo.doubleValue());
					r.addParametro("eficacia", eficacia.doubleValue());
					list.add(r);
					if(debug){
						System.out.println(r.toPrintString());
					}
					
				}else if(type.equals(TipoAlgoritmoPrioridade.AcoesMelhorCaminhoPorAcoes)){
					BigDecimal contAcoes = new BigDecimal(contAcao * maxAcoesMelhorCaminho);
					BigDecimal eficienciaAcoesNormalizadas = contAcoes.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : qtdAcoesMelhorCaminho.divide(contAcoes, rounding, roundingMode);
					BigDecimal prioridadeFuzzy = new BigDecimal(fuzzyPriority(eficienciaAcoesNormalizadas.doubleValue(), eficacia.doubleValue())).setScale(rounding, roundingMode);
					
					ResultadoPrioridade r = new ResultadoPrioridade(TipoAlgoritmoPrioridade.AcoesMelhorCaminhoPorAcoes, fluxo, prioridadeFuzzy.doubleValue());
					r.addParametro("acoesMelhorCaminho", qtdAcoesMelhorCaminho.doubleValue());
					r.addParametro("acoes", contAcoes.doubleValue());
					r.addParametro("eficiencia", eficienciaAcoesNormalizadas.doubleValue());
					r.addParametro("eficacia", eficacia.doubleValue());
					list.add(r);
					if(debug){
						System.out.println(r.toPrintString());
					}
					
				}else if(type.equals(TipoAlgoritmoPrioridade.DoisFuzzy)){
					BigDecimal eficienciaFuzzy = new BigDecimal(fuzzyEfficiency(tempoNormalizado.doubleValue(), acoesNormalizadas.doubleValue())).setScale(rounding, roundingMode);
					BigDecimal prioridadeFuzzy = new BigDecimal(fuzzyPriority(eficienciaFuzzy.doubleValue(), eficacia.doubleValue())).setScale(rounding, roundingMode);
					
					ResultadoPrioridade r = new ResultadoPrioridade(TipoAlgoritmoPrioridade.DoisFuzzy, fluxo, prioridadeFuzzy.doubleValue());
					r.addParametro("acoes", acoesNormalizadas.doubleValue());
					r.addParametro("tempo", tempoNormalizado.doubleValue());
					r.addParametro("eficiencia", eficienciaFuzzy.doubleValue());
					r.addParametro("eficacia", eficacia.doubleValue());
					list.add(r);
					if(debug){
						System.out.println(r.toPrintString());
					}
					
				}else if(type.equals(TipoAlgoritmoPrioridade.FuzzyTresParams)){
					BigDecimal prioridadeFuzzy = new BigDecimal(fuzzyPriorityThreeParams(eficacia.doubleValue(), tempoNormalizado.doubleValue(), acoesNormalizadas.doubleValue())).setScale(rounding, roundingMode);
					
					ResultadoPrioridade r = new ResultadoPrioridade(TipoAlgoritmoPrioridade.FuzzyTresParams, fluxo, prioridadeFuzzy.doubleValue());
					r.addParametro("acoes", acoesNormalizadas.doubleValue());
					r.addParametro("tempo", tempoNormalizado.doubleValue());
					r.addParametro("eficacia", eficacia.doubleValue());
					list.add(r);
					if(debug){
						System.out.println(r.toPrintString());
					}
					
				}
			}
			
			resultado.put(String.valueOf(id)+"-"+type, list);
		}
		
		return resultado;
	}
	
	/**
	 * Método que busca a semelhança entre duas listas de acoes. Se for passado um map como parâmetro, será contado apenas os que estiverem no map.
	 * 
	 * @param acoes = acoes originais, que serão comparadas
	 * @param acoes2 = lista de ações desejadas, ou seja, que servirá de base para comparação
	 * @param mapAcoes = map que contem a quantidade de cada ação na lista de acoes obrigatorias
	 * 
	 * @return
	 */
	private static int countEqualsActions(List<Action> acoes, List<ActionVO> acoes2, HashMap<ActionVO, Integer> mapAcoes){
		int contAcoes = 0;
		for(Action acao : acoes){
			ActionVO vo = acao.toVO();
			
			for(ActionVO acao2 : acoes2){				
				if(vo.equals(acao2)){
					if(mapAcoes != null){
						if(mapAcoes.get(vo) > 0){
							mapAcoes.put(vo, mapAcoes.get(vo) - 1);
						}else{
							break;
						}
					}
					contAcoes++;
					break;
				}
			}
			
		}
		return contAcoes;
	}
	
	private static long calculateTimeActions(List<Action> acoes){
		return acoes.size() > 0 ? acoes.get(acoes.size()-1).getsTime() - acoes.get(0).getsTime() : 0l;
	}
		

	public static String lcs(String a, String b) {
	    int[][] lengths = new int[a.length()+1][b.length()+1];
	 
	    // row 0 and column 0 are initialized to 0 already
	 
	    for (int i = 0; i < a.length(); i++)
	        for (int j = 0; j < b.length(); j++)
	            if (a.charAt(i) == b.charAt(j))
	                lengths[i+1][j+1] = lengths[i][j] + 1;
	            else
	                lengths[i+1][j+1] =
	                    Math.max(lengths[i+1][j], lengths[i][j+1]);
	 
	    // read the substring out from the matrix
	    StringBuffer sb = new StringBuffer();
	    for (int x = a.length(), y = b.length();
	         x != 0 && y != 0; ) {
	        if (lengths[x][y] == lengths[x-1][y])
	            x--;
	        else if (lengths[x][y] == lengths[x][y-1])
	            y--;
	        else {
	            assert a.charAt(x-1) == b.charAt(y-1);
	            System.out.println("X:"+(x-1)+", Y:"+(y-1)+", pos.:"+a.charAt(x-1));
	            sb.append(a.charAt(x-1));
	            x--;
	            y--;
	        }
	    }
	 
	    return sb.reverse().toString();
	}
	
}
