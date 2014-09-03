/*
Melhorias:
- Capturar o tamanho da janela para poder comparar o posicionamento dos itens (posX e posY);
- Capturar eventos de rolagem de barra -> content = posiçao no eixo y;
- Podemos pensar em modificações para agregar grandes mudanças assíncronas em app ricas;
- Capturar eventos referentes a retorno JSON ou alteração no DOM (inserção/remoção de nós);

Funcionamento
- Botao voltar:
MUDOU HASH
BACK
SAINDO...
LOAD

- F5 ou typed com hash:
SAINDO...
LOAD

- Mudou hash:
SAINDO...
LOAD
MUDOU HASH

*/
useskill_capt_onthefly({
	sendactions: false,
	plugin: true,
	version: 1,
	client: "Plugin"
});

function useskill_capt_onthefly(obj){
	(function($){
		$(document).ready(function(){

			console.log(getAcoes());

			if(!obj){
				obj = {};
			}

			//"http://localhost:3000/actions/create";
			//"http://96.126.116.159:3000/actions/create"
			var URL = obj.url ? obj.url : "";
			var CLIENT = obj.client ? obj.client : "";
			var VERSION = obj.version ? obj.version : 0;
			var USERNAME = obj.username ? obj.username : "";
			var ROLE = obj.role ? obj.role : "";
			var SEND_MESSAGES = obj.sendactions ? obj.sendactions : false;
			var CONNECTED_PLUGIN = obj.plugin ? obj.plugin : false;

			var TIME_SUBMIT_SEG = 300;
			var TIME_SUBMIT = TIME_SUBMIT_SEG * 1000;

			var interval = setInterval(function(){
				sendAcoes();
			}, TIME_SUBMIT);

			window.onbeforeunload = function(e) {
				sendAcoes();
			};
			
			// window.onpopstate = function(event) {
			// 	console.log("MUDOU HASH: " + JSON.stringify(event.state));
			// };

			var sending = false;
			
	    	function sendAcoes(){
				//return "Are you sure?";
	    		if(SEND_MESSAGES){
	    			var acoesString = getAcoesString();
					if(acoesString && !sending){
						sending = true;
						$.ajax({
					        url: URL,
					       	type: "POST",
				            async: false,
				            cache: false,
				            data: ({ "acoes" : acoesString }),
				            success: function(result) { 
				            	console.log("SUCESS: ", result);
				            	clearStorage();
				            	sending = false;
				            },
				            error: function(data) { 
				            	//location.reload(); 
				            	console.log(data);
				            	sending = false;
				            }
					    });
					}
	    		}
	    	}
			
			ultimaAcao = null;

			var actionCapt = {
	  			CLICK : "click", 
	  			FOCUSOUT: "focusout",
	  			MOUSEOVER: "mouseover",

	  			BROWSER_BACK : "back",
	  			BROWSER_FORM_SUBMIT : "form_submit",
	  			BROWSER_ONLOAD : "onload",
	  			BROWSER_RELOAD : "reload",
	  			BROWSER_CLOSE : "close",
	  			
	  			ROTEIRO : "roteiro",
	  			CONCLUIR : "concluir",
  				COMENTARIO : "comentario",
  				PULAR : "pular"
			};

			function Action(action, time, url, content, tag, tagIndex, id, classe, name, xPath, posX, posY, viewportX, viewportY, useragent) {
				this.sActionType = action;
				this.sTime = time;
				this.sUrl = url;
				this.sContent = String(content).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
				this.sContent = this.sContent.substr(0, 200);
				this.sTag = tag;
				this.sTagIndex = tagIndex;
				this.sId = id;
				this.sClass = classe;
				this.sName = name;
				this.sXPath = xPath;
				this.sPosX = posX;
				this.sPosY = posY;
				this.sViewportX = viewportX;
				this.sViewportY = viewportY;
				this.sUserAgent = useragent;

				this.sRealTime = new Date().getTime();
				this.sTimezoneOffset = new Date().getTimezoneOffset();

				this.sUsername = USERNAME;
				this.sRole = ROLE;
				this.sJhm = getJhmName();
				this.sActionJhm = getActionJhm();
				this.sSectionJhm = getSectionJhm();
				
				this.sClient = CLIENT;
				this.sVersion = VERSION;
			}

			var lastMouseMove = 0, lastMouseX, lastMouseY, mouseOffSet = 5;
			$(document).on("click", function(e) {
				insertNewAcao(e, actionCapt.CLICK);
			});
			$(document).on("focusout", function(e) {
				insertNewAcao(e, actionCapt.FOCUSOUT);
			});
			$(document).on("mousemove", function(e) {
				if(e.timeStamp - lastMouseMove >= 750){
					if(e.pageX <= lastMouseX+mouseOffSet &&
					   e.pageX >= lastMouseX-mouseOffSet &&
					   e.pageY <= lastMouseY+mouseOffSet &&
					   e.pageY >= lastMouseY-mouseOffSet){
						insertNewAcao(e, actionCapt.MOUSEOVER);
					}
				}
				lastMouseMove = e.timeStamp;
				lastMouseX = e.pageX;
				lastMouseY = e.pageY;
			});
			$(document).on("submit", function(e) {
				insertNewAcao(e, actionCapt.BROWSER_FORM_SUBMIT);
			});
			
			//capturar dados do jheat
			function getJhmName(){
				return getRegexValue(/(reportName|flowName|className)\=([^\&|\#]*)/g, true);
			}

			function getSectionJhm(){
				return getRegexValue(/(sectionName)\=([^\&|\#]*)/g, false);
			}

			function getActionJhm(){
				return getRegexValue(/(action)\=([^\&|\#]*)/g, false);
			}

			function getRegexValue(reg, fullSize){
				var matches = reg.exec(window.location.href);
				//{type: matches[1], value: matches[2]} : {type:"", value: ""};
				return matches != null ? (fullSize ? matches[1]+"="+matches[2] : matches[2]) : "";
			}

			//eventos extras, referentes ao carregamento da página e ao botao voltar
			window.onload = function(e){
				insertNewAcao(e, actionCapt.BROWSER_ONLOAD);
			}
			bajb_backdetect.OnBack = function(e){
				insertNewAcao({
					target: document.getElementsByTagName("html"),
					pageX: 0,
					pageY: 0
				}, actionCapt.BROWSER_BACK);
			}
			document.onkeydown = function(event) {
			    if(typeof event != 'undefined'){
			    	//Ctrl+R ou F5
			    	if(event.keyCode == 116 || (event.ctrlKey && event.keyCode == 82)){
			        	insertNewAcao(event, actionCapt.BROWSER_RELOAD);
			    	}
			    	//Ctrl+W ou ALT+F4
				    if((event.ctrlKey && event.keyCode == 87) || (event.altKey && event.keyCode == 114)) { 
						insertNewAcao(event, actionCapt.BROWSER_CLOSE);
					}
					if(event.ctrlKey && event.keyCode == 88){
						sendAcoes();
					}
			    }
			};

			/*	FUNÇÕES EXTRAS	*/
			function insertNewAcao(e, action){
				var target = e.target;
				var tagName = target.tagName;
				var $target = $(target);
				if(CONNECTED_PLUGIN){
					action = filterAction(e, $target, action);
				}
				if(action){
					var conteudo = getContent($target, action);
					//filtros de captura de dados
					if(
						!(action == actionCapt.FOCUSOUT && conteudo == "")//focusout em campo vazio, não preencheu nada
					){
						var acao = new Action(action, new Date().getTime(), getUrl(), conteudo, target.tagName, $(tagName).index(target), target.id, target.className, target.name, createXPathFromElement(e.target), e.pageX, e.pageY, $(window).width(), $(window).height(), navigator.userAgent);
						//verificar se a acao eh semelhante com a acao passada
						//console.log(acao, acao.sXPath);
						//TODO: se for concluir, verificar se já há outro concluir
						if(ultimaAcao){
							var acoesRapidas = (action == actionCapt.CLICK);
							//se for acao permitida, nao houver acao anterir ou se for mais demorado que 10ms
							if(acoesRapidas || !ultimaAcao.sTime || acao.sActionType != ultimaAcao.sActionType || (acao.sTime && acao.sTime - ultimaAcao.sTime > 10)){
								ultimaAcao = acao;
								addAcao(acao);
							}else{
								ultimaAcao = acao;
								console.log("Evitou repetição:", ultimaAcao);
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
				}else if(action==actionCapt.BROWSER_FORM_SUBMIT){
					val = "ACTION=["+$target.attr("action")+"], METHOD=["+$target.attr("method")+"]";
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
			function filterAction(e, $e, defaolt){
				var action = false;
				if(e.type != "focusout"){
					var isOnUseSkill = $e.parents('.UseSkill-nocapt').length;
					var id = $e.attr("id");
					var idParent = $e.parent().attr("id");
					action = isOnUseSkillDIV(isOnUseSkill, id, idParent);
				}
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

			function getAcoesString(){
				return localStorage.getItem("useskill_actions");
			}

			function getAcoes(){
				var str = localStorage.getItem("useskill_actions");
				return parseJSON(str);
			}

			function addAcao(acao){				
				if(CONNECTED_PLUGIN){
					var stringAcao = stringfyJSON(acao);
					chrome.extension.sendRequest({useskill: "addAcao", acao: stringAcao});
				}else{
					var arr = getAcoes();
					if(Array.isArray(arr)){
						arr.push(acao);
					}else{
						arr = [acao];
					}
					var str = stringfyJSON(arr);
					//console.log("ADD_ACAO: ", acao)
					localStorage.setItem("useskill_actions", str);
				}
			}

			function setItem(name, acoes){
				var x = stringfyJSON(acoes);
				localStorage.setItem(name, x);
			}

			function clearStorage(){
				localStorage.removeItem("useskill_actions");
			}

			function getItem(name){
				var strObj = localStorage.getItem(name);
				return parseJSON(strObj);
			}

			function getUrl(){
				return location.protocol+"//"+location.host+location.pathname;
			}

			function parseJSON(data) {
				return window.JSON && window.JSON.parse ? window.JSON.parse(data) : (new Function("return " + data))(); 
			}

			function stringfyJSON(data){
				return window.JSON && window.JSON.stringify ? window.JSON.stringify(data) : (new Function("return " + data))();
			}

			function createXPathFromElement(elm) {
			    var allNodes = document.getElementsByTagName('*');
			    for (segs = []; elm && elm.nodeType == 1; elm = elm.parentNode){
			        if (elm.hasAttribute('id')) {
			                var uniqueIdCount = 0;
			                for (var n=0;n < allNodes.length;n++) {
			                    if (allNodes[n].hasAttribute('id') && allNodes[n].id == elm.id) uniqueIdCount++;
			                    if (uniqueIdCount > 1) break;
			                };
			                if ( uniqueIdCount == 1) {
			                    segs.unshift('id("' + elm.getAttribute('id') + '")');
			                    return segs.join('/');
			                } else {
			                    segs.unshift(elm.localName.toLowerCase() + '[@id="' + elm.getAttribute('id') + '"]');
			                }
			        } else if (elm.hasAttribute('class')) {
			            segs.unshift(elm.localName.toLowerCase() + '[@class="' + elm.getAttribute('class') + '"]');
			        } else {
			            for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
			                if (sib.localName == elm.localName)  i++; };
			                segs.unshift(elm.localName.toLowerCase() + '[' + i + ']');
			        };
			    };
			    return segs.length ? '/' + segs.join('/') : null;
			}; 

			function lookupElementByXPath(path) {
			    var evaluator = new XPathEvaluator();
			    var result = evaluator.evaluate(path, document.documentElement, null,XPathResult.FIRST_ORDERED_NODE_TYPE, null);
			    return  result.singleNodeValue;
			}

		});
	})(jQuery);
}