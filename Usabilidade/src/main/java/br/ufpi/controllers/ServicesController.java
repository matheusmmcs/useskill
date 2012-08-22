package br.ufpi.controllers;

import java.util.HashMap;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Usuario;
import br.ufpi.models.vo.TesteVO;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.Criptografa;
import br.ufpi.util.Paginacao;

@Resource
public class ServicesController {
	private final TesteRepository testeRepository;
	private final Result result;
	private final UsuarioRepository usuarioRepository;
	private final Validator validator;
	private final UsuarioLogado usuarioLogado;

	public ServicesController(TesteRepository testeRepository, Result result,
			UsuarioRepository usuarioRepository, Validator validator,
			UsuarioLogado usuarioLogado) {
		super();
		this.testeRepository = testeRepository;
		this.result = result;
		this.usuarioRepository = usuarioRepository;
		this.validator = validator;
		this.usuarioLogado = usuarioLogado;
	}

	@Get
	public void login(final String email, final String senha) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		validator.checking(new Validations() {

			{
				that(email != null && !email.isEmpty(),
						"campo.email.obrigatorio", "campo.obrigatorio",
						i18n("email"));
				that(senha != null && !senha.isEmpty(),
						"campo.senha.obrigatorio", "campo.obrigatorio",
						i18n("senha"));
			}
		});
		if (validator.hasErrors()) {

			validator.onErrorUse(Results.json())
					.from(toStringMessage(validator.getErrors())).serialize();
		}
		String senhaCriptografada = Criptografa.criptografar(senha);
		Usuario usuario = usuarioRepository.logar(email, senhaCriptografada);
		if (usuario != null) {
			if (usuario.isEmailConfirmado()) {
				usuarioLogado.setUsuario(usuario);
				hashMap.put("return", true);
				hashMap.put("name", usuario.getNome());
				result.use(Results.json()).from(hashMap).serialize();
			} else {
				hashMap.put("return", false);
				hashMap.put("usuarioNaoConfirmado", "Usuario não confirmado");
				result.use(Results.json()).from(hashMap).serialize();
			}
		} else {
			validator.checking(new Validations() {

				{
					that(false, "email.senha.invalido", "email.senha.invalido");
				}
			});
			validator.onErrorUse(Results.json())
					.from(toStringMessage(validator.getErrors())).serialize();
		}
	}

	@Logado
	@Get({ "/services/testes/convidados",
			"/services/testes/convidados/pag/{numeroPagina}" })
	public void listarTestesConvidados(int numeroPagina) {
		if (numeroPagina == 0) {
			numeroPagina = 1;
		}
		HashMap<String, Object> json = new HashMap<String, Object>();
		Paginacao<TesteVO> paginacao = testeRepository.findTestesConvidados(
				numeroPagina, usuarioLogado.getUsuario().getId(),
				Paginacao.OBJETOS_POR_PAGINA);
		json.put("testesConvidados", paginacao.getListObjects());
		Long qttObjetosNoBanco = paginacao.getCount();
		paginacao.geraPaginacaoJson(numeroPagina, Paginacao.OBJETOS_POR_PAGINA,
				qttObjetosNoBanco, json, result);
	}

	/**
	 * Obtem o Mapa de erros gerados pela aplicação. Pra ser usado quando
	 * acontecer algum erro na aplicação.
	 * 
	 * @param messages
	 *            Mensagens capturadas pelos erros gerados pela validação do
	 *            Vraptor
	 * @return O mapa contendo todos os erros gerados na aplicação
	 */
	private HashMap<String, Object> toStringMessage(List<Message> messages) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		for (Message message : messages) {
			hashMap.put("ERROR , " + message.getCategory(),
					message.getMessage());
			hashMap.put("retorno", false);
		}
		return hashMap;
	}

	@Get("/services/user/logado")
	public void isUserLogado() {
		if (usuarioLogado == null) {
			result.use(Results.json()).from(Boolean.FALSE.toString(), "logado")
					.serialize();
		} else {
			result.use(Results.json()).from(Boolean.TRUE.toString(), "logado")
					.serialize();
		}
	}
}
