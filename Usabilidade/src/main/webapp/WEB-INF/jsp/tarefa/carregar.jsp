<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-ex.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/bootstrap.js"></script>
<style>
body,.page_content,.page_table {
	background: white;
	color: #666;
	font-family: "lucida grande", tahoma, verdana, arial, sans-serif;
	font-size: 11px;
	height: 100%;
	margin: 0;
	padding: 0;
	width: 100%;
}

tbody {
	display: table-row-group;
	vertical-align: middle;
	border-color: inherit;
}

tr {
	display: table-row;
	vertical-align: inherit;
	border-color: inherit;
}

iframe {
	height: 100%;
}

html {
	height: 100%;
	max-height: 100%;
	padding: 0;
	margin: 0;
	border: 0;
	overflow: auto;
	text-align: justify
}

body {
	background-color: #ffffff;
	height: 100%;
	max-height: 100%;
	overflow: auto;
	padding: 0;
	margin: 0;
	border: 0;
}

table.page_table {
	padding: 0px;
	width: 100%;
	height: 100%;
	max-height: 100%;
}

td.outerTable {
	height: 100%;
	max-height: 100%;
	vertical-align: top;
}

td.brandingPanel {
	height: 60px;
	vertical-align: top;
	background: #EEF3F6;
	border-bottom: 1px solid #007aa5;
}

td.bodyPanel {
	vertical-align: top;
}

.concluir {
	float: right;
	margin: 15px 30px;
	padding: 5px 30px;
}
</style>
</head>

<body>
	<table cellspacing="0" cellpadding="0" class="page_table">
		<tbody>
			<tr>
				<td class="brandingPanel">
					<div id="topocontrole">
						<a class="btn btn-success concluir" id="concluir12qz3"
							href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2"
							style="margin-left: 10px"><span class="icon-ok"></span> <fmt:message
								key="concluirTarefa" /> </a>
					</div>
				</td>
			</tr>
			<tr>
				<td class="bodyPanel"><iframe class="page_content"
						src="${string}" frameborder="0" scrolling="auto"
						name="content_iframe"> </iframe>
				</td>
			</tr>
		</tbody>
	</table>
</body>