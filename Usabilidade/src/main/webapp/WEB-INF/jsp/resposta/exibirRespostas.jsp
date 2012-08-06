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
			<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><fmt:message key="respostas" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pergunta.respostaEscritas}"
							var="respostaEscria">
							<tr>
								<td>${respostaEscria.resposta}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</fieldset>
	</div>
</div>