${teste.tituloPublico}
${pergunta.texto}

<c:if
	test="${pergunta.tipoRespostaAlternativa!=true}">
	<table class="table table-striped table-bordered table-condensed"
		style="background-color: white">
		<thead>
			<tr>
				<th><fmt:message key="respostas" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pergunta.respostaEscritas}" var="respostaEscria">
				<tr>
				<td>${respostaEscria.resposta}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

