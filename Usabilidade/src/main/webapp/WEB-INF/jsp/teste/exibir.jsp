<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
        ${error.message}<br />
			</c:forEach>
		</div>
	</c:if>

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span>  </span>
				<p>${teste.tituloPublico}</p>
				<p>${pergunta.texto}</p>
				<hr />
			</legend>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>Acao</th>
						<th>tipoAcao</th>
						<th>tempo</th>
						<th>url</th>
						<th>conteudo</th>
						<th>tag</th>
						<th>tagId</th>
						<th>tagClass</th>
						<th>tagName</th>
						<th>tagValue</th>
						<th>posicaoPaginaY</th>
						<th>posicaoPaginaX</th>
						<th>tagType</th>
					</tr>
				</thead>
				<tbody>${stringBuilder}
				</tbody>
			</table>
		</fieldset>
	</div>
</div>