/**
 * 
 */
package br.ufpi.analise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.analise.enums.TipoMedia;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.ConvidadoCount;
import br.ufpi.models.vo.FluxoCountVO;
import br.ufpi.models.vo.FluxoVO;
import br.ufpi.models.vo.RespostaAlternativaVO;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;

/**
 * Classe que ora fazer todos os calculos estatistico da aplicação
 * 
 * @author Cleiton
 * 
 */
@Component
public class Estatistica {
	private final TesteRepository testeRepository;
	private final Result result;
	private final TarefaRepository tarefaRepository;

	/**
	 * @param testeRepository
	 * @param result
	 * @param tarefaRepository
	 */
	public Estatistica(TesteRepository testeRepository, Result result,
			TarefaRepository tarefaRepository) {
		super();
		this.testeRepository = testeRepository;
		this.result = result;
		this.tarefaRepository = tarefaRepository;
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
	 * Obtem o desvio padrão de uma Lista de numeros passados
	 * 
	 * @param temposDetodosFluxos
	 * @return
	 */
	private HashMap<String, Double> desvioPadraoEMedia(List<FluxoCountVO> objetos) {
		HashMap<String, Double> retorno = new HashMap<String, Double>();
		HashMap<String, Double> mediaAritimetica = mediaAritimeticaFluvoCount(objetos);
		double mediaAritimeticaTempo = mediaAritimetica.get("tempo");
		double mediaAritimeticaAcao = mediaAritimetica.get("acao");
		if (objetos.size() == 1) {
			retorno.put("desvio_tempo", 0.0);
			retorno.put("desvio_acao", 0.0);
		} else {
			double somatorioTempo = 0.0;
			double somatorioAcoes = 0.0;
			for (int i = 0; i < objetos.size(); i++) {
				double resultTempo = objetos.get(i).getTempoRealizacao()
						- mediaAritimeticaTempo;
				double resultAcao = objetos.get(i).getQuantidadeAcoes()
						- mediaAritimeticaAcao;
				somatorioTempo = somatorioTempo + resultTempo * resultTempo;
				somatorioAcoes = somatorioAcoes + resultAcao * resultAcao;
			}
			retorno.put(
					"desvio_tempo",
					Math.sqrt(((double) 1 / (objetos.size() - 1))
							* somatorioTempo));
			retorno.put(
					"desvio_acao",
					Math.sqrt(((double) 1 / (objetos.size() - 1))
							* somatorioAcoes));
		}
		retorno.put("mediaTempo", mediaAritimeticaTempo);
		retorno.put("mediaAcao", mediaAritimeticaAcao);
		return retorno;
	}

	/**
	 * @param objetos
	 * @return
	 */
	private HashMap<String, Double> mediaAritimeticaFluvoCount(
			List<FluxoCountVO> objetos) {
		HashMap<String, Double> retorno = new HashMap<String, Double>();
		int quantidadeObjetos = objetos.size();
		if (quantidadeObjetos > 0) {
			double somatorioTempo = 0.0;
			double somatorioAcoes = 0.0;
			for (FluxoCountVO fluxoCountVO : objetos) {
				somatorioAcoes += fluxoCountVO.getQuantidadeAcoes();
				somatorioTempo += fluxoCountVO.getTempoRealizacao();
			}
			retorno.put("tempo", somatorioTempo / quantidadeObjetos);
			retorno.put("acao", somatorioAcoes / quantidadeObjetos);

		} else {
			retorno.put("tempo", 0.0);
			retorno.put("acao", 0.0);
		}
		return retorno;
	}

	public HashMap<String, Object> calculosFluxo(List<FluxoCountVO> fluxos,
			TipoConvidado tipoConvidado, List<FluxoVO> fluxosVO,
			boolean calcularMediaAcoes) {
		HashMap<String, Double> desvioPadraoEMedia = this
				.desvioPadraoEMedia(fluxos);
		HashMap<String, Object> retorno = new HashMap<String, Object>();
		Double mediaTempo = desvioPadraoEMedia.get("mediaTempo");
		Double mediaAcao = desvioPadraoEMedia.get("mediaAcao");
		Double desvioPadraoTempo = desvioPadraoEMedia.get("desvio_tempo");
		Double desvioPadraoAcao = desvioPadraoEMedia.get("desvio_acao");
		Long quantidadeUsuariosNAmediaTempo = 0l;
		Long quantidadeUsuariosNAmediaAcao = 0l;
		classificarUsuariosMediaTempo(mediaTempo, desvioPadraoTempo, fluxosVO,
				tipoConvidado);
		if (calcularMediaAcoes) {
			classificarUsuariosMediaAcoes(mediaAcao, desvioPadraoAcao,
					fluxosVO, tipoConvidado);
		}
		for (FluxoCountVO fluxoCountVO : fluxos) {
			Long quantidadeAcoes = fluxoCountVO.getQuantidadeAcoes();
			if (quantidadeAcoes >= (mediaAcao - desvioPadraoAcao)
					&& quantidadeAcoes <= (mediaAcao + desvioPadraoAcao)) {
				quantidadeUsuariosNAmediaAcao++;
			}
			Long tempoRealizacao = fluxoCountVO.getTempoRealizacao();
			if (tempoRealizacao >= (mediaTempo - desvioPadraoTempo)
					&& tempoRealizacao <= (mediaTempo + desvioPadraoTempo)) {
				quantidadeUsuariosNAmediaTempo++;
			}
		}
		retorno.put(
				"media_Acoes_" + tipoConvidado,
				((mediaAcao - desvioPadraoAcao) + " - " + (mediaAcao + desvioPadraoAcao)));
		retorno.put(
				"media_tempo_" + tipoConvidado,
				(((mediaTempo - desvioPadraoTempo)/1000) + " Seg - " + ((mediaTempo + desvioPadraoTempo)/1000))+" Seg");
		retorno.put("total_fluxos_" + tipoConvidado, fluxos.size());
		retorno.put("total_fluxos_media_tempo_" + tipoConvidado,
				quantidadeUsuariosNAmediaTempo);
		retorno.put("total_fluxos_media_acao_" + tipoConvidado,
				quantidadeUsuariosNAmediaTempo);

		return retorno;
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
	 * Classifica os usuarios alterando se as medias dos usuarios foram a cima
	 * ou a baixo dos outros
	 * 
	 * @param mediaAritimetica
	 * @param desvioPadrao
	 * @param fluxos
	 * @param tipoConvidado
	 */
	private void classificarUsuariosMediaTempo(double mediaAritimetica,
			double desvioPadrao, List<FluxoVO> fluxos,
			TipoConvidado tipoConvidado) {
		for (FluxoVO fluxoVO : fluxos) {
			if (fluxoVO.getTipoConvidado().equals(tipoConvidado)) {
				if (fluxoVO.getTempoRealizacao() < mediaAritimetica
						- desvioPadrao)
					fluxoVO.setMediaTempo(TipoMedia.ACIMA);
				else {
					if (fluxoVO.getTempoRealizacao() > mediaAritimetica
							+ desvioPadrao)
						fluxoVO.setMediaTempo(TipoMedia.ABAIXO);
					else
						fluxoVO.setMediaTempo(TipoMedia.MEDIA);
				}
			}
		}
	}

	/**
	 * Classifica os usuarios alterando se as medias de acoes dos usuarios foram
	 * a cima ou a baixo dos outros
	 * 
	 * @param mediaAritimetica
	 * @param desvioPadrao
	 * @param fluxos
	 * @param tipoConvidado
	 */
	private void classificarUsuariosMediaAcoes(double mediaAritimetica,
			double desvioPadrao, List<FluxoVO> fluxos,
			TipoConvidado tipoConvidado) {
		for (FluxoVO fluxoVO : fluxos) {
			if (fluxoVO.getTipoConvidado().equals(tipoConvidado)) {
				if (fluxoVO.getQuantidadeAcoes() < mediaAritimetica
						- desvioPadrao)
					fluxoVO.setMediaAcoes(TipoMedia.ACIMA);
				else {
					if (fluxoVO.getQuantidadeAcoes() > mediaAritimetica
							+ desvioPadrao)
						fluxoVO.setMediaAcoes(TipoMedia.ABAIXO);
					else
						fluxoVO.setMediaAcoes(TipoMedia.MEDIA);
				}
			}
		}
	}

	/**
	 * Gera a porcentagem de cada alternativa
	 * 
	 * @param respostasAlternativas
	 */
	public void gerarPorcentagem(
			List<RespostaAlternativaVO> respostasAlternativas) {
		if (!respostasAlternativas.isEmpty()) {
			double somatorio = 0;
			for (RespostaAlternativaVO respostaAlternativaVO : respostasAlternativas) {
				somatorio += respostaAlternativaVO.getQuantidadeRespostas();
			}
			for (RespostaAlternativaVO respostaAlternativaVO : respostasAlternativas) {
				double regra = respostaAlternativaVO.getQuantidadeRespostas() * 100;
				respostaAlternativaVO.setPorcentos(regra / somatorio);
			}
		}
	}

	/**
	 * Inseria quantidade de usuarios convidados em cada Teste
	 * 
	 * @param testeId
	 */
	public void quantidadeUsuariosConvidados(Long testeId) {
		List<ConvidadoCount> participantesTeste = testeRepository
				.getParticipantesTeste(testeId);
		List<TipoConvidado> naoContem = new ArrayList<TipoConvidado>();
		naoContem.addAll(Arrays.asList(TipoConvidado.values()));
		for (ConvidadoCount convidadoCount : participantesTeste) {
			result.include("convidados_" + convidadoCount.getTipoConvidado(),
					convidadoCount.getNumeroConvidados());
			naoContem.remove(convidadoCount.getTipoConvidado());
		}
		for (TipoConvidado tipoConvidado : naoContem) {
			result.include("convidados_" + tipoConvidado, 0);
		}
	}

	/**
	 * Realiza todos os calculos de media e desvio padrao de um fluxo
	 * 
	 * @param tarefaId
	 * @param fluxosVOs
	 * @param isCalcularMediaAcoes
	 *            true se for para calcular a media de ações em um determinado
	 *            fluxo
	 */
	public void calculoTempoEAcoesFluxo(Long tarefaId, List<FluxoVO> fluxosVOs,
			boolean isCalcularMediaAcoes) {
		List<TipoConvidado> tiposConvidados = new ArrayList<TipoConvidado>();
		tiposConvidados.addAll(Arrays.asList(TipoConvidado.values()));
		for (TipoConvidado tipoConvidado : tiposConvidados) {
			List<FluxoCountVO> quantidadeAcoes = tarefaRepository.quantidadeAcoesETempo(tarefaId, tipoConvidado);
			HashMap<String, Object> calculosFluxo = this.calculosFluxo(
					quantidadeAcoes, tipoConvidado, fluxosVOs,
					isCalcularMediaAcoes);
			Set<String> keySet = calculosFluxo.keySet();
			for (String key : keySet) {
				result.include(key, calculosFluxo.get(key));
			}
		}

	}
}
