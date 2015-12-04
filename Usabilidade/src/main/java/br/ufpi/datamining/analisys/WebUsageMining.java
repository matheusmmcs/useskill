package br.ufpi.datamining.analisys;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.aux.CorrectnessTask;
import br.ufpi.datamining.models.aux.CountActionsAux;
import br.ufpi.datamining.models.aux.FieldSearch;
import br.ufpi.datamining.models.aux.FieldSearchComparatorEnum;
import br.ufpi.datamining.models.aux.OrderSearch;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.aux.SessionResultDataMining;
import br.ufpi.datamining.models.aux.UserResultDataMining;
import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningEnum;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.datamining.utils.EntityDefaultManagerUtil;
import br.ufpi.datamining.utils.EntityManagerUtil;
import br.ufpi.datamining.utils.StatisticsUtils;
import br.ufpi.datamining.utils.UsabilityUtils;
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
		EntityManager entityDafaultManager = EntityDefaultManagerUtil.getEntityManager();
		
		TaskDataMiningRepository taskDataMiningRepository = new TaskDataMiningRepository(entityDafaultManager);
		ActionDataMiningRepository actionDataMiningRepository = new ActionDataMiningRepository(entityManager);
		
		//01 - 31/08/2015 / 15 (1439607600000)
		analyze(14l, 1438398000000l, 1439607600000l, taskDataMiningRepository, actionDataMiningRepository);
		
		//retorna as ações realizadas entre duas datas
		//List<ActionDataMining> listActionsBetweenDates = WebUsageMining.listActionsBetweenDates(14l, taskDataMiningRepository, actionDataMiningRepository, initialDate, finalDate);
		//System.out.println(listActionsBetweenDates.size());
		
		//ações mais realizadas agrupadas de acordo com filtro
//		List<CountActionsAux> counts = WebUsageMining.countActionsByRestrictions(14l, new FieldSearch("sJhm", "sJhm", null, null), taskDataMiningRepository, actionDataMiningRepository, initialDate, finalDate);
//		for (CountActionsAux c : counts) {
//			System.out.println(c.getDescription() + " -> " + c.getCount());
//		}
		
		//ações realizadas por determinado usuário
//		List<ActionDataMining> actionsFromUserBetweenDates = WebUsageMining.listActionsFromUserBetweenDates(14l, "gomes", new FieldSearch("sJhm", "local", "MarcarExameAuditor", FieldSearchComparatorEnum.EQUALS), taskDataMiningRepository, actionDataMiningRepository, initialDate, finalDate);
//		System.out.println(actionsFromUserBetweenDates.size());
//		for (ActionDataMining a : actionsFromUserBetweenDates) {
//			System.out.println(a.getId() + " = " + a.getsJhm() + ", " + a.getsStepJhm() + " ... " + a.getsActionType());
//		}
		

		//analisar a usabilidade
		
		//ResultDataMining resultDataMining = analyze(14l, taskDataMiningRepository, actionDataMiningRepository);
		//System.out.println(resultDataMining.getSessions().size());
	}
	
	public static ResultDataMining analyze(Long taskId, Long initDate, Long endDate, TaskDataMiningRepository taskDataMiningRepository, ActionDataMiningRepository actionDataMiningRepository) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		
		boolean DEBUG = true;//false;
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
			fieldsSearch.add(new FieldSearch("sActionType", "sActionType", a.getActionType().getAction(), FieldSearchComparatorEnum.EQUALS));
			for(FieldSearchTupleDataMining f : a.getElementFiedlSearch()){
				fieldsSearch.add(new FieldSearch(f.getField(), f.getField(), f.valueToObject(), FieldSearchComparatorEnum.EQUALS));
			}
			for(FieldSearchTupleDataMining f : a.getUrlFieldSearch()){
				if(!f.getField().equals("sUrl")){
					fieldsSearch.add(new FieldSearch(f.getField(), f.getField(), f.valueToObject(), FieldSearchComparatorEnum.EQUALS));
				}
			}
			//verifica janela temporal
			fieldsSearch.add(new FieldSearch("sTime", "sTimeInit", initDate, FieldSearchComparatorEnum.GREATER_EQUALS_THAN));
			fieldsSearch.add(new FieldSearch("sTime", "sTimeEnd", endDate, FieldSearchComparatorEnum.LESS_EQUALS_THAN));
			
			List<ActionDataMining> actions = actionDataMiningRepository.getActions(fieldsSearch, taskDataMining.getDisregardActions(), null, null);
			for(ActionDataMining ai : actions){
				if(initialActionOfsectionsFromUser.get(ai.getsUsername()) == null){
					initialActionOfsectionsFromUser.put(ai.getsUsername(), new ArrayList<ActionDataMining>());
				}
				initialActionOfsectionsFromUser.get(ai.getsUsername()).add(ai);
			}
		}

		System.out.println(initialActionOfsectionsFromUser.size());
		for(String u : initialActionOfsectionsFromUser.keySet()){
			System.out.println(u + " -> " + initialActionOfsectionsFromUser.get(u).size());
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
		
		
		/*
		 DESCRIÇÃO DOS "FORs":
		 
		 itera todos os usuários
			- contabiliza indices (erro, ok, limiar, reinicio, mediatempo, mediaacoes, etc) por usuário;
			itera todas as sessões desse usuário
				- contabiliza índices por sessão;
				itera todas as ações de determinada sessão
					- cria labels que identificam cada ação e contabilizam qtas vezes foram realizadas; (geral, todos usuários)
					- verifica de acordo com as ações se a sessão é repetida, sucesso ou ultrapassou limiar;
		 */
		
		Set<String> usersWithInitialActions = initialActionOfsectionsFromUser.keySet();
		for(String username : usersWithInitialActions){
			
			if (DEBUG) {
				System.out.println("-----"+username+"-----");
			}
			
			List<ActionDataMining> userInitialActions = initialActionOfsectionsFromUser.get(username);
			
			//variaveis para contabilizar resultado dos usuarios
			int countok = 0, counterro = 0, countinit = 0, countthreshold = 0, countUserSessions = userInitialActions.size();
			double countUserActionsSessionsOk = 0, countUserTimesSessionsOk = 0;
			List<String> sessionsResultIds = new ArrayList<String>();
			
			Double countActionsRequiredUser = 0d;
			
			if (DEBUG) {
				System.out.println("UserSessions: "+countUserSessions);
			}
			
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
				
				if (DEBUG) {
					System.out.println("Session("+ (i+1) + "/" + countUserSessions + "), size: " + sectionSize);
				}
				
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
				
				if (DEBUG) {
					System.out.println(", classification: " + classification);
				}
				
				//resultados da sessao
				String sessionKey = username+"-"+i;
				Long sessionTime = endTime - initialTime;
				
				//required
				Double required = CorrectnessTask.calcCorrectnessSession(taskDataMining.getActionsRequired(), actionsRequiredSession, taskDataMining.getActionsRequiredOrder());
				countActionsRequiredUser += required;
				countActionsRequiredTask += required;
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
			Double required = countActionsRequiredUser > 0 ? countActionsRequiredUser / countUserSessions : 0;
			
			usersResult.add(new UserResultDataMining(username, actionsAverageOk, timesAverageOk, countok, counterro, countinit, countthreshold, 0d, required, sessionsResultIds));
			System.out.println(username + " [ok=" + countok + ", init=" + countinit + ", erro=" + counterro + "]");
		}
		
		Double actionsTaskAverageOk = (countTaskActionsSessionsOk / countoktotal);
		Double timesTaskAverageOk = (countTaskTimesSessionsOk / countoktotal);
		
		
		
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
		
		//Cálculo da média e desvio padrão de ações e tempo
		double meanActionsOk = StatisticsUtils.getMean(convertDouble(actionsOkSize));
		double meanTimesOk = StatisticsUtils.getMean(convertDouble(timesOkSize));
		double stdDevActionsOk = StatisticsUtils.getStdDevPopulation(convertDouble(actionsOkSize));
		double stdDevTimesOk = StatisticsUtils.getStdDevPopulation(convertDouble(timesOkSize));
		
		double minEffectiveness = Double.MAX_VALUE;
		double minEfficiency = Double.MAX_VALUE;
		double maxEffectiveness = Double.MIN_VALUE;
		double maxEfficiency = Double.MIN_VALUE;
		
		double minEffectivenessUser = Double.MAX_VALUE;
		double minEfficiencyUser = Double.MAX_VALUE;
		double maxEffectivenessUser = Double.MIN_VALUE;
		double maxEfficiencyUser = Double.MIN_VALUE;
		
		if(initialActionOfsectionsFromUser.size() > 0){
			//#4.1 - Cálculo da Eficácia e Eficiência
			//permitir que seja possível normalizar os dados das sessões
			HashMap<String, Double> userRateSuccess = new HashMap<String, Double>();
			for(UserResultDataMining u : usersResult){
				u.setMaxActionsAverageOk(maxActionsAverageOk);
				u.setMaxTimeAverageOk(maxTimeAverageOk);
				userRateSuccess.put(u.getUsername(), u.getUncompleteNormalized());
				
				//Proposta de Zscore, eficácia e eficiência
				u.setzScoreActions((u.getActionsAverageOk() - meanActionsOk)/stdDevActionsOk);
				u.setzScoreTimes((u.getTimesAverageOk() - meanTimesOk)/stdDevTimesOk);
				u.setEffectiveness(UsabilityUtils.calcEffectiveness(u.getRateSuccess(), u.getUserRateRequired()));
				//u.setEfficiency(UsabilityUtils.calcEfficiency(u.getEffectiveness(), u.getzScoreActions(), u.getzScoreTimes()));
				u.setEfficiency(UsabilityUtils.calcEfficiency(u.getEffectiveness(), u.getActionsAverageOk(), (u.getTimesAverageOk()/1000)));
				
				minEffectivenessUser = minEffectivenessUser > u.getEffectiveness() ? u.getEffectiveness() : minEffectivenessUser;
				minEfficiencyUser = minEfficiencyUser > u.getEfficiency() ? u.getEfficiency() : minEfficiencyUser;
				maxEffectivenessUser = maxEffectivenessUser < u.getEffectiveness() ? u.getEffectiveness() : maxEffectivenessUser;
				maxEfficiencyUser = maxEfficiencyUser < u.getEfficiency() ? u.getEfficiency() : maxEfficiencyUser;
			}
			
			for(UserResultDataMining u : usersResult){
				u.setEffectivenessNormalized( (u.getEffectiveness() - minEffectivenessUser) / (maxEffectivenessUser - minEffectivenessUser));
				u.setEfficiencyNormalized( (u.getEfficiency() - minEfficiencyUser) / (maxEfficiencyUser - minEfficiencyUser));
				
				if(u.getEffectiveness().equals(Double.NaN)){
					u.setEffectiveness(0d);
				}
				if(u.getEfficiency().equals(Double.NaN)){
					u.setEfficiency(0d);
				}
				if(u.getEffectivenessNormalized().equals(Double.NaN)){
					u.setEffectivenessNormalized(0d);
				}
				if(u.getEfficiencyNormalized().equals(Double.NaN)){
					u.setEfficiencyNormalized(0d);
				}
			}
			
			for(SessionResultDataMining s : sessionsResult){
				s.setUserRateSuccess(100 - (userRateSuccess.get(s.getUsername()) * 100));
				s.setActionsNormalized(s.getActions().size()/maxActionOk);
				s.setTimeNormalized(s.getTime()/maxTimeOk);
				
				//Proposta de Zscore, eficácia e eficiência
				
				s.setzScoreActions((s.getActions().size() - meanActionsOk)/stdDevActionsOk);
				s.setzScoreTimes((s.getTime() - meanTimesOk)/stdDevTimesOk);
				
				s.setEffectiveness(UsabilityUtils.calcEffectiveness(s.getUserRateSuccess(), s.getUserRateRequired()));
				//s.setEfficiency(UsabilityUtils.calcEfficiency(s.getEffectiveness(), s.getzScoreActions(), s.getzScoreTimes()));
				Double time = new Double(s.getTime()/1000);
				s.setEfficiency(UsabilityUtils.calcEfficiency(s.getEffectiveness(), (double) s.getActions().size(), time));
				
				minEffectiveness = minEffectiveness > s.getEffectiveness() ? s.getEffectiveness() : minEffectiveness;
				minEfficiency = minEfficiency > s.getEfficiency() ? s.getEfficiency() : minEfficiency;
				maxEffectiveness = maxEffectiveness < s.getEffectiveness() ? s.getEffectiveness() : maxEffectiveness;
				maxEfficiency = maxEfficiency < s.getEfficiency() ? s.getEfficiency() : maxEfficiency;
			}
			
			for(SessionResultDataMining s : sessionsResult){
				s.setEffectivenessNormalized( (s.getEffectiveness() - minEffectiveness) / (maxEffectiveness - minEffectiveness));
				s.setEfficiencyNormalized( (s.getEfficiency() - minEfficiency) / (maxEfficiency - minEfficiency));
				
				if(s.getEffectiveness().equals(Double.NaN)){
					s.setEffectiveness(0d);
				}
				if(s.getEfficiency().equals(Double.NaN)){
					s.setEfficiency(0d);
				}
				if(s.getEffectivenessNormalized().equals(Double.NaN)){
					s.setEffectivenessNormalized(0d);
				}
				if(s.getEfficiencyNormalized().equals(Double.NaN)){
					s.setEfficiencyNormalized(0d);
				}
			}
			
			
			/* Fuzzy CMeans */
			DEBUG = false;
			Fuzzy.executeFuzzySystemWithFCMUsersEfcEft(usersResult, IGNORE_ZERO, DEBUG);
			System.out.println("### Fuzzy CMeans Users EfcEft Ok");
			
			Fuzzy.executeFuzzySystemWithFCMSessionEfcEft(sessionsResult, IGNORE_ZERO, DEBUG);
			System.out.println("### Fuzzy CMeans Sessions EfcEft Ok");
			
			Fuzzy.executeFuzzySystemWithFCM(usersResult, IGNORE_ZERO, DEBUG);
			System.out.println("### Fuzzy CMeans Users Ok");
			
			Fuzzy.executeFuzzySystemWithFCMSession(sessionsResult, IGNORE_ZERO, DEBUG);
			System.out.println("### Fuzzy CMeans Sessions Ok");
		}
		
		
		
		//Reverse Map ids
		HashMap<String, String> pageViewActionIdsReverse = new HashMap<String, String>();
		Set<String> keySet = pageViewActionIds.keySet();
		for(String k : keySet){
			pageViewActionIdsReverse.put(pageViewActionIds.get(k), k);
		}
		
		// pageViewActionIdsReverse
		ResultDataMining result = new ResultDataMining(usersResult, sessionsResult, actionsTaskAverageOk, timesTaskAverageOk, countoktotal, counterrototal, countinittotal, countthresholdtotal, pageViewActionIdsReverse, pageViewActionCount);
		
		//required
		Double required = countActionsRequiredTask > 0 ? countActionsRequiredTask / countTaskSessions : 0;
		
		result.setRateRequired(required);
		result.setActionsRequiredTask(actionsRequiredTask);
		
		result.setMeanActionsOk(meanActionsOk);
		result.setMeanTimesOk(meanTimesOk);
		//
		result.setStdDevActionsOk(stdDevActionsOk);
		result.setStdDevTimesOk(stdDevTimesOk);
		//
		result.setMinActionsOk(minDouble(actionsOkSize));
		result.setMinTimesOk(minDouble(timesOkSize));
		//
		result.setMaxActionsOk(maxDouble(actionsOkSize));
		result.setMaxTimesOk(maxDouble(timesOkSize));
		
		System.out.println("MaxActions = "+result.getMaxActionsOk()+" ;MaxTime = "+result.getMaxTimesOk());
		System.out.println("Actions = " + (result.getMaxActionsOk() - result.getMeanActionsOk()) / result.getStdDevActionsOk());
		System.out.println("Times = " + (result.getMaxTimesOk() - result.getMeanTimesOk()) / result.getStdDevTimesOk());
		
		return result;
	}
	
	public static List<CountActionsAux> countActionsByRestrictions(Long taskId, FieldSearch fieldGroup, TestDataMiningRepository testDataMiningRepository, ActionDataMiningRepository actionDataMiningRepository, Date initialDate, Date finalDate) {
		TestDataMining taskDataMining = testDataMiningRepository.find(taskId);
		String clientAbbreviation = taskDataMining.getClientAbbreviation();
		
		List<FieldSearch> fieldsSearch = new ArrayList<FieldSearch>();
		fieldsSearch.add(new FieldSearch("sClient", "sClient", clientAbbreviation, FieldSearchComparatorEnum.EQUALS));
		if (initialDate != null) {
			fieldsSearch.add(new FieldSearch("sTime", "sTime1", initialDate.getTime(), FieldSearchComparatorEnum.GREATER_EQUALS_THAN));
		}
		if (finalDate != null) {
			fieldsSearch.add(new FieldSearch("sTime", "sTime2", finalDate.getTime(), FieldSearchComparatorEnum.LESS_EQUALS_THAN));
		}
		
		return actionDataMiningRepository.getCountActionsByRestrictions(fieldGroup, fieldsSearch, null);
	}
	
	public static List<ActionDataMining> listActionsBetweenDates(Long taskId, TaskDataMiningRepository taskDataMiningRepository, ActionDataMiningRepository actionDataMiningRepository, Date initialDate, Date finalDate, Long limit) {
		TaskDataMining taskDataMining = taskDataMiningRepository.find(taskId);
		String clientAbbreviation = taskDataMining.getTestDataMining().getClientAbbreviation();
		
		List<FieldSearch> fieldsSearch = new ArrayList<FieldSearch>();
		fieldsSearch.add(new FieldSearch("sClient", "sClient", clientAbbreviation, FieldSearchComparatorEnum.EQUALS));
		if (initialDate != null) {
			fieldsSearch.add(new FieldSearch("sTime", "sTime1", initialDate.getTime(), FieldSearchComparatorEnum.GREATER_EQUALS_THAN));
		}
		if (finalDate != null) {
			fieldsSearch.add(new FieldSearch("sTime", "sTime2", finalDate.getTime(), FieldSearchComparatorEnum.LESS_EQUALS_THAN));
		}
		
		return actionDataMiningRepository.getActions(fieldsSearch, null, new OrderSearch("sTime", true), limit);
	}
	
	public static List<ActionDataMining> listActionsFromUserBetweenDates(String clientAbbreviation, String username, FieldSearch actionLocal, ActionDataMiningRepository actionDataMiningRepository, Date initialDate, Date finalDate, Long limit) {
		List<FieldSearch> fieldsSearch = new ArrayList<FieldSearch>();
		fieldsSearch.add(new FieldSearch("sClient", "sClient", clientAbbreviation, FieldSearchComparatorEnum.EQUALS));
		fieldsSearch.add(new FieldSearch("sUsername", "sUsername", username, FieldSearchComparatorEnum.EQUALS));
		
		if (initialDate != null) {
			fieldsSearch.add(new FieldSearch("sTime", "sTime1", initialDate.getTime(), FieldSearchComparatorEnum.GREATER_EQUALS_THAN));
		}
		if (finalDate != null) {
			fieldsSearch.add(new FieldSearch("sTime", "sTime2", finalDate.getTime(), FieldSearchComparatorEnum.LESS_EQUALS_THAN));
		}
		if (actionLocal != null) {
			fieldsSearch.add(new FieldSearch(actionLocal.getField(), actionLocal.getAlias(), actionLocal.getValue(), FieldSearchComparatorEnum.EQUALS));
		}
		
		return actionDataMiningRepository.getActions(fieldsSearch, null, new OrderSearch("sTime", true), limit);
	}
	
	private static boolean listContainsAction(List<ActionDataMining> list, ActionDataMining action){
		for(ActionDataMining a : list){
			
			if(a.getsActionType().equals(action.getsActionType())
				&& a.getsJhm().equals(action.getsJhm())
				//&& a.getsUrl().equals(action.getsUrl())
				&& a.getsStepJhm().equals(action.getsStepJhm())){
				
				if(action.getsActionType().equals(ActionTypeDataMiningEnum.click.getAction()) ||
					action.getsActionType().equals(ActionTypeDataMiningEnum.focusout.getAction()) ||
					action.getsActionType().equals(ActionTypeDataMiningEnum.mouseover.getAction())){
					
					if(a.getsXPath().replaceAll("\\\\", "").equals(action.getsXPath())){
						return true;
					}
				}else{
					return true;
				}
				
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
