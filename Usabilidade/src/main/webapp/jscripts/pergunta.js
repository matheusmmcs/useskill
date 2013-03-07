(function($) {
	$(document).ready(
		function() {
			var contalternativas = 0, maxalternativas = 5;
			var $grupoalternativas = $("#grupoalternativas"), $addalternativa = $("#addalternativa");
			
			$('.campoalternativa').each(function(){
				contalternativas++;
			});
			
			if(contalternativas==0){
				$grupoalternativas.hide();
			}

			var alternativa = '<div class="controls campoalternativa"><input type="text" class="span6 alternativa" name="pergunta.alternativas[].textoAlternativa" /><a class="btn btn-primary delalternativa" href="#"><i class="icon-trash icon-white"></i></a></div>';

			$(".delalternativa").live("click", function(e) {
				e.preventDefault();
				if (contalternativas > 1) {
					$(this).parent().remove();
					contalternativas--;
				}
			});

			$addalternativa.live('click', function(e) {
				e.preventDefault();
				if (contalternativas < maxalternativas) {
					$grupoalternativas.append(alternativa);
					contalternativas++;
				}
			});

			$("#respobj").live('click', function() {
				$grupoalternativas.show();
				$grupoalternativas.append(alternativa);
				contalternativas++;
			});

			$("#respsub").live('click', function() {
				$grupoalternativas.hide();
				$grupoalternativas.children(".campoalternativa").remove();
					contalternativas = 0;
				});
			});
})(jQuery);