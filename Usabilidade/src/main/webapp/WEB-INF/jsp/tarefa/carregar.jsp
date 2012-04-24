<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/captTester.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-ex.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/bootstrap.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/testar-carregar.css" />
<!--  FANCYBOX -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.css"
	media="screen" />
<script type="text/javascript">
	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script>

</head>
<body>
	<div id="inline1" style="width: 400px; display: none;">
		<h3>Roteiro</h3>
		<p>${tarefaDetalhe.roteiro}</p>
	</div>
	<table cellspacing="0" cellpadding="0" class="page_table">
		<tbody>
			<tr>
				<td class="brandingPanel">
					<div id="topocontrole">
						<div class="direita">
							<a class="btn btn-success concluir" id="concluir12qz3"
								href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2"><span
								class="icon-ok"></span> <fmt:message key="concluirTarefa" /> </a>
						</div>
						<div class="centro">
							<a class="fancybox btn btn-info" id="roteiro" href="#inline1">Roteiro</a>
						</div>
						<div class="esquerda">
							<a class="bom btn btn-primary">Bom</a>
							<a class="ruim btn btn-danger">Ruim</a>
							<a class="comentario btn"><fmt:message key="comentario" /></a>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="bodyPanel"><iframe src="${tarefaDetalhe.url}"
						frameborder="0" scrolling="auto"> </iframe>
				</td>
			</tr>
		</tbody>
	</table>
</body>