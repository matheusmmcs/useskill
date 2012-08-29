(function($){
		var url = chrome.extension.getURL("html/topoteste.html")

		var html = $('html');

		var iframeId = 'USsomeSidebar7X1';
		if (document.getElementById(iframeId)) {
		  throw 'id:' + iframeId + 'taken please dont use this id!';
		}
		html.append('<div id="'+iframeId+'"></div>');

		if(url!=undefined){
			$('#'+iframeId).load(url);
		}
})(jQuery);