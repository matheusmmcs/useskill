(function($){
	$(document).ready(function(){
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

		$(document).bind({
			click : function(e) {
				var action = getAction($(e.target), "click");
				if(action){
					var acao = new Action(action, new Date().getTime(), getUrl(), $(e.target).html(), e.target.tagName, $(e.target.tagName).index(e.target), e.pageX, e.pageY);
					addAcao(acao);
				}
			}, focusout : function(e) {
				var action = getAction($(e.target), "focusout");
				if(action){
					var acao = new Action(action, new Date().getTime(), getUrl(), $(e.target).val(), e.target.tagName, $(e.target.tagName).index(e.target), e.pageX, e.pageY);
					addAcao(acao);
				}
			}
		});
		/*	FUNÇÕES EXTRAS	*/
		function getAction($e, defaolt){
			var parent = $e.parents('#UseSkill-nocapt').length;
			var id = $e.attr("id");
			var action = filterIsOnUseSkillDIV(parent, id);
			console.log(action)
			if(action == true){
				action = null;
			}else if(action == false){
				action = defaolt;
			}
			return action;
		}
		function filterIsOnUseSkillDIV(param, id){
			var action = false;
			console.log(param)
			console.log(id)
			if(param){
				action = true;
				if(id=="USIDroteiro"){
					action = "roteiro";
				}else if(id=="concluir12qz3"){
					action = "concluir";
				}else if(id=="UScomentario"){
					action = "cometario";
				}
			}
			return action;
		}
		printAcoes();
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