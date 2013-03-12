(function($){
	$(document).ready(function(){
		
		var actionCapt = {
  			CLICK : "click", 
  			FOCUSOUT: "focusout", 
  			SUBMIT : "submit",
  			ROTEIRO : "roteiro",
  			CONCLUIR : "concluir",
  			COMENTARIO : "comentario"
		};

		function Action(action, time, url, content, tag, tagIndex, posX, posY) {
			this.sActionType = action;
			this.sTime = time;
			this.sUrl = url;
			this.sContent = String(content).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').substr(0, 200);
			this.sTag = tag;
			this.sTagIndex = tagIndex;
			this.sPosX = posX;
			this.sPosY = posY;
		}

		console.log("capt -> localStorage");
		printAcoes();

		$(document).on({
			click : function(e) {
				insertNewAcao(e, actionCapt.CLICK);
			}, focusout : function(e) {
				insertNewAcao(e, actionCapt.FOCUSOUT);
			}, submit : function(e) {
				insertNewAcao(e, actionCapt.SUBMIT);
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
					var acao = new Action(action, new Date().getTime(), getUrl(), conteudo, target.tagName, $(tagName).index(target), e.pageX, e.pageY);
					addAcao(acao);
					
				}
			}
		}
		function getContent($target, action){
			var val = null;
			if(action==actionCapt.COMENTARIO){
				val = $('#UScomentText').val();
			}else if(action==actionCapt.CLICK){
				val = $target.html();
			}else if(action==actionCapt.FOCUSOUT){
				val = $target.val();
			}else if(action==actionCapt.SUBMIT){
				val = $target.serialize();
			}

			if(val){
				return val;
			}else{
				return "";
			}
		}
		function filterAction($e, defaolt){
			var isOnUseSkill = $e.parents('#UseSkill-nocapt').length;
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
		function isOnUseSkillDIV(isOnUseSkill, id, idParent){
			var action = false;
			if(isOnUseSkill){
				action = true;
				if(id=="USIDroteiro"||idParent=="USIDroteiro"){
					action = actionCapt.ROTEIRO;
				}else if(id=="concluir12qz3"||idParent=="concluir12qz3"){
					action = actionCapt.CONCLUIR;
				}else if(id=="UScomentEnviar"||idParent=="UScomentEnviar"){
					action = actionCapt.COMENTARIO;
				}
			}
			return action;
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