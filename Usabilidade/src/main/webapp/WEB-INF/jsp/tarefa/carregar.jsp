<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-ex.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jscripts/bootstrap.js" />
<style>
body {
	position: relative;
	height: 100%;
	width: 100%;
	margin: 0;
	padding: 0;
}

iframe {
	position: relative;
	width: 100%;
	height: 85%;
	margin: 0;
	padding: 0;
}

#topocontrole {
	position: relative;
	width: 100%;
	height: 79px;
	min-height: 79px;
	background: #EEF3F6;
	border-bottom: 1px solid #007aa5;
}
.concluir{
	float: right;
	margin: 25px;
	padding: 5px 30px;
}
</style>
</head>

<body>
	<div id="topocontrole">
		<a class="btn btn-success concluir" id="concluir12qz3"  href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2" style="margin-left: 10px"><span class="icon-ok"></span> <fmt:message key="concluirTarefa" /></a>
	</div>
	<iframe src="${string}" frameborder="0"> </iframe>
</body>