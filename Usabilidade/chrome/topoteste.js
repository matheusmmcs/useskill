(function($){
		var url = chrome.extension.getURL("html/topoteste.html")

		var body = $('body');

		var iframeId = 'USsomeSidebar7X';
		if (document.getElementById(iframeId)) {
		  throw 'id:' + iframeId + 'taken please dont use this id!';
		}
		body.append('<div id="'+iframeId+'"></div>');

		if(url!=undefined){
			$('#'+iframeId).load(url);
		}
})(jQuery);