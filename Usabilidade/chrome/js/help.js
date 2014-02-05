// Saves options to localStorage.
(function($){
  $(document).ready(function(){
    $('a').on('click', function(e){
      e.preventDefault();
      var value = $(this).attr('href');
      if(value){
        if(value.charAt(0)=="#"){
          value = value.slice(1);
        }
        if(value){
          chrome.extension.sendRequest({useskillsettings: "omniboxEntered", text: value});
        }
      }
    });
  });
})(jQuery);