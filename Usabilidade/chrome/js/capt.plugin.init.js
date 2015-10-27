
//@ sourceURL=capt.useskill.init.js

window.useSkillActions = [];

(function(){
	console.log('capt init plugin!', window.isUseSkillPluginOn);
	
	var readyStateCheckInterval = setInterval(function() {
	    if (document.readyState === "interactive" || document.readyState === "complete") {
	        clearInterval(readyStateCheckInterval);
	        if(!window.isUseSkillPluginOn){
	    		useskill_capt_onthefly({
	    			debug: false,
					waitdomready: false,
					plugin: true
	    		});
	    		window.isUseSkillPluginOn = true;
	    	}
	    }
	}, 10);
	
})();