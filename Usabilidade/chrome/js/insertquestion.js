(function($){
	$(document).ready(function(){
		$('input:submit').click(function(e){
			e.preventDefault();
			chrome.extension.sendRequest({useskill: "getStorage"}, function(dado) {
				var prox = dado.dados.elementoAtualLista;
				prox++;
				chrome.extension.sendRequest({useskill: "nextElement", atual: prox});	
			});
		});
	});
})(jQuery);