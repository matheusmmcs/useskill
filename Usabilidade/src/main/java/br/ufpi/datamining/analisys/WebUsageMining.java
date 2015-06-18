package br.ufpi.datamining.analisys;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.aux.FieldSearch;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.aux.SessionResultDataMining;
import br.ufpi.datamining.models.aux.UserResultDataMining;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningEnum;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.utils.EntityManagerUtil;



/*
 * Gambis:
 * 1 - retirar todos os \ dos xpath
 * 
 * 
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
	
	*** Dados importantes:
	 * Diferentemente de capturas de logs no servidor, a identificacao das pageviews, do usuario e das sessoes sao feitas antes no cleaning;
	 * Identificar pageView -> padronizo as url e crio o um set;
	 * Identificar pageAction -> XPath;
	 
	 */
	
	//----- NOVA ABORDAGEM -----
	//-----
	//ok- usuário: 	conjunto de sessões;
	//ok- sessão: 	classificação, quantidade de ações, tempo e todas as ações que foram realizadas;
	//-----
	//- média de ações por classificação;
	//- média de tempo por classificação;
	//- ações mais realizadas por classificação;
	//ok- média de ações por usuário;
	//ok- média de tempo por usuário;
	//ok- taxa de ok, repet e erro por usuário;
	//-----
	//- evolução do usuário;
	//- classificar o usuário como experiente/novato; (especialista)
	//-----
	//grafo e clusterização, fuzzy para priorizar análises
	//-----
	
	
	public static ResultDataMining analyze(Long taskId) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		TaskDataMiningRepository taskDataMiningRepository = new TaskDataMiningRepository(entityManager);
		ActionDataMiningRepository actionDataMiningRepository = new ActionDataMiningRepository(entityManager);
		
		
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
				fieldsSearch.add(new FieldSearch(f.getField(), f.valueToObject()));
			}
			
			List<ActionDataMining> actions = actionDataMiningRepository.getActions(fieldsSearch);
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
		
		Set<String> usersWithInitialActions = initialActionOfsectionsFromUser.keySet();
		for(String username : usersWithInitialActions){
			List<ActionDataMining> userInitialActions = initialActionOfsectionsFromUser.get(username);
			//variaveis para contabilizar resultado dos usuarios
			int countok = 0, counterro = 0, countinit = 0, countUserSessions = userInitialActions.size();
			double countUserActionsSessions = 0, countUserTimesSessions = 0;
			List<String> sessionsResultIds = new ArrayList<String>();
			
			//verificar cada sessao
			for(int i = 0; i < countUserSessions; i++){
				ActionDataMining initialAction = userInitialActions.get(i);
				List<ActionDataMining> userSectionActions;
				
				//se for a ultima sessão do usuário
				if(i == countUserSessions-1){
					userSectionActions = actionDataMiningRepository.getUserActions(username, initialAction.getId());
				}else{
					ActionDataMining nextInitialAction = userInitialActions.get(i+1);
					userSectionActions = actionDataMiningRepository.getUserActions(username, initialAction.getId(), nextInitialAction.getId());
				}
				
				//analise das acoes
				SessionClassificationDataMiningEnum classification = SessionClassificationDataMiningEnum.ERROR;
				List<ActionDataMining> userSectionRealActions = new ArrayList<ActionDataMining>();
				int countActions = 0, sectionSize = userSectionActions.size();
				long initialTime = 0l, endTime = 0l;
				boolean okThreshold = true;
				
				//iterar acoes do usuario na sessao
				ActionDataMining action = null;
				for(int j = 0; j < sectionSize; j++){
					
					if(okThreshold){
						action = userSectionActions.get(j);
						userSectionRealActions.add(action);
						countActions++;
						
						if(j == 0){
							initialTime = action.getsTime();
						}else{
							//vejo limiar com o anterior
							ActionDataMining previousAction = userSectionActions.get(j-1);
							BigDecimal diff = new BigDecimal(action.getsTime() - previousAction.getsTime());
							if(diff.compareTo(threshold) == 1){
								okThreshold = false;
								break;
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
				
				endTime = action.getsTime();
				
				//resultados da sessao
				String sessionKey = username+"-"+i;
				Long sessionTime = endTime - initialTime;
				sessionsResult.add(new SessionResultDataMining(sessionKey, classification, sessionTime, userSectionRealActions, !okThreshold));
				
				
				//resultados do usuario por sessao
				sessionsResultIds.add(sessionKey);
				countUserActionsSessions += countActions;
				countUserTimesSessions += sessionTime;
				if(classification.equals(SessionClassificationDataMiningEnum.ERROR)){
					counterro++;
				}else if(classification.equals(SessionClassificationDataMiningEnum.SUCCESS)){
					countok++;
				}else if(classification.equals(SessionClassificationDataMiningEnum.REPEAT)){
					countinit++;
				}
				
			}
			
			//resultados por usuario
			Double actionsAverage = (countUserActionsSessions / countUserSessions);
			Double timesAverage = (countUserTimesSessions / countUserSessions);
			
			usersResult.add(new UserResultDataMining(username, actionsAverage, timesAverage, countok, counterro, countinit, sessionsResultIds));
			System.out.println(username + " [ok=" + countok + ", init=" + countinit + ", erro=" + counterro + "]");
		}
		
		ResultDataMining result = new ResultDataMining(usersResult, sessionsResult);
		
		return result;
	}
	
	private static boolean listContainsAction(List<ActionDataMining> list, ActionDataMining action){
		
		for(ActionDataMining a : list){
			if(a.getsActionType().equals(action.getsActionType())
				&& a.getsJhm().equals(action.getsJhm())
				&& a.getsUrl().equals(action.getsUrl())
				&& a.getsStepJhm().equals(action.getsStepJhm())
				&& a.getsXPath().replaceAll("\\\\", "").equals(action.getsXPath())){
				return true;
			}
		}
		return false;
	}
	
}
