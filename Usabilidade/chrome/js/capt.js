/*
Melhorias:
Capturar o tamanho da janela para poder comparar o posicionamento dos itens (posX e posY);
Capturar eventos de rolagem de barra -> content = posiçao no eixo y;
Capturar carregamento de páginas -> content = Título da página;
	Podemos pensar em modificações para agregar grandes mudanças assíncronas em app ricas;
	Capturar eventos referentes a retorno JSON ou alteração no DOM (inserção/remoção de nós);
*/

(function($){
	$(document).ready(function(){
		
		//compara a ultima acao com a realizada, evitando duplicacao de acoes
		ultimaAcao = null;

		var actionCapt = {
  			CLICK : "click", 
  			FOCUSOUT: "focusout", 
  			SUBMIT : "submit",
  			MOUSEOVER: "mouseover",

  			ROTEIRO : "roteiro",
  			CONCLUIR : "concluir",
  			COMENTARIO : "comentario",
  			PULAR : "pular"
		};

		function Action(action, time, url, content, tag, tagIndex, posX, posY, viewportX, viewportY, useragent) {
			this.sActionType = action;
			this.sTime = time;
			this.sUrl = url;
			this.sContent = String(content).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
			this.sContent = (action == actionCapt.CONCLUIR) ? this.sContent : this.sContent.substr(0, 200);
			this.sTag = tag;
			this.sTagIndex = tagIndex;
			this.sPosX = posX;
			this.sPosY = posY;
			this.sViewportX = viewportX;
			this.sViewportY = viewportY;
			this.sUserAgent = useragent;
		}

		console.log("capt -> localStorage");

		printAcoes();

		var lastMouseMove = 0, lastMouseX, lastMouseY, mouseOffSet = 5;
		$(document).on({
			click : function(e) {
				insertNewAcao(e, actionCapt.CLICK);
			}, focusout : function(e) {
				insertNewAcao(e, actionCapt.FOCUSOUT);
			}, submit : function(e) {
				insertNewAcao(e, actionCapt.SUBMIT);
			}, mousemove : function(e) {
				if(e.timeStamp - lastMouseMove >= 750){
					if(e.pageX <= lastMouseX+mouseOffSet &&
					   e.pageX >= lastMouseX-mouseOffSet &&
					   e.pageY <= lastMouseY+mouseOffSet &&
					   e.pageY >= lastMouseY-mouseOffSet){
					   	console.log(e)
						insertNewAcao(e, actionCapt.MOUSEOVER);
					}
				}
				lastMouseMove = e.timeStamp;
				lastMouseX = e.pageX;
				lastMouseY = e.pageY;
			}
		});

		

		/*	FUNÇÕES EXTRAS	*/
		function insertNewAcao(e, actionType){
			var target = e.target;
			var tagName = target.tagName;
			var $target = $(target);
			var action = filterAction($target, actionType);
			if(action){
				var conteudo = getContent($target, action);
				//filtros de captura de dados
				if(
					!(action == actionCapt.FOCUSOUT && conteudo == "")//focusout em campo vazio, não preencheu nada
				){
					var acao = new Action(action, new Date().getTime(), getUrl(), conteudo, target.tagName, $(tagName).index(target), e.pageX, e.pageY, $(window).width(), $(window).height(), navigator.userAgent);
					//verificar se a acao eh semelhante com a acao passada
					console.log(acao)

					//TODO: se for concluir, verificar se já há outro concluir
					
					if(ultimaAcao){
						if(ultimaAcao.sTime && acao.sTime && acao.sTime - ultimaAcao.sTime > 10){
							ultimaAcao = acao;
							addAcao(acao);
						}else{
							ultimaAcao = acao;
							console.log("Evitou repetição:");
							console.log(ultimaAcao);
						}
					}else{
						//primeira ação
						ultimaAcao = acao;
						addAcao(acao);
					}
					
				}
			}
		}
		/**
		Receber o conteudo da ação de acordo com o elemento e a ação
		*/
		function getContent($target, action){
			var val = null;
			if(action==actionCapt.COMENTARIO){
				val = $('#UScomentarioTexto').val();
			}else if(action==actionCapt.CLICK){
				val = $target.html();
			}else if(action==actionCapt.FOCUSOUT){
				val = $target.val();
			}else if(action==actionCapt.SUBMIT){
				val = $target.serialize();
			}else if(action==actionCapt.MOUSEOVER){
				val = getContentMouseOver($target);
				//receber o title caso seja de um no pai
				var count = 0, parent = $target;
				while(val == ""){
					count++;
					parent = parent.parent();
					val = getContentMouseOver(parent);
					if(count >= 2){
						val = " ";
					}
				}
			}else if(action==actionCapt.CONCLUIR){
				val = document.documentElement.outerHTML;
			}

			if(val){
				return val;
			}else{
				return "";
			}
		}
		/**
		Função para filtrar a ação realizada
		*/
		function filterAction($e, defaolt){
			var isOnUseSkill = $e.parents('.UseSkill-nocapt').length;
			var id = $e.attr("id");
			var idParent = $e.parent().attr("id");
			var action = isOnUseSkillDIV(isOnUseSkill, id, idParent);
			if(action == true){
				action = null;
			}else if(action == false){
				action = defaolt;
			}
			return action;
		}
		/**
		Função para alterar a ação em elementos específicos da UseSkill
		*/
		function isOnUseSkillDIV(isOnUseSkill, id, idParent){
			var action = false;
			if(isOnUseSkill){
				action = true;
				if(id=="USroteiroFull"||idParent=="USroteiroFull"){
					action = actionCapt.ROTEIRO;
				}else if(id=="USconcluirTarefa"||idParent=="USconcluirTarefa"){
					action = actionCapt.CONCLUIR;
				}else if(id=="UScomentarioEnviar"||idParent=="UScomentarioEnviar"){
					action = actionCapt.COMENTARIO;
				}else if(id=="USpularTarefa"||idParent=="USpularTarefa"){
					action = actionCapt.PULAR;
				}
			}
			return action;
		}
		
		function getContentMouseOver($target){
			var title = $target.attr("title"), alt = $target.attr("alt");
			val = title ? title : "";
			val = alt ? (val ? val + " - " + alt : alt) : val;
			return val;
		}
		function printAcoes(){
			chrome.extension.sendRequest({useskill: "getAcoes"}, function(response) {
				console.log(response.dados);
			});
		}
		function addAcao(acao){
			var stringAcao = stringfyJSON(acao);
			chrome.extension.sendRequest({useskill: "addAcao", acao: stringAcao});
		}
		function setItem(name, acoes){
			var x = stringfyJSON(acoes);
			localStorage.setItem(name, x);
		}
		function getItem(name){
			var strObj = localStorage.getItem(name);
			return parseJSON(strObj);
		}
		function getUrl(){
			return location.protocol+location.host+location.pathname;
		}
		function parseJSON(data) {
			return window.JSON && window.JSON.parse ? window.JSON.parse(data) : (new Function("return " + data))(); 
		}
		function stringfyJSON(data){
			return window.JSON && window.JSON.stringify ? window.JSON.stringify(data) : (new Function("return " + data))();
		}
	});
})(jQuery);