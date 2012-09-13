/**
 * 
 */
package br.ufpi.analise;

import java.util.List;

import br.ufpi.analise.enums.TipoMedia;
import br.ufpi.models.vo.FluxoVO;

/**
 * Classe que ora fazer todos os calculos estatistico da aplicação
 * 
 * @author Cleiton
 * 
 */
public class Estatistica {

	/**
	 * 
	 */
	public Estatistica() {
		super();
	}

	/**
	 * Obtem o desvio padrão de uma Lista de numeros passados
	 * 
	 * @param temposDetodosFluxos
	 * @return
	 */
	public double desvioPadrao(List<Long> objetos) {
		if (objetos.size() == 1) {
			return 0.0;
		} else {
			double mediaAritimetica = mediaAritimetica(objetos);
			double somatorio = 0l;
			for (int i = 0; i < objetos.size(); i++) {
				double result = objetos.get(i) - mediaAritimetica;
				somatorio = somatorio + result * result;
			}
			return Math.sqrt(((double) 1 / (objetos.size() - 1)) * somatorio);
		}
	}

	/**
	 * Obtem o a media aritmetica de um array de Elementos
	 * 
	 * @param temposDetodosFluxos
	 * @return
	 */
	public double mediaAritimetica(List<Long> objetos) {
		double somatorio = 0l;
		for (Long d : objetos) {
			somatorio += d;
		}
		return somatorio / objetos.size();
	}

	/**
	 * @param mediaAritimetica
	 * @param desvioPadrao
	 * @param fluxos
	 */
	public void classificarUsuarios(double mediaAritimetica,
			double desvioPadrao, List<FluxoVO> fluxos) {
		for (FluxoVO fluxoVO : fluxos) {
			if (fluxoVO.getTempoRealicao() < mediaAritimetica - desvioPadrao)
				fluxoVO.setMedia(TipoMedia.ACIMA);
			else {
				if (fluxoVO.getTempoRealicao() > mediaAritimetica
						+ desvioPadrao)
					fluxoVO.setMedia(TipoMedia.ACIMA);
				else
					fluxoVO.setMedia(TipoMedia.MEDIA);

			}

		}

	}

}
