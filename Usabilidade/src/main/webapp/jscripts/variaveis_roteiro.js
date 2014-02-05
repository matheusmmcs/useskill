(function($) {
	$(document).ready(function() {
		var $table = $("#tabela_valores_dinamicos"),
			$tbody = $table.find('tbody'),
			$variavel = $("#variavel"),
			$valor = $("#valor");
		
		$(document).on('click', '#addVariavel', function(e){
			e.preventDefault();
			
			var countTr = $tbody.attr('count-tr'),
				variavel = $variavel.val(),
				valor = $valor.val();
			
			if(variavel == null || valor == null ||
			   variavel == "" || valor == ""){
				alert("Variável e Valor devem ser preenchidos.");
				return 0;
			}
			
			if(countTr == null || countTr == undefined || countTr == 0){
				$table.show();
				countTr = 0;
			}
			
			var arrayValores = valor.split(";"), 
				valorHTML = "";
			
			for(var i in arrayValores){
				if(arrayValores[i] != ''){
					valorHTML += arrayValores[i] + "<br/>";
				}
			}
			
			//$tbody
			var idLinha = stringToId(variavel),
				$linha = $("#"+idLinha),
				conteudo = '<td>'+ variavel +'</td><td>'+ valorHTML +'</td><td> <a class="btn btn-danger valor-remove" href="#"><i class="icon-remove icon-white"></i></a> <input type="hidden" name="variaveis[]" value="'+ variavel + ":" + valor +'" /> </td>';
			if($linha.length == 0){
				$tbody.append('<tr id="'+ idLinha +'">'+conteudo+'</tr>');
				countTr++;
			}else{
				$linha.html(conteudo);
			}
			
			$tbody.attr('count-tr', countTr);
		});
		
		$(document).on('click', '.valor-remove', function(e){
			e.preventDefault();
			
			var countTr = $tbody.attr('count-tr'),
				$tr = $(this).closest('tr');
			
			$tr.remove();
			
			console.log(countTr);
			if(countTr == 1){
				$table.hide();
			}
			
			$tbody.attr('count-tr', countTr-1);
		});
		
		function stringToId(str){
			return str.replace(" ","").toUpperCase();
		}
		
	});
})(jQuery);