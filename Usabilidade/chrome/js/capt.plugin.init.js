window.isUseSkillOn = false;
(function(){
	var interval = window.setInterval(function(){
		
		if(!window.isUseSkillOn){
			if(isFunction(useskill_capt_onthefly)){
				useskill_capt_onthefly({
					sendactions: false,
					plugin: true,
					version: 1,
					client: "Plugin"
				});
				window.isUseSkillOn = true;
			}
		}else{
			window.clearInterval(interval);
		}
		
	}, 1000);
	
	function isFunction(functionToCheck) {
		 var getType = {};
		 return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
	}
})();