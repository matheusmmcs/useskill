(function($) {
	$(document).ready(
			function() {
				// modal para btn-danger -> data-acao e data-href
				$('.btn-danger').click(function(e) {
					e.preventDefault();
					var acao = $(this).attr('data-acao');
					var href = $(this).attr('data-href');

					if (acao != "") {
						$('#modalDangerAcao').html(acao);
					}
					if (href != "") {
						$('#modalDangerAcao').attr('href', href);
					}
				});

				// menu esquerdo
				$('.nav-show').click(function(e) {
					e.preventDefault();
					$(this).parent().parent().parent().children('ul').eq(1).toggle(200);
				});
				$('.nav-show').trigger('click');
			});
})(jQuery);