<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/capt.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function() {
		$('.fancybox').fancybox();
		$.webclientCapt({'url': "${pageContext.request.contextPath}/tarefa/save/fluxo/ideal"});
	});
})(jQuery);
</script>

<div id="inline1" style="width: 400px; display: none;">
	<h3>Roteiro</h3>
	<p>${tarefaDetalhe.roteiro}</p>
</div>
<div id="webclient-topo">
	<div class="direita">
		<a class="botao botao-verde concluir" id="concluir12qz3"
			href="${pageContext.request.contextPath}/teste/${testeSession.teste.id}/editar/passo2">
			<fmt:message key="concluirTarefa" /> </a>
	</div>
	<div class="centro">
		<a class="fancybox botao botao-info" id="roteiro" href="#inline1">Roteiro</a>
	</div>
	<div class="esquerda">
		<a class="bom botao botao-azul">Bom</a> 
		<a class="ruim botao botao-vermelho">Ruim</a>
		<a class="comentario botao"><fmt:message key="comentario" /> </a>
	</div>
</div>