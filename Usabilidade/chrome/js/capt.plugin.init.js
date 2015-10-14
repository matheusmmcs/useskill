
//@ sourceURL=capt.useskill.init.js

window.useSkillActions = [];

(function(){
	console.log('capt init!', window.isUseSkillOn);
	
	var readyStateCheckInterval = setInterval(function() {
	    if (document.readyState === "interactive" || document.readyState === "complete") {
	        clearInterval(readyStateCheckInterval);
	        if(!window.isUseSkillOn){
	    		useskill_capt_onthefly({
	    			plugin: true
	    		});
	    		window.isUseSkillOn = true;
	    	}
	    }
	}, 10);
	
})();