/**
 * 
 */
package br.ufpi.analise;

import org.apache.commons.math.stat.descriptive.moment.Variance;

/**Classe que ora fazer todos os calculos estatistico da aplicação
 * @author Cleiton
 * 
 */
public class Estatistica {
	private Variance variancia;

	/**
	 * 
	 */
	public Estatistica() {
		super();
		variancia = new Variance();
	}

	/**
	 * Obtem o desvio padrão de um array de numeros passados
	 * 
	 * @param observed
	 * @return
	 */
	public double desvioPadrao(double[] observed) {
		return Math.sqrt(this.variancia.evaluate(observed));
	}
}
