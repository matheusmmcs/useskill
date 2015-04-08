//realiza a insercao do html dentro da pagina
(function($){
		var url = chrome.extension.getURL("html/useskill.html")
		var html = $('html');
		var iframeId = 'USmySidebarEaSII';
		if (document.getElementById(iframeId)) {
		  throw 'id:' + iframeId + 'taken please dont use this id!';
		}
		html.append('<div id="'+iframeId+'"></div>');
		if(url!=undefined){
			$('#'+iframeId).load(url);
		}
})(jQuery);