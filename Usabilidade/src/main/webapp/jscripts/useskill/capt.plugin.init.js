window.useSkillActions = '[]';

(function(){
	if(!window.isUseSkillOn){
		useskill_capt_onthefly({
			plugin: true
		});
		window.isUseSkillOn = true;
	}
})();