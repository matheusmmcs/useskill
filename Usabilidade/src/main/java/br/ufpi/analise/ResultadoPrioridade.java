package br.ufpi.analise;


import java.text.DecimalFormat;
import java.util.HashMap;

import br.ufpi.analise.enums.TipoAlgoritmoPrioridade;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Usuario;

public class ResultadoPrioridade {
	
	private TipoAlgoritmoPrioridade tipoAlgoritmo;
	
	private Fluxo fluxo;
	
	private Double prioridade;
	
	private HashMap<String, Double> parametros;

	public ResultadoPrioridade() {
		super();
		parametros = new HashMap<String, Double>();
	}
	
	public ResultadoPrioridade(TipoAlgoritmoPrioridade tipoAlgoritmo, Fluxo fluxo, Double prioridade) {
		super();
		this.tipoAlgoritmo = tipoAlgoritmo;
		this.fluxo = fluxo;
		this.prioridade = prioridade;
		this.parametros = new HashMap<String, Double>();
	}

	/**
	 * @return the usuario
	 */
	public Usuario getFluxoUsuario() {
		return this.fluxo.getUsuario();
	}
	
	/**
	 * @return the fluxo
	 */
	public Fluxo getFluxo() {
		return fluxo;
	}

	/**
	 * @param fluxo the fluxo to set
	 */
	public void setFluxo(Fluxo fluxo) {
		this.fluxo = fluxo;
	}

	/**
	 * @return the prioridade
	 */
	public Double getPrioridade() {
		return prioridade;
	}

	/**
	 * @param prioridade the prioridade to set
	 */
	public void setPrioridade(Double prioridade) {
		this.prioridade = prioridade;
	}

	/**
	 * @return the parametros
	 */
	public HashMap<String, Double> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(HashMap<String, Double> parametros) {
		this.parametros = parametros;
	}
	
	public void addParametro(String nome, Double valor){
		this.parametros.put(nome, valor);
	}

	/**
	 * @return the tipoAlgoritmo
	 */
	public TipoAlgoritmoPrioridade getTipoAlgoritmo() {
		return tipoAlgoritmo;
	}

	/**
	 * @param tipoAlgoritmo the tipoAlgoritmo to set
	 */
	public void setTipoAlgoritmo(TipoAlgoritmoPrioridade tipoAlgoritmo) {
		this.tipoAlgoritmo = tipoAlgoritmo;
	}
	
	public String toPrintString(){
		DecimalFormat df = new DecimalFormat("#.0000");
		if(tipoAlgoritmo.equals(TipoAlgoritmoPrioridade.AcoesPorTempo)){
			return "User["+fluxo.getTarefa().getId()+"-"+fluxo.getTipoConvidado()+"]: "+fluxo.getUsuario().getNome() + " / Acoes: "+ parametros.get("acoes") +"; Time: "+ df.format(parametros.get("tempo")) +" => Eficiência: "+ parametros.get("eficiencia") +" | Eficacia: " + parametros.get("eficacia") + " | Prioridade: " + prioridade;
		}else if(tipoAlgoritmo.equals(TipoAlgoritmoPrioridade.AcoesMelhorCaminhoPorAcoes)){
			return "User["+fluxo.getTarefa().getId()+"-"+fluxo.getTipoConvidado()+"]: "+fluxo.getUsuario().getNome() + " / AcoesMelhorCaminho: "+ parametros.get("acoesMelhorCaminho") +"; Acoes: "+ df.format(parametros.get("acoes")) +" => Eficiência: "+ parametros.get("eficiencia") +" | Eficacia: " + parametros.get("eficacia") + " | Prioridade: " + prioridade;
		}else if(tipoAlgoritmo.equals(TipoAlgoritmoPrioridade.DoisFuzzy)){
			return "User["+fluxo.getTarefa().getId()+"-"+fluxo.getTipoConvidado()+"]: "+fluxo.getUsuario().getNome() + " / Tempo: "+ parametros.get("tempo") +"; Acoes: "+ parametros.get("acoes") +" => Eficiência: "+ parametros.get("eficiencia") +" | Eficacia: " + parametros.get("eficacia") + " | Prioridade: " + prioridade;
		}else if(tipoAlgoritmo.equals(TipoAlgoritmoPrioridade.FuzzyTresParams)){
			return "User["+fluxo.getTarefa().getId()+"-"+fluxo.getTipoConvidado()+"]: "+fluxo.getUsuario().getNome() + " / Tempo: "+ parametros.get("tempo") +" | Acoes: "+ parametros.get("acoes") +" | Eficacia: " + parametros.get("eficacia") + " | Prioridade: " + prioridade;
		}else{
			return "";
		}
	}
	
}
