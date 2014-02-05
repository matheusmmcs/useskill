// Saves options to localStorage.
(function($){
  $(document).ready(function(){
    var urlUseSkill = $('#urlUseSkill');
    var alert = $('#alert');
    restoreContent();
    function restoreContent(){
      var urlUseSkillLocalStorage;
      chrome.extension.sendRequest({useskillsettings: "getURL"}, function(response) {
        urlUseSkillLocalStorage = response.url;
        if (!urlUseSkillLocalStorage) {
          return;
        }
        urlUseSkill.val(urlUseSkillLocalStorage);
      });
    }

    $('#save').on('click', function(e){
      e.preventDefault();
      var newValue = urlUseSkill.val();
      
        chrome.extension.sendRequest({useskillsettings: "setURL", newValue: newValue}, function(response){
          if(response.success==true||response.success=="true"){
            alert.html('Operação realizada com sucesso!');
            alert.fadeOut().attr('class', 'alert alert-success').fadeIn(500,function(){
              setTimeout(function(){
                alert.fadeOut(500);
              }, 1500);
            });
          }else{
            alert.html('Ops! =(');
            alert.fadeOut().attr('class', 'alert alert-error').fadeIn(500,function(){
              setTimeout(function(){
                alert.fadeOut(500);
              }, 1500);
            });
          }
        });
      
    })
  });
})(jQuery);