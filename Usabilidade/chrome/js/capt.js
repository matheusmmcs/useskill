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
				var acao = new Action('click', new Date().getTime(), getUrl(), $(e.target).html(), e.target.tagName, $(e.target.tagName).index(e.target), e.pageX, e.pageY);
				addAcao(acao);
				//$(e.target).css("background",'red');
			}, focusout : function(e) {
				var acao = new Action('focusout', new Date().getTime(), getUrl(), $(e.target).val(), e.target.tagName, $(e.target.tagName).index(e.target), e.pageX, e.pageY);
				addAcao(acao);
				//$(e.target).css("background",'red');
			}
		});
		/*	FUNÇÕES EXTRAS	*/
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