<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/capt.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function() {
		$('.fancybox').fancybox();
		$.webclientCapt({'url': "${pageContext.request.contextPath}/tarefa/save/fluxo/usuario"});
		
		$('#roteiro').click(function(e){
			$.ajax({
				url : "${pageContext.request.contextPath}/tarefa/roteiro",
				type : 'POST',
				dataType : 'json',
				async : false,
				data : {
					'idTarefa' : get_url_parameter("idTarefa")
				},
				success : function(json) {
					console.log("SUCCESS");
					$('#inline1').children('p').html(json.string);
				},
				error : function(jqXHR, textStatus, errorThrown){
					console.log("ERRO");
					
				}
			});
		});
		
		function get_url_parameter(param) {
			param = param.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
			var r1 = "[\\?&]" + param + "=([^&#]*)";
			var r2 = new RegExp(r1);
			var r3 = r2.exec(window.location.href);
			if (r3 == null)
				return "";
			else
				return r3[1];
		}
	});
})(jQuery);
</script>

<div id="inline1" style="width: 400px; display: none;">
	<h3>Roteiro</h3>
	<p></p>
</div>
<div id="webclient-topo">
	<div class="direita">
		<a class="botao botao-verde concluir" id="concluir12qz3"
			href="${pageContext.request.contextPath}/tarefa/loadtaskuser">
			<fmt:message key="concluirTarefa" /> </a>
	</div>
	<div class="centro" id="roteiroContent">
		<a class="fancybox botao botao-info" id="roteiro" href="#inline1">Roteiro</a>
	</div>
	<div class="esquerda">
		<a class="bom botao botao-azul">Bom</a> 
		<a class="ruim botao botao-vermelho">Ruim</a>
		<a class="comentario botao"><fmt:message key="comentario" /> </a>
	</div>
</div>