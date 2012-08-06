(function($) {
	$(document).ready(
			function() {
				$(".alert").alert();
		        $('#modalMessages').modal({
		            'show':false
		        });
				
				// modal para btn-danger -> data-acao e data-href
		        // OBRIGATÓRIO: data-toggle="modal" href="#modalMessages"
		        // OPÇÕES:
		        // data-acao 		= editar nome da ação 	->	Prosseguir
		        // data-titulo 		= editar o título 		-> 	Cuidado, Ação Perigosa
		        // data-conteudo 	= editar o conteúdo		-> 	Você tem certeza que deseja prosseguir?
		        //
		        // SE LINK <a></a>
		        // data-href="urldesejada"

				$('.btn-modal').live('click', function(e) {
					e.preventDefault();
					var acao = $(this).attr('data-acao');
					var href = $(this).attr('data-href');
					var titulo = $(this).attr('data-titulo');
					var conteudo = $(this).attr('data-conteudo');
					var submit = $(this).is('input:submit');

					if (acao != "" && acao != undefined) {
						$('#modalMessagesAction').html(acao);
					}
					if (href != "" && href != undefined) {
						$('#modalMessagesAction').attr('href', href);
					}
					if (titulo != "" && titulo != undefined) {
						$('#modalMessagesTitulo').html(titulo);
					}
					if (conteudo != "" && conteudo != undefined) {
						$('#modalMessagesConteudo').html(conteudo);
					}
					if (submit){
						var $elemPai = $(this).parent(); 
						while(!$elemPai.is("form")){
							$elemPai = $elemPai.parent();
						}
						$('#modalMessagesAction').attr('data-form', $elemPai.attr('id'));
					}
				});
				
				$('#modalMessagesAction').click(function(e){
					var dataForm = $(this).attr('data-form');
					if(dataForm != "" && dataForm != undefined){
						e.preventDefault();
						//simular um form e limpar o attr
						$(this).attr('data-form','')
						$('#'+dataForm).submit();
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