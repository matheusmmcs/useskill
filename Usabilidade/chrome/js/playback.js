(function($){
	$.playbackCapt = function(settings) {
		$(document).ready(function(){
			var config = {
					'time': null
			};
			if(settings){$.extend(config, settings);}

			chrome.extension.sendRequest({useskill: "getAcoes"}, function(response) {
				var array = response.dados;
				console.log("playback -> localStorage");
				playBack(array,config.time);
			});

			/*	PLAYBACK	*/
			function playBack(array,time){
				var delay;
				console.log(array)
				if(array.length>0){
					var evt = array[0];
					var nexEvt = array[1];
					var isUrl = false;
					//definir o tempo para a execução do próximo elemento
					if(nexEvt){
						delay = nexEvt.sTime - evt.sTime;
					}else{
						delay = 0;
					}
					if(time){
						delay = time;
					}
					//url do envento == a atual
					if(getUrl() == evt.sUrl){
						isUrl = true;
					}else{
						delay = 0;
					}
					array.splice(0,1);
					if(evt.sTag!="HTML" && evt.sTag!="BODY" && isUrl){
						var $el = $(evt.sTag).eq(evt.sTagIndex);
						//realizar a ação correspondente
						if(evt.sActionType=="focusout"){
							$el.blur();
							$el.val(evt.sContent);
						}else{
							$el.trigger(evt.sActionType);
						}
					}
					setTimeout(function() {
					    playBack(array,time);
					}, delay);
				}
			}
			/*	FUNÇÕES EXTRAS	*/
			function getItem(name){
				var strObj = localStorage.getItem(name);
				return parseJSON(strObj);
			}
			function parseJSON(data) {
				return window.JSON && window.JSON.parse ? window.JSON.parse(data) : (new Function("return " + data))(); 
			}
			function getUrl(){
				return location.protocol+location.host+location.pathname;
			}
		});
	};

	$.playbackCapt({time:500});
})(jQuery);