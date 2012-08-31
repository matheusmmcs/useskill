(function($){
	/*Definição de urls para chamadas ajax*/
	var domainUseSkill = "http://localhost:8080/Usabilidade";
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

	window.onload = function() { 
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

	/*receber os testes convidados do usuário*/
	function getTestesConvidados(){
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
			htmlReturn+='</td><td class="centertd"><a class="btn btn-primary teste-info" href="#" rel="tooltip" data-original-title="Tipo: '+arrayTestes[t].tipoConvidado+' <br/>Convidou: '+arrayTestes[t].usuarioCriado+'"><span class="icon-question-sign icon-white"></span></a></td><td class="centertd"><a class="btn btn-success teste-aceitar" href="#" title="Aceitar"><span class="icon-ok icon-white"></span></a><a title="Recusar" class="btn btn-danger teste-recusar" href="#"><span class="icon-remove icon-white"></span></a></td></tr>';
		}
		$('tbody').html(htmlReturn);
		$('.teste-info').tooltip();

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
})(jQuery);



/*
function click(e) {
	chrome.tabs.executeScript(null,
		{code:"document.body.style.backgroundColor='" + e.target.id + "'"});
		window.close();
}

document.addEventListener('DOMContentLoaded', function () {
	var divs = document.querySelectorAll('div');
	for (var i = 0; i < divs.length; i++) {
		divs[i].addEventListener('click', click);
	}
});
*/

//var notification = webkitNotifications.createHTMLNotification('html/notification.html');
//notification.show();