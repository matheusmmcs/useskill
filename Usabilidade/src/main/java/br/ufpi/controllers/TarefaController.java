package br.ufpi.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.analise.Estatistica;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.ObjetoSalvo;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Action;
import br.ufpi.models.Comentario;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.enums.SituacaoDeUsoEnum;
import br.ufpi.models.roteiro.ValorRoteiro;
import br.ufpi.models.roteiro.VariavelRoteiro;
import br.ufpi.models.vo.ConvidadoCount;
import br.ufpi.models.vo.FluxoVO;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.ComentarioRepository;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.ValorRoteiroRepository;
import br.ufpi.repositories.VariavelRoteiroRepository;
import br.ufpi.util.EnumObjetoSalvo;
import br.ufpi.util.GsonElements;
import br.ufpi.util.Paginacao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Resource
public class TarefaController extends BaseController {

	private final TarefaRepository tarefaRepository;
	private final ComentarioRepository comentarioRepository;
	private final TesteRepository testeRepository;
	private final FluxoRepository fluxoRepository;
	private final TesteSessionPlugin testeSessionPlugin;
	private final Estatistica estatistica;
	private final VariavelRoteiroRepository variavelRoteiroRepository;
	private final ValorRoteiroRepository valorRoteiroRepository;

	public TarefaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			TarefaRepository tarefaRepository, TesteRepository testeRepository,
			FluxoRepository fluxoIdealRepository,
			TesteSessionPlugin testeSessionPlugin, Estatistica estatistica,
			ComentarioRepository comentarioRepository,
			VariavelRoteiroRepository variavelRoteiroRepository,
			ValorRoteiroRepository valorRoteiroRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.tarefaRepository = tarefaRepository;
		this.testeRepository = testeRepository;
		this.fluxoRepository = fluxoIdealRepository;
		this.testeSessionPlugin = testeSessionPlugin;
		this.estatistica = estatistica;
		this.comentarioRepository = comentarioRepository;
		this.variavelRoteiroRepository = variavelRoteiroRepository;
		this.valorRoteiroRepository = valorRoteiroRepository;
	}

	/**
	 * Usado para criar Tarefas analisa se o teste id passado não é igual a
	 * null, caso seja redireciona para a pagina de login
	 * 
	 * @param testeId
	 * @param tarefa
	 * @return
	 */
	@Logado
	@Get("teste/{testeId}/editar/passo2/criar/tarefa")
	public Tarefa criarTarefa(Long testeId, Tarefa tarefa) {
		this.testeNaoRealizadoPertenceUsuarioLogado(testeId);
		if (tarefa == null) {
			return new Tarefa();
		}
		return tarefa;
	}

	@Logado
	@Post("teste/salvar/tarefa")
	public void salvarTarefa(Tarefa tarefa, Long idTeste, Collection<String> variaveis) {
		validateComponente.validarString(tarefa.getNome(), "tarefa.titulo");
		validateComponente.validarString(tarefa.getRoteiro(), "tarefa.roteito");
		validateComponente.validarString(tarefa.getUrlInicial(),
				"tarefa.urlInicial");
		validator.onErrorRedirectTo(this).criarTarefa(idTeste, tarefa);
		
		
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);
		Teste teste = testeView.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.create(tarefa);
		
		if(variaveis != null){
			for(String var : variaveis){
				
				String[] arrayVariaveis = var.split(":");
				if(arrayVariaveis != null && arrayVariaveis.length == 2){
					String variavel = arrayVariaveis[0];
					String[] valores = arrayVariaveis[1].split(";");
					if(variavel != null && !variavel.isEmpty() && valores != null & valores.length > 0){
						
						//instanciando variavel a ser iterada
						VariavelRoteiro variavelDoRoteiro = new VariavelRoteiro();
						variavelDoRoteiro.setTarefa(tarefa);
						variavelDoRoteiro.setVariavel(variavel);
						variavelRoteiroRepository.create(variavelDoRoteiro);
						
						for(String val : valores){
							if(!val.isEmpty()){
								
								//cadastrando valores para a variavel definida
								ValorRoteiro valorDaVariavel = new ValorRoteiro();
								valorDaVariavel.setValor(val);
								valorDaVariavel.setVariavelRoteiro(variavelDoRoteiro);
								valorDaVariavel.setSituacaoDeUso(SituacaoDeUsoEnum.LIVRE);
								
								valorRoteiroRepository.create(valorDaVariavel);
								System.out.println("Variavel: "+variavel+" : "+val);
							}
						}
						
					}
					
				}else{
					System.out.println("Variavel invalida: "+var);
				}
				
			}
		}
		
		Teste testeUpdate = GsonElements.addTarefa(tarefa.getId(), testeView.getTeste());
		testeRepository.update(testeUpdate);
		result.redirectTo(TesteController.class).passo2(testeView.getTeste().getId());
	}

	/**
	 * Utilizado para alterar Tarefa
	 * 
	 * @param idTeste
	 *            Procupar por teste com identificador
	 * @param tarefa
	 *            Tarefa a ser alterada
	 * 
	 * @param isErro
	 *            True para verificar se esta vindo de uma tentativa de alterar
	 *            e ocorreu erro, neste caso não necessita pegar do banco e
	 *            tambem ele tem que retorna a pergunta do modo que foi
	 *            alterada.
	 * @return
	 */
	@Logado
	@Get()
	@Path(value = "teste/{idTeste}/editar/passo2/editar/{tarefa.id}/tarefa")
	public Tarefa editarTarefa(Long idTeste, Tarefa tarefa, boolean isErro) {
		this.instanceIdTesteView(idTeste);
		if (isErro) {
			return tarefa;
		}
		Tarefa tarefaPertenceTeste = this.tarefaPertenceTeste(idTeste, tarefa.getId());
		testeView.setTeste(testeRepository.find(idTeste));
		return tarefaPertenceTeste;
	}

	/**
	 * Pagina para editar Tarefa, se idTeste e TarefaId foor igual a null
	 * redireciona para página 404
	 * 
	 * @param tarefa
	 * @param idTeste
	 *            Identificador ao qual a tarefa pertence
	 */
	@Logado
	@Post("teste/tarefa/atualizar")
	public void updateTarefa(Tarefa tarefa, Long idTeste, Collection<String> variaveis) {
		validateComponente.validarString(tarefa.getNome(), "tarefa.titulo");
		validateComponente.validarString(tarefa.getRoteiro(), "tarefa.roteito");
		validateComponente.validarString(tarefa.getUrlInicial(), "tarefa.urlInicial");
		validator.onErrorRedirectTo(this).editarTarefa(idTeste, tarefa, true);
		this.tarefaPertenceTesteNaoRealizado(tarefa.getId(), idTeste);
		
		Tarefa tarefaUpdate = tarefaRepository.find(tarefa.getId());
		tarefaUpdate.setRoteiro(tarefa.getRoteiro());
		tarefaUpdate.setNome(tarefa.getNome());
		
		if (!tarefaUpdate.getUrlInicial().equals(tarefa.getUrlInicial().trim())) {
			tarefaUpdate.setUrlInicial(tarefa.getUrlInicial());
		}
		
		if(variaveis != null){
			for(String var : variaveis){
				
				String[] arrayVariaveis = var.split(":");
				if(arrayVariaveis != null && arrayVariaveis.length == 2){
					String variavel = arrayVariaveis[0];
					String[] valores = arrayVariaveis[1].split(";");
					if(variavel != null && !variavel.isEmpty() && valores != null & valores.length > 0){
						
						//buscar a variavel que possua esse nome nessa tarefa
						VariavelRoteiro variavelDoRoteiro;
						variavelDoRoteiro = variavelRoteiroRepository.findVariavelDaTarefaComNomeIgual(tarefa.getId(), variavel);
						
						//ja existe
						if(variavelDoRoteiro != null){
							variavelRoteiroRepository.destroy(variavelDoRoteiro);
						}
						
						variavelDoRoteiro = new VariavelRoteiro();
						variavelDoRoteiro.setTarefa(tarefa);
						variavelDoRoteiro.setVariavel(variavel);
						variavelRoteiroRepository.create(variavelDoRoteiro);
						
						for(String val : valores){
							if(!val.isEmpty()){
								//cadastrando valores para a variavel definida
								ValorRoteiro valorDaVariavel = new ValorRoteiro();
								valorDaVariavel.setValor(val);
								valorDaVariavel.setVariavelRoteiro(variavelDoRoteiro);
								valorDaVariavel.setSituacaoDeUso(SituacaoDeUsoEnum.LIVRE);
								
								valorRoteiroRepository.create(valorDaVariavel);
								System.out.println("Variavel: "+variavel+" : "+val);
							}
						}
						
					}
					
				}else{
					System.out.println("Variavel invalida: "+var);
				}
				
			}
		}
		
		tarefaRepository.update(tarefaUpdate);
		result.redirectTo(TesteController.class).passo2(idTeste);

	}

	/**
	 * Remove Tarefa caso tarefa não possa ser deletada vai pra 404.
	 * 
	 * @param idTarefa
	 *            identificador da Tarefa a ser deletada
	 * @param idTeste
	 */
	@Logado
	@Get("teste/{idTeste}/tarefa/{idTarefa}/apagar")
	public void removed(Long idTarefa, Long idTeste) {
		Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa, idTeste);
		testeView.setTeste(testeRepository.find(idTeste));
		Teste teste = GsonElements.removerTarefa(tarefa.getId(),
				testeView.getTeste());
		tarefaRepository.destroy(tarefa);
		testeRepository.update(teste);
		result.redirectTo(TesteController.class).passo2(idTeste);
	}

	@Logado
	@Post("tarefa/save/fluxo")
	public void saveFluxo(String dados, Long tarefaId, Boolean isFinished,
			String comentario) {
		Tarefa tarefa = tarefaRepository.find(tarefaId);
		
		//altera os valores para concluido
		for(VariavelRoteiro var : tarefa.getVariaveisRoteiro()){
			for(ValorRoteiro val : var.getValores()){
				if(val.getSituacaoDeUso().equals(SituacaoDeUsoEnum.EM_UTILIZACAO)){
					System.out.println("Em uso: "+val.getValor());
					val.setSituacaoDeUso(SituacaoDeUsoEnum.CONCLUIDO);
					valorRoteiroRepository.update(val);
				}
			}
		}
		
		Fluxo fluxo = new Fluxo();
		fluxo.setUsuario(usuarioLogado.getUsuario());
		fluxo.setTarefa(tarefa);
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Action>>() {
		}.getType();
		Collection<Action> ints2 = gson.fromJson(dados, collectionType);
		if (ints2 != null) {
			List<Action> acoes = new ArrayList<Action>(ints2);
			fluxo.setDataRealizacao(new Date(System.currentTimeMillis()));
			int ultimoFluxo = acoes.size() - 1;
			if (ultimoFluxo < 0)
				ultimoFluxo = 0;
			fluxo.setTempoRealizacao(acoes.get(ultimoFluxo).getsTime()
					- acoes.get(0).getsTime());
			diferencaTempo(acoes);
			fluxo.setAcoes(acoes);
		}
		fluxo.setTipoConvidado(testeSessionPlugin.getTipoConvidado());
		if (isFinished != null) {
			fluxo.setFinished(isFinished);
		}
		if (!fluxo.isFinished()) {
			fluxo.setComentario(comentario);
		}
		fluxoRepository.create(fluxo);
		System.out.println("Tarefa id" + tarefaId);
		System.out.println("Identificador do fluxo:" + fluxo.getId());
		testeSessionPlugin.addObjetosSalvos(new ObjetoSalvo(fluxo.getId(),
				EnumObjetoSalvo.FLUXO));
		result.use(Results.json()).from("true").serialize();
	}

	/**
	 * Calcula a diferença de cada ação em relacao ao tempo da primeira ação
	 * realizada
	 * 
	 * @param acoes
	 *            Lista de ações que serão comparadas
	 */
	public static void diferencaTempo(List<Action> acoes) {
		if (!acoes.isEmpty()) {
			Long anterior = 0l;
			for (int i = 0; i < acoes.size(); i++) {
				if (i != 0) {
					Long tempo = acoes.get(i).getsTime() - anterior;
					acoes.get(i).setsTime(tempo);
				} else {
					anterior = acoes.get(0).getsTime();
					acoes.get(0).setsTime(0l);
				}
			}
		}
	}

	/**
	 * Analisa se Tarefa pertence ao id do Teste passado.Caso ela pertence ao
	 * usuario Logado Caso não Obedeça as requisições redireciona para a página
	 * 404
	 * 
	 * @param idTeste
	 * @param idTarefa
	 * @return Tarefa requisitada pelo id. Caso não encontre não retorna nada,
	 *         pois pagina é redirecionada para a página 404
	 */
	private Tarefa tarefaPertenceTeste(Long idTeste, Long idTarefa) {
		validateComponente.validarId(idTeste);
		validateComponente.validarId(idTarefa);
		Tarefa tarefa = tarefaRepository.pertenceTeste(idTarefa, idTeste,
				usuarioLogado.getUsuario().getId());
		validateComponente.validarObjeto(tarefa);
		return tarefa;
	}

	/**
	 * Analisa se um determinado Teste não liberado pertence ao usuario
	 * 
	 * @param idTeste
	 *            Identificador do Teste a ser procurado
	 */
	private void testeNaoRealizadoPertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		Teste teste = testeRepository.getTestCriadoNaoLiberado(usuarioLogado
				.getUsuario().getId(), idTeste);
		validateComponente.validarObjeto(teste);
		testeView.setTeste(teste);

	}

	/**
	 * Analisa se uma determinada tarefa pertence a um teste ainda não
	 * Realizado.
	 * 
	 * @param tarefaId
	 * @return
	 */
	private Tarefa tarefaPertenceTesteNaoRealizado(Long tarefaId, Long idTeste) {
		validateComponente.validarId(idTeste);
		validateComponente.validarId(tarefaId);
		Tarefa tarefaRetorno = tarefaRepository.perteceTesteNaoRealizado(
				tarefaId, idTeste, usuarioLogado.getUsuario().getId());
		validateComponente.validarObjeto(tarefaRetorno);
		return tarefaRetorno;
	}

	@Post("/tarefa/roteiro")
	@Logado
	public void getRoteiro(Long idTarefa) {
		validateComponente.validarId(idTarefa);
		String roteiro = tarefaRepository.getRoteiro(idTarefa, testeSessionPlugin.getIdTeste(), usuarioLogado.getUsuario());
		validateComponente.validarObjeto(roteiro);
		result.use(Results.json()).from(roteiro).serialize();
	}
	
	@Get("/teste/{idTeste}/tarefa/{idTarefa}/roteiro")
	@Logado
	public void getRoteiro2(Long idTeste, Long idTarefa) {
		validateComponente.validarId(idTarefa);
		String roteiro = tarefaRepository.getRoteiro(idTarefa, idTeste, usuarioLogado.getUsuario());
		validateComponente.validarObjeto(roteiro);
		result.use(Results.json()).from(roteiro).serialize();
	}

	@Post("/tarefa/titulo")
	@Logado
	public void getTarefaTitulo(Long idTarefa) {
		validateComponente.validarId(idTarefa);
		String roteiro = tarefaRepository.getTitulo(idTarefa,
				testeSessionPlugin.getIdTeste());
		validateComponente.validarObjeto(roteiro);
		result.use(Results.json()).from(roteiro).serialize();
	}

	@Logado
	@Get("/tarefa/{idTarefa}/json")
	public void getTarefa(Long idTarefa) {
		validateComponente.validarId(idTarefa);
		validateComponente.validarId(testeSessionPlugin.getIdTeste());
		TarefaVO tarefaVO = tarefaRepository.getTarefaVO(idTarefa,
				testeSessionPlugin.getIdTeste());
		System.out.println(tarefaVO);
		validateComponente.validarObjeto(tarefaVO);
		result.use(Results.json()).from(tarefaVO).serialize();
	}

	/**
	 * Passos do método
	 * 
	 * 1º passar idTarefa,idTeste,idPessoa(Validar itens passados) e tipo de
	 * fluxo que ele realizou
	 * 
	 * 2º verificar se o usario é criador do teste
	 * */
	@Logado
	@Get({
			"teste/{testeId}/tarefa/{tarefaId}/usuario/{usuarioId}/analise",
			"teste/{testeId}/tarefa/{tarefaId}/usuario/{usuarioId}/analise/pag/{numeroPagina}" })
	public void exibirFluxosUsuario(Long testeId, Long tarefaId, Long usuarioId) {
		validateComponente.validarId(testeId);
		validateComponente.validarId(testeId);
		validateComponente.validarId(usuarioId);
		Long usuarioCriadorId = usuarioLogado.getUsuario().getId();
		tarefaPertenceAoUsuarioLogado(usuarioCriadorId, tarefaId, testeId);
		List<FluxoVO> fluxos = tarefaRepository.getFluxoUsuario(tarefaId,
				usuarioId);
		estatistica.calculoTempoEAcoesFluxo(tarefaId, fluxos, true);
		if (!fluxos.isEmpty()) {
			FluxoVO fluxoVO = fluxos.get(0);
			String nomeUsuario = tarefaRepository.getNameUsuario(fluxoVO
					.getFluxoId());
			result.include("nomeUsuario", nomeUsuario);

		}
		validateComponente.validarObjeto(fluxos);
		result.include("tarefa", tarefaRepository.find(tarefaId));
		result.include("fluxos", fluxos);
		result.include("testeId", testeId);
		result.include("usuarioId", usuarioId);
	}

	/**
	 * Passos do método
	 * 
	 * 1º passar idTarefa,idTeste,idPessoa(Validar itens passados) e tipo de
	 * fluxo que ele realizou
	 * 
	 * 2º verificar se o usario é criador do teste
	 * */
	@Logado
	@Get({ "teste/{testeId}/tarefa/{tarefaId}/usuario/{usuarioId}/fluxo/{fluxoId}/analise" })
	public void exibirActions(Long testeId, Long tarefaId, Long usuarioId,
			Long fluxoId) {
		validateComponente.validarId(testeId);
		validateComponente.validarId(tarefaId);
		validateComponente.validarId(usuarioId);
		validateComponente.validarId(fluxoId);
		Long usuarioCriadorId = usuarioLogado.getUsuario().getId();
		Fluxo fluxo = tarefaRepository.getFluxo(testeId, tarefaId, usuarioId,
				usuarioCriadorId, fluxoId);

		result.include("acoes", fluxo.getAcoes());
		result.include("nomeTarefa", fluxo.getTarefa().getNome());
		result.include("nomeUsuario", fluxo.getUsuario().getNome());
		result.include("nomeTeste", fluxo.getTarefa().getTeste().getTitulo());
		result.include("tarefaId", tarefaId);
		result.include("tarefaRoteiro", fluxo.getTarefa().getRoteiro());
		result.include("testeId", testeId);
		result.include("usuarioId", usuarioId);
		result.include("fluxoId", fluxoId);
	}

	@Logado
	@Get({ "teste/{testeId}/tarefa/{tarefaId}/analise",
			"teste/{testeId}/tarefa/{tarefaId}/analise/pag/{numeroPagina}" })
	public void analise(Long testeId, Long tarefaId, int numeroPagina) {
		validateComponente.validarId(tarefaId);
		validateComponente.validarId(testeId);
		Long usuarioDonoTeste = usuarioLogado.getUsuario().getId();
		tarefaPertenceAoUsuarioLogado(usuarioDonoTeste, tarefaId, testeId);
		if (numeroPagina <= 0) {
			numeroPagina = 1;
		}
		Paginacao<FluxoVO> paginacao = tarefaRepository.getFluxos(tarefaId,
				testeId, usuarioDonoTeste, Paginacao.OBJETOS_POR_PAGINA,
				numeroPagina);
		paginacao.geraPaginacao("fluxos", numeroPagina, result);
		result.include("tarefaId", tarefaId);
		result.include("testeId", testeId);
		result.include("tarefa", tarefaRepository.find(tarefaId));
		estatistica.calculoTempoEAcoesFluxo(tarefaId,
				paginacao.getListObjects(), false);
		estatistica.quantidadeUsuariosConvidados(testeId);
		quantidadeUsuariosQueRealizaramOTeste(testeId);
		List<Long> quantidadeAcoesETempoPorTipoAcao = tarefaRepository.quantidadeAcoesETempoPorTipoAcao(tarefaId, TipoConvidado.USER, "click");
		double mediaAritimetica = estatistica.mediaAritimetica(quantidadeAcoesETempoPorTipoAcao);
		System.out.println(mediaAritimetica);
	}

	@SuppressWarnings("unused")
	@Logado
	@Get("teste/{testeId}/tarefa/{tarefaId}/analise2")
	/**
	 * 
	 * @param testeId
	 * @param tarefaId
	 */
	public void analise(Long testeId, Long tarefaId) {
		validateComponente.validarId(tarefaId);
		validateComponente.validarId(testeId);
		Long usuarioDonoTeste = usuarioLogado.getUsuario().getId();
		tarefaPertenceAoUsuarioLogado(usuarioDonoTeste, tarefaId, testeId);
		/**
		 * quantidade de fluxos que foram concluidos pelos usuários
		 */
		Map<TipoConvidado, Long> quantidadeFluxosConcluidos = tarefaRepository
				.quantidadeFluxos(tarefaId, true);
		/**
		 * quantidade de fluxos que não foram concluidos
		 */
		Map<TipoConvidado, Long> quantidadeFluxosNaoConcluidos = tarefaRepository
				.quantidadeFluxos(tarefaId, false);
		//Adicionar a media de tempo dos usuários que conseguiram realizar as tarefas
		//
	}
	


	@Logado
	@Post({ "tarefa/enviarcomentario" })
	public void salvarComentario(String texto, Long idTarefa,
			boolean qualificacao) {
		Comentario comentario = new Comentario();
		comentario.setTexto(texto);
		// TODo RefazertarefaPertenceTeste(testeSessionPlugin.getIdTeste(),
		// idTarefa);
		Tarefa tarefa = tarefaRepository.find(idTarefa);
		comentario.setUsuario(super.usuarioLogado.getUsuario());
		comentario.setTarefa(tarefa);
		comentario.setGood(qualificacao);
		comentarioRepository.create(comentario);
		result.use(Results.json()).from(true).serialize();
	}

	private void quantidadeUsuariosQueRealizaramOTeste(Long testeId) {
		List<ConvidadoCount> participantesTeste = testeRepository
				.getRealizaramTeste(testeId);
		List<TipoConvidado> naoContem = new ArrayList<TipoConvidado>();
		naoContem.addAll(Arrays.asList(TipoConvidado.values()));
		for (ConvidadoCount convidadoCount : participantesTeste) {
			result.include(
					"convidados_realizaram_"
							+ convidadoCount.getTipoConvidado(),
					convidadoCount.getNumeroConvidados());
			naoContem.remove(convidadoCount.getTipoConvidado());
		}
		for (TipoConvidado tipoConvidado : naoContem) {
			result.include("convidados_realizaram_" + tipoConvidado, 0);
		}
	}

	private void tarefaPertenceAoUsuarioLogado(Long usuarioDonoTeste,
			Long tarefaId, Long testeId) {
		Tarefa pertenceTeste = tarefaRepository.pertenceTeste(tarefaId,
				testeId, usuarioDonoTeste);
		validateComponente.validarObjeto(pertenceTeste);
	}
}
