(function($){
	chrome.extension.sendRequest({useskill: "getDomain"}, function(response) {
		var domainUseSkill;
		
		if(response && response.domain){
			domainUseSkill = response.domain;
		}else{
			
		}
		var correctDomain = false;
		
		//testar se tem conexão com o servidor
		$.ajax({
			url: domainUseSkill,
			cache: false,
			type: "GET",
			async: false,
			success: function(dados){
				correctDomain = true;
			},
			error: function(jqXHR, status, err){
				console.log(domainUseSkill);
				chrome.tabs.create({url: "options.html"});
				var notification = webkitNotifications.createHTMLNotification('html/notifications/404.html');
				notification.show();
			}
		});
	

		var urls = new Array();
		urls['isLogged'] = domainUseSkill+"/services/user/logado";
		urls['login'] = domainUseSkill+"/services/login";
		urls['convidados'] = domainUseSkill+"/services/testes/convidados";
		urls['logout'] = domainUseSkill+"/logout";

		/*Definição de tipos de chamadas ajax*/
		typeEnum = {
		    POST : "POST",
		    GET : "GET"
		}

		init();

		function init() { 
			var userLogged = isLogged();
			if(userLogged == "true" || userLogged == true){
				logged();
			}else{
				getPage("login");
			}
		}

		localStorage.setItem("x", 1);

		$(document).ready(function(){
			/*	EVENTS JQUERY DEFINITION	*/
			/*	LOGIN 	*/
			/*click no submit para efetuar login*/
			$('#loginForm input:submit').live({
				"click" : function(e){
					e.preventDefault();
					var objLogin = login();
					if(objLogin.retorno){
						logged();
					}else{
						$('#alerts').html(objLogin.erro).fadeIn(300);
					}
				}
			});

			/*	CONVIDADOS	*/
			$('.teste-info').live({
				"click" : function(e){
					e.preventDefault();
				}
			});

			$('#USlogout').live({
				"click" : function(e){
					e.preventDefault();
					chrome.extension.sendRequest({useskill: "testFinish"});
					var objJson = ajax(urls.logout, typeEnum.GET);
					console.log(objJson);
					getPage("login");
				}
			})

			$('.teste-aceitar').live({
				"click" : function(e){
					e.preventDefault();
					var id = $(this).parentsUntil('tr').parent().attr('data-id');

					var objJson = ajax(domainUseSkill+"/teste/participar/"+id+"/aceitar", "GET");
					chrome.extension.sendRequest({useskill: "nextElement", lista: objJson, atual: 0});
				}
			});

			$('.adiarteste').live({
				"click" : function(e){
					e.preventDefault();
					chrome.extension.sendRequest({useskill: "testFinish"});
					logged();
				}
			});
		});

		/*	FUNCTIONS DEFINITION	*/

		/*retorna se o usuário está logado*/
		function isLogged(){
			var objJson = ajax(urls.isLogged, typeEnum.GET);
			return objJson.logado;
		}

		/*realizar o login na ferramenta*/
		function login(){
			var objJson = ajax(urls.login, typeEnum.POST, $('#loginForm').serialize());
			return parseJSON(objJson.string);
		}

		/*função para usuário logado*/
		function logged(){
			getPage("convidados", 'getTestesConvidados');
		}

		function getStorage(callback){			
			chrome.extension.sendRequest({useskill: "getStorage"}, function(response) {
				if (callback && typeof(callback) === "function") {
	    			callback.call(this, response);
				}
			});
		}

		/*receber os testes convidados do usuário*/
		function getTestesConvidados(){
			getStorage(function(dados){
				//se estiver gravando avisa que precisa encerrar
				console.log(dados)
				if(!dados.dados.gravando){
					var objJson = ajax(urls.convidados, typeEnum.GET);
					objJson = parseJSON(objJson.string);
					objJson.testesConvidados = parseJSON(objJson.testesConvidados);
					var arrayTestes = objJson.testesConvidados;

					var htmlReturn = "";
					for(var t in arrayTestes){
						htmlReturn+='<tr data-id="'+arrayTestes[t].id+'"><td>';
						if(arrayTestes[t].nome!=undefined){
							htmlReturn+=arrayTestes[t].nome;
						}else{
							htmlReturn+="- Sem Titulo -";
						}
						htmlReturn+='</td><td class="centertd"><a class="btn teste-info" href="#" rel="tooltip" data-original-title="Tipo: '+arrayTestes[t].tipoConvidado+' <br/>Convidou: '+arrayTestes[t].usuarioCriado+'"><span class="icon-question-sign"></span></a></td><td class="centertd"><div class="btn-group"><a class="btn btn-primary teste-aceitar" href="#" title="Aceitar"><span class="icon-ok icon-white"></span></a><a title="Recusar" class="btn btn-danger teste-recusar" href="#"><span class="icon-remove icon-white"></span></a></div></td></tr>';
					}
					$('tbody').html(htmlReturn);
					$('.teste-info').tooltip();	
					$(".scroll").mCustomScrollbar();
				}else{
					var htmlReturn = '<div style="text-align: center;">Para iniciar outro teste, finalize o teste que est&aacute; em execu&ccedil;&atilde;o! <br/> <a class="btn btn-danger adiarteste" href="#"><b class="icon-remove icon-white"></b> Adiar Teste</a></div>';
					$('#idConvidados').html(htmlReturn);
				}
			})
		}

		/*método genérico para realizar ajax*/
		function ajax(caminho, tipo, dados){
			var retorno;
			$.ajax({
				url: caminho,
				cache: false,
				type: tipo,
				async: false,
				data: dados,
				success: function(dados){
					retorno = dados;
				},
				error: function(jqXHR, status, err){
					console.log(jqXHR);
				}
			});
			return retorno;
		}

		/*função para carregar um novo html no conteúdo do popup*/
		function getPage(pagina, callBack){
			var vel = 400;
			var $content = $('#content');

			$content.fadeOut(vel,function(){
				$('#loader').fadeIn(vel, function(){
					$content.load('html/'+pagina+'.html', function(){
						//fazer com que os links para a useskill nao quebrem
						$.each($('.btn-useskill'), function(){
							console.log($(this))
							$(this).attr('href', domainUseSkill+$(this).attr('href'));
						});
								
						$('#loader').fadeOut(vel, function(){
							$content.fadeIn(vel);
							
							switch(callBack){
								case 'getTestesConvidados':
									getTestesConvidados();
									break;
								default:
									break;
							}
						});
					});
				});
			});
		}
		/*converter json to object*/
		function parseJSON(data) {
    		return window.JSON && window.JSON.parse ? window.JSON.parse( data ) : (new Function("return " + data))(); 
		}
	});
})(jQuery);

//var notification = webkitNotifications.createHTMLNotification('html/notification.html');
//notification.show();