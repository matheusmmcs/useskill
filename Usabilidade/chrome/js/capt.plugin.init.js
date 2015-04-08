window.isUseSkillOn = false;
window.useSkillActions = [];

(function(){
	if(!window.isUseSkillOn){
		useskill_capt_onthefly({
			sendactions: false,
			plugin: true,
			version: 1,
			client: "Plugin"
		});
		window.isUseSkillOn = true;
	}
})();