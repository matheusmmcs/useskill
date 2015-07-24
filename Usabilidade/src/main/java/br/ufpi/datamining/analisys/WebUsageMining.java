package br.ufpi.datamining.analisys;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionPieceWiseLinear;
import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.datamining.models.PageViewActionDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.aux.FieldSearch;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.aux.SessionResultDataMining;
import br.ufpi.datamining.models.aux.UserResultDataMining;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningEnum;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.utils.EntityManagerUtil;
import br.ufpi.datamining.utils.StatisticsUtils;
import br.ufpi.util.ApplicationPath;


/*
 * TODO:
 * 1 - retirar todos os \ dos xpath
 * 2 - PageViewActionDataMining amarrado com jhms, xpath, etc.
 * 3 - PageViewDataMining retirar o replace da url
 */
public class WebUsageMining {
	
	/*
	############
	
	1 - Coleta dos dados:
	2 - Pré-processamento:
	 - filtrar por dados úteis (quais ações serão analisadas?):
	 - identificar usuário (login);
	 - identificar sessão: ações iniciais / ações iniciais + threshold de X min;
	3 - Descoberta de padrões:
	 - classificar a utilização (boas / ruins):
	 		* pontos de obrigatórios (plugin que captura ações específicas);
	 			> todas ou porcentagem?
	 - classificar o usuário (experiente / novato):
	 		* qtd de fluxos;
	 		* especialista define grau do usuário;
	 - Sequential Pattern;
	 - Clusterization;
	4 - Análise dos dados:
 	 - Fuzzy;
 	 - Graphs;
	 
	############
	 
	- Selecao dos dados que fazem parte do que sera analisado;
	- Cleaning: remover dados inuteis;
	OK - Pageview Identification: Dados referentes ao JHM/URL;
	OK - PageViewAction - identificar cada acao realizada;
	OK - User Identification: Sessao do usuario logado;
	- Sessionization: tempo de sessao / tempo por pagina / referencia de onde o usuario veio (nesse caso, qual o jhm q o usuario está);
		- Neste caso seria analise de ponto inicial e ponto final (threshold de tempo maximo entre acoes e analisar se realizou outra acao);
	- Path Completition: Como saber se houve falha na captura dos dados?;
	- Data Integration: Receber mais informações para integrar aos dados capturados;
	
	** Dados importantes:
	* Diferentemente de capturas de logs no servidor, a identificacao das pageviews, do usuario e das sessoes sao feitas antes no cleaning;
	* Identificar pageView -> padronizo as url e crio o um set;
	* Identificar pageAction -> XPath;
	*/
	
	//----- NOVA ABORDAGEM -----
	//-----
	//ok- usuário: 	conjunto de sessões;
	//ok- sessão: 	classificação, quantidade de ações, tempo e todas as ações que foram realizadas;
	//-----
	//ok- média de ações por usuário;
	//ok- média de tempo por usuário;
	//ok- taxa de ok, repet e erro por usuário;
	//- evolução do usuário;
	//- média de ações por classificação;
	//- média de tempo por classificação;
	//- ações mais realizadas por classificação;
	//-----
	//- classificar o usuário como experiente/novato; (especialista ou (med.acoes, med.tempo, taxa de ok))
	//-----
	//- grafo contendo as sessões corretas (serve para validar as ações iniciais/finais e formar o modelo de interação correto);
	//- permitir sobrepor o "grafo correto" com qualquer sessão (priorizar as com mais tempo, ação e piores usuários (fuzzy));
	//- clusterizar os usuários de acordo com tempo e ação;
	//-----
	
	//----- TODO -----
	//-----
	//- criar listagem dos usuários (com seus índices), grafo com as ações de quem realizou corretamente;
	//- criar plugin para facilitar na identificação do XPath, Jhm, Step e Url (depois tornar dinâmico);
	//- criar gráficos de pizza, apresentando os resultados dos usuários e das sessões.
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, IOException {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		TaskDataMiningRepository taskDataMiningRepository = new TaskDataMiningRepository(entityManager);
		ActionDataMiningRepository actionDataMiningRepository = new ActionDataMiningRepository(entityManager);
		ResultDataMining resultDataMining = analyze(6l, taskDataMiningRepository, actionDataMiningRepository);
		System.out.println(resultDataMining.getSessions().size());
	}
	
	
	public static ResultDataMining analyze(Long taskId, TaskDataMiningRepository taskDataMiningRepository, ActionDataMiningRepository actionDataMiningRepository) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		
		boolean DEBUG = false;
		TaskDataMining taskDataMining = taskDataMiningRepository.find(taskId);
		
		//#0 - pegar as configurações da tarefa
		BigDecimal threshold = new BigDecimal(taskDataMining.getThreshold() * 60000); //minutos para milesegundos
		List<ActionDataMining> actionsEnd = new ArrayList<ActionDataMining>();
		List<ActionDataMining> actionsInitial = new ArrayList<ActionDataMining>();
		for(ActionSingleDataMining actionSingle : taskDataMining.getActionsEnd()){
			actionsEnd.add(actionSingle.toActionDataMining());
		}
		for(ActionSingleDataMining actionSingle : taskDataMining.getActionsInitial()){
			actionsInitial.add(actionSingle.toActionDataMining());
		}
		
		
		//#1 - pegar todas as interações que possuem esses pontos de incio e agrupar por usuarios todas as interações;
		HashMap<String, List<ActionDataMining>> initialActionOfsectionsFromUser = new HashMap<String, List<ActionDataMining>>();
		for(ActionSingleDataMining a : taskDataMining.getActionsInitial()){
			List<FieldSearch> fieldsSearch = new ArrayList<FieldSearch>();
			fieldsSearch.add(new FieldSearch("sActionType", a.getActionType().getAction()));
			for(FieldSearchTupleDataMining f : a.getElementFiedlSearch()){
				fieldsSearch.add(new FieldSearch(f.getField(), f.valueToObject()));
			}
			for(FieldSearchTupleDataMining f : a.getUrlFieldSearch()){
				if(!f.getField().equals("sUrl")){
					fieldsSearch.add(new FieldSearch(f.getField(), f.valueToObject()));
				}
			}
			
			List<ActionDataMining> actions = actionDataMiningRepository.getActions(fieldsSearch, taskDataMining.getDisregardActions());
			for(ActionDataMining ai : actions){
				if(initialActionOfsectionsFromUser.get(ai.getsUsername()) == null){
					initialActionOfsectionsFromUser.put(ai.getsUsername(), new ArrayList<ActionDataMining>());
				}
				initialActionOfsectionsFromUser.get(ai.getsUsername()).add(ai);
			}
		}

		if(DEBUG){
			System.out.println(initialActionOfsectionsFromUser.size());
			for(String u : initialActionOfsectionsFromUser.keySet()){
				System.out.println(u + " -> " + initialActionOfsectionsFromUser.get(u).size());
			}
		}
		
		
		//#2 - para cada sessão dos usuarios, buscar as acoes (filtradas, se necessário) realizadas
		//#3 - montar o resultdatamining
		List<UserResultDataMining> usersResult = new ArrayList<UserResultDataMining>();
		List<SessionResultDataMining> sessionsResult = new ArrayList<SessionResultDataMining>();
		int countoktotal = 0, counterrototal = 0, countinittotal = 0, countthresholdtotal = 0, countTaskSessions = 0;
		double countTaskActionsSessionsOk = 0, countTaskTimesSessionsOk = 0,
				maxTimeAverageOk = 0, maxActionsAverageOk = 0,
				maxActionOk = 0, maxTimeOk = 0;
		
		//Statistics Analysis
		List<Double> actionsSize = new ArrayList<Double>(),
				actionsOkSize = new ArrayList<Double>(),
				timesSize = new ArrayList<Double>(),
				timesOkSize = new ArrayList<Double>();
		
		//Count Actions
		int countIdPageViewAction = 1;
		HashMap<String, String> pageViewActionIds = new HashMap<String, String>();
		HashMap<String, Integer> pageViewActionCount = new HashMap<String, Integer>();
		
		//Actions required
		List<String> actionsRequired = new ArrayList<String>();
		for(ActionSingleDataMining a : taskDataMining.getActionsRequired()){
			PageViewActionDataMining pageViewActionDataMining = new PageViewActionDataMining(a.toActionDataMining());
			actionsRequired.add(pageViewActionDataMining.getPageViewActionUnique());
		}
		HashMap<String, Integer> actionsRequiredTask = getActionsRequired(actionsRequired);
		Integer countActionsRequired = actionsRequired.size();
		Double countActionsRequiredTask = 0d;
		
		
		Set<String> usersWithInitialActions = initialActionOfsectionsFromUser.keySet();
		for(String username : usersWithInitialActions){
			List<ActionDataMining> userInitialActions = initialActionOfsectionsFromUser.get(username);
			
			//variaveis para contabilizar resultado dos usuarios
			int countok = 0, counterro = 0, countinit = 0, countthreshold = 0, countUserSessions = userInitialActions.size();
			double countUserActionsSessionsOk = 0, countUserTimesSessionsOk = 0;
			List<String> sessionsResultIds = new ArrayList<String>();
			
			Double countActionsRequiredUser = 0d;
			
			//verificar cada sessao
			for(int i = 0; i < countUserSessions; i++){
				ActionDataMining initialAction = userInitialActions.get(i);
				List<ActionDataMining> userSectionActions;
				
				//se for a ultima sessão do usuário
				if(i == countUserSessions-1){
					userSectionActions = actionDataMiningRepository.getUserActions(username, initialAction.getsTime(), taskDataMining.getDisregardActions());
				}else{
					ActionDataMining nextInitialAction = userInitialActions.get(i+1);
					userSectionActions = actionDataMiningRepository.getUserActions(username, initialAction.getsTime(), nextInitialAction.getsTime(), taskDataMining.getDisregardActions());
				}
				
				//analise das acoes
				SessionClassificationDataMiningEnum classification = SessionClassificationDataMiningEnum.ERROR;
				List<PageViewActionDataMining> userSectionPageViewActions = new ArrayList<PageViewActionDataMining>();
				int countActions = 0, sectionSize = userSectionActions.size();
				long initialTime = 0l, endTime = 0l;
				boolean okThreshold = true;
				
				HashMap<String, Integer> actionsRequiredSession = getActionsRequired(actionsRequired);
				
				//iterar acoes do usuario na sessao
				ActionDataMining action = null;
				for(int j = 0; j < sectionSize; j++){
					
					if(okThreshold){
						action = userSectionActions.get(j);
						
						//gerar uma descrição menor para identificar a ação
						PageViewActionDataMining pageViewActionDataMining = new PageViewActionDataMining(action);
						String pvaKey, pvaUnique = pageViewActionDataMining.getPageViewActionUnique();
						if(pageViewActionIds.get(pvaUnique) == null){
							pvaKey = "A"+countIdPageViewAction;
							pageViewActionIds.put(pvaUnique, pvaKey);
							countIdPageViewAction++;
						}else{
							pvaKey = pageViewActionIds.get(pvaUnique);
						}
						pageViewActionDataMining.setIdentifier(pvaKey);
						userSectionPageViewActions.add(pageViewActionDataMining);
						countActions++;
						
						if(DEBUG){
							//System.out.println(pvaUnique);
						}
						
						if(actionsRequiredTask.get(pvaUnique) != null){
							actionsRequiredTask.put(pvaUnique, (actionsRequiredTask.get(pvaUnique) + 1));
							actionsRequiredSession.put(pvaUnique, (actionsRequiredSession.get(pvaUnique) + 1));
						}
						
						if(j == 0){
							initialTime = action.getsTime();
							//contabiliza a ação inicial
							if(pageViewActionCount.get(pvaKey) == null){
								pageViewActionCount.put(pvaKey, 1);
							}else{
								pageViewActionCount.put(pvaKey, (pageViewActionCount.get(pvaKey) + 1));
							}
						}else{
							//vejo limiar com o anterior
							ActionDataMining previousAction = userSectionActions.get(j-1);
							BigDecimal diff = new BigDecimal(action.getsTime() - previousAction.getsTime());
							if(diff.compareTo(threshold) == 1){
								okThreshold = false;
								classification = SessionClassificationDataMiningEnum.THRESHOLD;
								break;
							}
							
							//não ultrapassou limiar, então contabiliza a ação
							if(pageViewActionCount.get(pvaKey) == null){
								pageViewActionCount.put(pvaKey, 1);
							}else{
								pageViewActionCount.put(pvaKey, (pageViewActionCount.get(pvaKey) + 1));
							}
							
							//verifica se é ação inicial/final
							if(listContainsAction(actionsEnd, action)){
								classification = SessionClassificationDataMiningEnum.SUCCESS;
								break;
							}else if(listContainsAction(actionsInitial, action)){
								classification = SessionClassificationDataMiningEnum.REPEAT;
								break;
							}
						}
					}
				}
				
				if(action == null){
					if(DEBUG){
						System.out.println("action null: "+ username + "-" + i);
					}
					endTime = 0;
				}else{
					endTime = action.getsTime();
				}
				
				if(DEBUG){
					//System.out.println("-"+classification);
				}
				
				Set<String> keySetActionsRequiredSession = actionsRequiredSession.keySet();
				Double countActionsRequiredSession = 0d;
				for(String k : keySetActionsRequiredSession){
					if(actionsRequiredSession.get(k) > 0){
						countActionsRequiredSession++;
						countActionsRequiredUser++;
						countActionsRequiredTask++;
					}
				}
				
				//resultados da sessao
				String sessionKey = username+"-"+i;
				Long sessionTime = endTime - initialTime;
				
				//required
				Double required = countActionsRequiredSession > 0 ? (countActionsRequiredSession/countActionsRequired) : 0;
				
				sessionsResult.add(new SessionResultDataMining(sessionKey, username, classification, sessionTime, userSectionPageViewActions, actionsRequiredSession, required, !okThreshold));
				
				
				//resultados do usuario por sessao
				sessionsResultIds.add(sessionKey);
				if(okThreshold){
					actionsSize.add((double) countActions);
					timesSize.add((double) sessionTime);
				}
				
				//statistics
				if(classification.equals(SessionClassificationDataMiningEnum.ERROR)){
					counterro++;
				}else if(classification.equals(SessionClassificationDataMiningEnum.SUCCESS)){
					countok++;
					countUserActionsSessionsOk += countActions;
					countUserTimesSessionsOk += sessionTime;
					
					maxTimeOk = maxTimeOk < sessionTime ? sessionTime : maxTimeOk;
					maxActionOk = maxActionOk < countActions ? countActions : maxActionOk;
					
					//statistics
					actionsOkSize.add((double) countActions);
					timesOkSize.add((double) sessionTime);
				}else if(classification.equals(SessionClassificationDataMiningEnum.REPEAT)){
					countinit++;
				}else if(classification.equals(SessionClassificationDataMiningEnum.THRESHOLD)){
					countthreshold++;
				}
				
			}
			
			//resultados somado para o total
			countoktotal += countok;
			counterrototal += counterro;
			countinittotal += countinit;
			countthresholdtotal += countthreshold;
			countTaskSessions += countUserSessions;
			
			countTaskTimesSessionsOk += countUserTimesSessionsOk;
			countTaskActionsSessionsOk += countUserActionsSessionsOk;
			
			//resultados por usuario
			Double actionsAverageOk = countok > 0 ? (countUserActionsSessionsOk / countok) : 0;
			Double timesAverageOk = countok > 0 ? (countUserTimesSessionsOk / countok) : 0;
			
			//dados para normalizar o fuzzy
			maxTimeAverageOk = maxTimeAverageOk < timesAverageOk ? timesAverageOk : maxTimeAverageOk;
			maxActionsAverageOk = maxActionsAverageOk < actionsAverageOk ? actionsAverageOk : maxActionsAverageOk;
			
			//required
			Double required = countActionsRequiredUser > 0 ? (countActionsRequiredUser/(countUserSessions * countActionsRequired)) : 0;
			
			usersResult.add(new UserResultDataMining(username, actionsAverageOk, timesAverageOk, countok, counterro, countinit, countthreshold, 0d, required, sessionsResultIds));
			System.out.println(username + " [ok=" + countok + ", init=" + countinit + ", erro=" + counterro + "]");
		}
		
		Double actionsTaskAverageOk = (countTaskActionsSessionsOk / countoktotal);
		Double timesTaskAverageOk = (countTaskTimesSessionsOk / countoktotal);
		
		//permitir que seja possível normalizar os dados das sessões
		HashMap<String, Double> userRateSuccess = new HashMap<String, Double>();
		for(UserResultDataMining u : usersResult){
			u.setMaxActionsAverageOk(maxActionsAverageOk);
			u.setMaxTimeAverageOk(maxTimeAverageOk);
			userRateSuccess.put(u.getUsername(), u.getUncompleteNormalized());
		}
		
		//#4 - Fuzzy tempo e ações nas -> 
		boolean IGNORE_ZERO = true;
		
		//Fuzzy para priorizar análise de usuários
		for(UserResultDataMining u : usersResult){
			if(!IGNORE_ZERO || (IGNORE_ZERO && u.getTimesAverageOk() > 0 && u.getTimesAverageOk() > 0)){
//				/* Teste A-T-C OK */
//				u.setFuzzyPriority(Fuzzy.fuzzyPriority3(u.getUncompleteNormalized(), u.getTimesAvarageOkNormalized(), u.getActionsAvarageOkNormalized(), DEBUG));
				
//				/* Teste A-T-C */
//				u.setFuzzyPriority(Fuzzy.fuzzyPriority3(u.getUncompleteNormalized(), u.getTimesAvarageNormalized(), u.getActionsAvarageNormalized(), DEBUG));
				
//				/* Teste A-T Ok */
//				u.setFuzzyPriority(Fuzzy.fuzzyPriority(u.getTimesAvarageOkNormalized(), u.getActionsAvarageOkNormalized(), DEBUG));
				
//				/* Teste A-T */
//				u.setFuzzyPriority(Fuzzy.fuzzyPriority(u.getTimesAvarageNormalized(), u.getActionsAvarageNormalized(), DEBUG));
			}else{
				u.setFuzzyPriority(0d);
			}
		}
		/* Fuzzy CMeans */
		Fuzzy.executeFuzzySystemWithFCM(usersResult, IGNORE_ZERO, DEBUG);
		
		
		//Fuzzy para priorizar análise de sessões
		for(SessionResultDataMining s : sessionsResult){
			s.setActionsNormalized(s.getActions().size()/maxActionOk);
			s.setTimeNormalized(s.getTime()/maxTimeOk);
			s.setUserRateSuccess(userRateSuccess.get(s.getUsername()));
		}
		
		Fuzzy.executeFuzzySystemWithFCMSession(sessionsResult, IGNORE_ZERO, DEBUG);
		
		
		//Reverse Map ids
		HashMap<String, String> pageViewActionIdsReverse = new HashMap<String, String>();
		Set<String> keySet = pageViewActionIds.keySet();
		for(String k : keySet){
			pageViewActionIdsReverse.put(pageViewActionIds.get(k), k);
		}
		
		// pageViewActionIdsReverse
		ResultDataMining result = new ResultDataMining(usersResult, sessionsResult, actionsTaskAverageOk, timesTaskAverageOk, countoktotal, counterrototal, countinittotal, countthresholdtotal, pageViewActionIdsReverse, pageViewActionCount);
		
		//required
		Double required = countActionsRequiredTask > 0 ? (countActionsRequiredTask/(countTaskSessions * countActionsRequired)) : 0;
		
		result.setRateRequired(required);
		result.setActionsRequiredTask(actionsRequiredTask);
		
		result.setMeanActionsOk(StatisticsUtils.getMean(convertDouble(actionsOkSize)));
		result.setMeanTimesOk(StatisticsUtils.getMean(convertDouble(timesOkSize)));
		//
		result.setStdDevActionsOk(StatisticsUtils.getStdDevPopulation(convertDouble(actionsOkSize)));
		result.setStdDevTimesOk(StatisticsUtils.getStdDevPopulation(convertDouble(timesOkSize)));
		//
		result.setMinActionsOk(minDouble(actionsOkSize));
		result.setMinTimesOk(minDouble(timesOkSize));
		//
		result.setMaxActionsOk(maxDouble(actionsOkSize));
		result.setMaxTimesOk(maxDouble(timesOkSize));
		
		return result;
	}
	
	private static boolean listContainsAction(List<ActionDataMining> list, ActionDataMining action){
		for(ActionDataMining a : list){
			if(a.getsActionType().equals(action.getsActionType())
				&& a.getsJhm().equals(action.getsJhm())
				//&& a.getsUrl().equals(action.getsUrl())
				&& a.getsStepJhm().equals(action.getsStepJhm())
				&& a.getsXPath().replaceAll("\\\\", "").equals(action.getsXPath())){
				return true;
			}
		}
		return false;
	}
	
	private static HashMap<String, Integer> getActionsRequired(List<String> actionsRequired) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
		HashMap<String, Integer> actionsRequiredSession = new HashMap<String, Integer>();
		for(String a : actionsRequired){
			actionsRequiredSession.put(a, 0);
		}
		return actionsRequiredSession;
	}
	
	private static double minDouble(List<Double> numbers){
		double min = Double.MAX_VALUE;
	    Iterator<Double> iterator = numbers.iterator();
	    while (iterator.hasNext()){
	    	double n = iterator.next().doubleValue();
	    	min = min > n ? n : min;
	    }
	    return min;
	}
	
	private static double maxDouble(List<Double> numbers){
		double max = Double.MIN_VALUE;
	    Iterator<Double> iterator = numbers.iterator();
	    while (iterator.hasNext()){
	    	double n = iterator.next().doubleValue();
	    	max = max < n ? n : max;
	    }
	    return max;
	}
	
	private static double[] convertDouble(List<Double> numbers){
		double[] ret = new double[numbers.size()];
	    Iterator<Double> iterator = numbers.iterator();
	    for (int i = 0; i < ret.length; i++){
	        ret[i] = iterator.next().doubleValue();
	    }
	    return ret;
	}
	
}
