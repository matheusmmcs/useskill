package br.ufpi.analise;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import br.ufpi.models.Action;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.Usuario;
import br.ufpi.models.vo.ActionVO;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;

public class TestesAlgoritmos {

	
	private static List<ActionVO> getListAcoesObrigatorias(Tarefa tarefa, TarefaRepositoryImpl tarefaRepositoryImpl){
		List<ActionVO> acoesObrigatorias = new ArrayList<ActionVO>();
		List<Fluxo> fluxos = tarefaRepositoryImpl.getFluxos(tarefa.getId(), TipoConvidado.EXPERT);
		for(Fluxo fluxo : fluxos){
			List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
			for(Action acao : acoes){
				ActionVO acaoVO = acao.toVO();
				int contFluxos = 0;
				for(Fluxo fluxo2 : fluxos){
					for(Action acao2 : tarefaRepositoryImpl.getAcoesReais(fluxo2.getId())){
						ActionVO acao2VO = acao2.toVO();
						if(acaoVO.equals(acao2VO)){
							contFluxos++;
							break;
						}
					}
				}
				if(contFluxos == fluxos.size() && !acoesObrigatorias.contains(acaoVO)){
					acoesObrigatorias.add(acaoVO);
				}
			}
			
		}
		return acoesObrigatorias;
	}
	
	private static List<ActionVO> getListAcoesMelhorCaminho(Tarefa tarefa, TarefaRepositoryImpl tarefaRepositoryImpl) {
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
	
	private static HashMap<ActionVO, Integer> getMapAcoesObrigatorias(Tarefa tarefa, TarefaRepositoryImpl tarefaRepositoryImpl){
		HashMap<ActionVO, Integer> acoesObrigatorias = new HashMap<ActionVO, Integer>();
		List<Fluxo> fluxos = tarefaRepositoryImpl.getFluxos(tarefa.getId(), TipoConvidado.EXPERT);
		for(Fluxo fluxo : fluxos){
			List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
			for(Action acao : acoes){
				ActionVO acaoVO = acao.toVO();
				Integer contPrevious = acoesObrigatorias.get(acaoVO);
				if(contPrevious != null){
					acoesObrigatorias.put(acaoVO, contPrevious + 1);
				}else{
					acoesObrigatorias.put(acaoVO, 1);
				}
			}
			
		}
		return acoesObrigatorias;
	}
	
	private static HashMap<ActionVO, BigDecimal> normalizeMapAcoesObrigatorias(HashMap<ActionVO, Integer> mapAcoesObrigatorias){
		int max = 0;
		Set<ActionVO> mapAcoesObrigatoriaskeySet = mapAcoesObrigatorias.keySet();
		for(ActionVO key : mapAcoesObrigatoriaskeySet){
			if(mapAcoesObrigatorias.get(key) > max){
				max = mapAcoesObrigatorias.get(key); 
			}
		}
		HashMap<ActionVO, BigDecimal> newMap = new HashMap<ActionVO, BigDecimal>();
		for(ActionVO key : mapAcoesObrigatoriaskeySet){
			BigDecimal normalized = new BigDecimal(mapAcoesObrigatorias.get(key));
			normalized = normalized.divide(new BigDecimal(max), 2, RoundingMode.HALF_UP);
			newMap.put(key, normalized);
		}
		return newMap;
	}
	
	private static double fuzzyEffectiveness(double time, double action) throws IOException {
        return fuzzyTwoParams("src/main/webapp/files/fuzzy/funceffectiveness.fcl", "time", time, "action", action, "effectiveness", false);
	}
	
	private static double fuzzyPriority(double efficiency, double effectiveness) throws IOException {
        return fuzzyTwoParams("src/main/webapp/files/fuzzy/funcpriority.fcl", "efficiency", efficiency, "effectiveness", effectiveness, "priority", false);
	}
	
	private static double fuzzyTwoParams(String filePath, String sParam1, double param1, String sParam2, double param2, String sResult, boolean debug) throws IOException {
        // Load from 'FCL' file
        FIS fis = FIS.load(filePath,true);

        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" + filePath + "'");
            throw new IOException("Error reading file '" + filePath + "'");
        }

        // Set inputs
        fis.setVariable(sParam1, param1);
        fis.setVariable(sParam2, param2);

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
            System.out.println("Input value: " + param1 + "("+sParam1+"), " + param2 + "("+sParam2+")");
            System.out.println("Output value: " + fis.getVariable(sResult).getValue()); 
            
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
		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = emf.createEntityManager();
		TarefaRepositoryImpl tarefaRepositoryImpl = new TarefaRepositoryImpl(entityManager);
		
		
		long[] ids = {22};//{19, 20}; - 3, 4, 5
		RoundingMode roundingMode = RoundingMode.UP;
		int rounding = 3, roundingPlus = 6;
		
		
		for(long id: ids){
			Tarefa tarefa = tarefaRepositoryImpl.find(id);
			System.out.println("Tarefa: "+tarefa.getId());
			
			
			List<ActionVO> acoesObrigatorias = TestesAlgoritmos.getListAcoesObrigatorias(tarefa, tarefaRepositoryImpl);
			System.out.println("ACOES OBRIGATORIAS: ");
			for(ActionVO a : acoesObrigatorias){
				System.out.println(a);
			}
			System.out.println(acoesObrigatorias.size());
			System.out.println();
			//
			List<ActionVO> acoesMelhorCaminho = TestesAlgoritmos.getListAcoesMelhorCaminho(tarefa, tarefaRepositoryImpl);
			System.out.println("ACOES MELHOR CAMINHO: "+acoesMelhorCaminho.get(0).getUsuario().getNome());
			for(ActionVO a : acoesMelhorCaminho){
				System.out.println(a);
			}
			System.out.println(acoesMelhorCaminho.size());
			System.out.println();
			
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
			double maxTempoAcoes = 0, maxAcoesMelhorCaminho = 0;
			for(Fluxo fluxo : tarefa.getFluxos()){
				List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
				
				BigDecimal qtdAcoes = new BigDecimal(acoes.size());
				
				BigDecimal actionTime = qtdAcoes.divide(new BigDecimal(calculateTimeActions(acoes)), roundingPlus, roundingMode);
				if(maxTempoAcoes < actionTime.doubleValue()){
					maxTempoAcoes = actionTime.doubleValue();
				}

				BigDecimal eficienciaAcoes = new BigDecimal(acoesMelhorCaminho.size()).divide(qtdAcoes, rounding, roundingMode);
				if(maxAcoesMelhorCaminho < eficienciaAcoes.doubleValue()){
					maxAcoesMelhorCaminho = eficienciaAcoes.doubleValue();
				}
			}
			
			System.out.println("maxTempoAcoes: "+ maxTempoAcoes+", maxAcoesMelhorCaminho: "+maxAcoesMelhorCaminho);
			
			for(Fluxo fluxo : tarefa.getFluxos()){
				List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
				System.out.println("Fluxo: "+fluxo.getId());
				
				int contAcao = acoes.size(), contAcaoObrigatoria = countEqualsActions(acoes, acoesObrigatorias), contMelhorCaminho = countEqualsActions(acoes, acoesMelhorCaminho);
				long timeTotal = calculateTimeActions(acoes);
				
				BigDecimal qtdAcoesObrigatorias = new BigDecimal(acoesObrigatorias.size());
				BigDecimal qtdAcoesObrigatoriasFeitas = new BigDecimal(contAcaoObrigatoria);				
				BigDecimal qtdAcoesMelhorCaminho = new BigDecimal(acoesMelhorCaminho.size());//qtd acoes no melhor caminho -> contMelhorCaminho 
				BigDecimal qtdAcoes = new BigDecimal(contAcao);
				
				//eficacia (completude das acoes obrigatorias) -> fazer o que deve ser feito
				BigDecimal eficacia = qtdAcoesObrigatoriasFeitas.divide(qtdAcoesObrigatorias, rounding, roundingMode);
				
				//eficiencia (qtd eventos) -> fazer da melhor forma
				BigDecimal time = new BigDecimal(timeTotal);
				time = time.divide(new BigDecimal(maxTime), rounding, roundingMode);
				BigDecimal action = new BigDecimal(contAcao);
				action = action.divide(new BigDecimal(maxActions), rounding, roundingMode);
				
				BigDecimal eficienciaFuzzy = new BigDecimal(fuzzyEffectiveness(time.doubleValue(), action.doubleValue())).setScale(rounding, roundingMode);
				BigDecimal eficienciaAcoes = qtdAcoesMelhorCaminho.divide(new BigDecimal(contAcao * maxAcoesMelhorCaminho), rounding, roundingMode);
				BigDecimal eficienciaTempo = qtdAcoes.divide(new BigDecimal(timeTotal * maxTempoAcoes), rounding, roundingMode);
				BigDecimal eficiencia = eficienciaTempo;
				
				//prioridade
				BigDecimal prioridadeFuzzy = new BigDecimal(fuzzyPriority(eficiencia.doubleValue(), eficacia.doubleValue())).setScale(rounding, roundingMode);
				
				System.out.println("User["+fluxo.getTipoConvidado()+"]: "+fluxo.getUsuario().getEmail() + " / Tempo: "+ timeTotal + " - Acoes: "+ contAcao + " (" + eficienciaTempo + ") | Eficacia: " + eficacia + " - Eficiencia: " + eficiencia + " | Prioridade: " + prioridadeFuzzy );
				System.out.println("");
			}
			
			
			System.out.println("");
		}
		
		//System.out.println(lcs("Matheus", "Artesateste"));
	}
	
	private static int countEqualsActions(List<Action> acoes, List<ActionVO> acoes2){
		int contAcoes = 0;
		for(Action acao : acoes){			
			ActionVO vo = acao.toVO();
			if(acoes2.contains(vo)){
				contAcoes++;
			}
		}
		return contAcoes;
	}
	
	private static long calculateTimeActions(List<Action> acoes){
		return acoes.get(acoes.size()-1).getsTime() - acoes.get(0).getsTime();
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
	
	private static void generateLog(String name, List<Fluxo> fluxos){
		SimpleDateFormat dateFormatInit = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("hh:mm:ss.SSS");
		String log = getLogHeader(name);
		//TipoConvidado
		for(Fluxo fluxo : fluxos){
			log += "<trace><string key=\"concept:name\" value=\"Case"+fluxo.getId()+".0\"/>";
			for(Action acao : fluxo.getAcoes()){
				String date = dateFormatInit.format(acao.getsTime())+"T"+dateFormatEnd.format(acao.getsTime())+"+03:00";
				String attrs = (acao.getsId() == null || acao.getsId().equals("")) && (acao.getsName() == null || acao.getsName().equals("")) ? acao.getsClass() : acao.getsId() + "-" + acao.getsName();
				String element = acao.getsActionType()+"-"+acao.getsTag()+"-"+acao.getsTagIndex()+"-"+ attrs;
				
				log += "<event>"
							+ "<string key=\"org:resource\" value=\"UNDEFINED\"/>"
							+ "<string key=\"id\" value=\""+acao.getId()+"\"/>"
							+ "<date key=\"time:timestamp\" value=\""+date+"\"/>"
							+ "<string key=\"concept:name\" value=\""+element+"\"/>"
							+ "<string key=\"content\" value=\""+(acao.getsContent().length() < 300 ? acao.getsContent().replaceAll("&lt;", "(").replaceAll("&gt;", ")").replaceAll("&quot;", "").replaceAll("&", "").trim() : "null")+"\"/>"
							+ "<string key=\"location\" value=\""+acao.getsUrl()+"\"/>"
							+ "<string key=\"position\" value=\""+acao.getsPosX()+":"+acao.getsPosY()+"\"/>"
							+ "<string key=\"lifecycle:transition\" value=\""+fluxo.getTipoConvidado()+"\"/>"
						+ "</event>";
			}
			log += "</trace>";
		}
		log += "</log>";
		
		FileWriter arquivo;  
        try {  
            arquivo = new FileWriter(new File("C:/"+name+".xes"));  
            arquivo.write(log);  
            arquivo.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	
	private static String getLogHeader(String name){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<!-- This file has been generated with the OpenXES library. It conforms -->"
				+ "<!-- to the XML serialization of the XES standard for log storage and -->"
				+ "<!-- management. -->"
				+ "<!-- XES standard version: 1.0 -->"
				+ "<!-- OpenXES library version: 1.0RC7 -->"
				+ "<!-- OpenXES is available from http://www.openxes.org/ -->"
				+ "<log xes.version=\"1.0\" xes.features=\"nested-attributes\" openxes.version=\"1.0RC7\" xmlns=\"http://www.xes-standard.org/\">"
				+ "<extension name=\"Lifecycle\" prefix=\"lifecycle\" uri=\"http://www.xes-standard.org/lifecycle.xesext\"/>"
				+ "<extension name=\"Organizational\" prefix=\"org\" uri=\"http://www.xes-standard.org/org.xesext\"/>"
				+ "<extension name=\"Time\" prefix=\"time\" uri=\"http://www.xes-standard.org/time.xesext\"/>"
				+ "<extension name=\"Concept\" prefix=\"concept\" uri=\"http://www.xes-standard.org/concept.xesext\"/>"
				+ "<extension name=\"Semantic\" prefix=\"semantic\" uri=\"http://www.xes-standard.org/semantic.xesext\"/>"
				+ "<global scope=\"trace\">"
				+ "<string key=\"concept:name\" value=\"__INVALID__\"/>"
				+ "<string key=\"concept:value\" value=\"__INVALID__\"/>"
				+ "</global>"
				+ "<global scope=\"event\">"
				+ "<string key=\"concept:name\" value=\"__INVALID__\"/>"
				+ "<string key=\"lifecycle:transition\" value=\"complete\"/>"
				+ "</global>"
				+ "<classifier name=\"MXML Legacy Classifier\" keys=\"concept:name lifecycle:transition\"/>"
				+ "<classifier name=\"Event Name\" keys=\"concept:name\"/>"
				+ "<classifier name=\"Resource\" keys=\"org:resource\"/>"
				+ "<string key=\"source\" value=\"Rapid Synthesizer\"/>"
				+ "<string key=\"concept:name\" value=\""+name+".mxml\"/>"
				+ "<string key=\"lifecycle:model\" value=\"standard\"/>";
	}
	
}
