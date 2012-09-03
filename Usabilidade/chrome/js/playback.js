var nameStorage = "UScapt";

(function($){
	$.playbackCapt = function(settings) {
		$(document).ready(function(){
			var config = {
					'time': null
			};
			if(settings){$.extend(config, settings);}

			console.log("playback -> localStorage");
			console.log(getItem(nameStorage));

			playBack(getItem(nameStorage),config.time);

			/*	PLAYBACK	*/
			function playBack(array,time){
				var delay;
				console.log(array)
				if(array.length>0){
					var evt = array[0];
					var nexEvt = array[1];
					//definir o tempo para a execução do próximo elemento
					if(nexEvt){
						delay = nexEvt.sTime - evt.sTime;
					}else{
						delay = 0;
					}
					if(time){
						delay = time;
					}
					array.splice(0,1);
					if(evt.sTag!="HTML" && evt.sTag!="BODY"){
						var $el = $(evt.sTag).eq(evt.sTagIndex);
						//realizar a ação correspondente
						if(evt.sActionType=="focusout"){
							$el.val(evt.sContent);
						}else{
							$el.trigger(evt.sActionType);
						}
						setTimeout(function() {
						    playBack(array,time);
						}, delay);
					}

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
		});
	}
})(jQuery);