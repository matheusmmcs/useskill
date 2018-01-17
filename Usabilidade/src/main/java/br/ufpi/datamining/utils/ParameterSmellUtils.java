package br.ufpi.datamining.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.ufpi.datamining.models.ParameterSmellDataMining;
import br.ufpi.datamining.models.ParameterSmellTestDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.repositories.ParameterSmellDataMiningRepository;
import br.ufpi.datamining.repositories.ParameterSmellTestDataMiningRepository;
import br.ufpi.datamining.repositories.TestDataMiningRepository;

public class ParameterSmellUtils {
	
	/**
	 * Método que averigua se os parametros dos smells já foram instanciados;
	 * 
	 * @param idTest
	 */
	public static List<ParameterSmellTestDataMining> findParametersSmellValues(Long idTest,
			ParameterSmellDataMiningRepository parameterSmellDataMiningRepository,
			ParameterSmellTestDataMiningRepository parameterSmellTestDataMiningRepository) {
		
		EntityManager entityDefaultManager = EntityDefaultManagerUtil.getEntityManager();
		TestDataMiningRepository testDataMiningRepository = new TestDataMiningRepository(entityDefaultManager);
		
		List<ParameterSmellTestDataMining> listValueParameterSmellsTest = new ArrayList<ParameterSmellTestDataMining>();
		
		List<ParameterSmellDataMining> listParameterSmells = parameterSmellDataMiningRepository.findAll();
		for (ParameterSmellDataMining ps : listParameterSmells) {
			ParameterSmellTestDataMining valueParameterSmellTest = parameterSmellTestDataMiningRepository.getValueParameterSmellTest(idTest, ps.getId());
			if (valueParameterSmellTest == null) {
				TestDataMining testDataMining = testDataMiningRepository.find(idTest);
				
				valueParameterSmellTest = new ParameterSmellTestDataMining();
				valueParameterSmellTest.setTest(testDataMining);
				valueParameterSmellTest.setParameterSmell(ps);
				valueParameterSmellTest.setValue(-1.0);
				
				parameterSmellTestDataMiningRepository.create(valueParameterSmellTest);
				System.out.println("Parameter initialized: " + ps.getDescription());
			}
//			else {
//				System.out.println("Já criado: " + ps.getDescription() + " ... id: " + valueParameterSmellTest.getId());
//			}
			listValueParameterSmellsTest.add(valueParameterSmellTest);
		}
		return listValueParameterSmellsTest;
	}
	
//	/**
//	 * Inicializa todos os parametros de smells que ainda não existem no BD;
//	 * 
//	 * @param testId
//	 */
//	public static List<ParameterSmellDataMining> checkParametersSmellInicialization(Long testId,
//			ParameterSmellDataMiningRepository parameterSmellDataMiningRepository) {
//		
//		Set<Long> initializedSmellsIds = new HashSet<Long>();
//		List<ParameterSmellDataMining> listParameterSmells = parameterSmellDataMiningRepository.findAll();
//		for (ParameterSmellDataMining ps : listParameterSmells) {
//			initializedSmellsIds.add(ps.getIdSmell());
//		}
//		
//		Long[] smellsIds = {2l, 3l};
//		List<ParameterSmellDataMining> listValueParameterSmellsTest = new ArrayList<ParameterSmellDataMining>();
//		for (int i = 0; i < smellsIds.length; i++) {
//			if (!initializedSmellsIds.contains(smellsIds[i])) {
//				List<String> descriptions = new ArrayList<String>();
//				if (smellsIds[i] == 1) {
//					
//				} else if (smellsIds[i] == 2) {
//					descriptions.add("Taxa de ciclos máxima");
//				} else if (smellsIds[i] == 3) {
//					descriptions.add("Taxa de ocorrência máxima");
//					descriptions.add("Número mínimo de ocorrências");
//				} else if (smellsIds[i] == 4) {
//					
//				} else if (smellsIds[i] == 5) {
//					
//				} else if (smellsIds[i] == 6) {
//					
//				} else if (smellsIds[i] == 7) {
//					
//				}
//				
//				for (String description : descriptions) {
//					ParameterSmellDataMining parameterSmellDataMining = new ParameterSmellDataMining();
//					parameterSmellDataMining.setDescription(description);
//					parameterSmellDataMining.setIdSmell(smellsIds[i]);
//					parameterSmellDataMiningRepository.create(parameterSmellDataMining);
//					listValueParameterSmellsTest.add(parameterSmellDataMining);
//				}
//			}
//		}
//		
//		return listValueParameterSmellsTest;
//	}
	
}
