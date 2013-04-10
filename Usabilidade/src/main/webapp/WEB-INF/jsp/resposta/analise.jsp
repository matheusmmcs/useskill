<%@include file="../leftmenus/default.jsp"%>
<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
        		${error.message}<br />
			</c:forEach>
		</div>
	</c:if>

	<ul class="breadcrumb">
		<li>
			<a href="${pageContext.request.contextPath}/usuario"> 
				<fmt:message key="testes.meus" />
			</a>
			<span class="divider">/</span>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/testes/liberados"> 
				<fmt:message key="testes.liberado" />
			</a>
			<span class="divider">/</span>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/teste/${teste.id}/analise"> 
				<fmt:message key="analise.tarefas" />
			</a>
			<span class="divider">/</span>     
		</li>
		<li class="active">
			<fmt:message key="analise.grafico.respostas.subjetivas" />
		</li>
	</ul>

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span> ${pergunta.titulo } </span>
				<hr />
			</legend>
			<p style="padding: 0px 10px 10px; border-bottom: 1px solid #eee; font-size: 14px"> ${pergunta.texto } </p>
			<c:choose>
				<c:when test="${isAlternativa ==true}">
					<div id="chart_div" style="width: 400; height: 300"></div>
					<script type="text/javascript" src="https://www.google.com/jsapi"></script>
					<script type="text/javascript">
					      google.load('visualization', '1.0', {'packages':['corechart']});
					      google.setOnLoadCallback(drawChart);
					      function drawChart() {
					      var data = new google.visualization.arrayToDataTable(
					   	  [['Alternativa','Quantidade de Respostas'],
							<c:forEach items="${respostas}" var="resposta">
					        ['${resposta.alternativa.textoAlternativa}', ${resposta.quantidadeRespostas}], 
							</c:forEach>
					      ]);
					
					      // Set chart options
					      var options = {'width':700,
					                     'height':350};
					
					      // Instantiate and draw our chart, passing in some options.
					      var chart =  new google.visualization.PieChart(document.getElementById('chart_div'));
					      chart.draw(data, options);
					    }
					</script>
				</c:when>	
				<c:otherwise>
					<c:if test="${not empty respostasEscrita}">
						<c:forEach items="${respostasEscrita}" var="resposta">
							Resposta: ${resposta.resposta }<br/>
							<span style="color: #bbb">Usuario: ${resposta.nomeUsuario }</span></br>
							<br/>
						</c:forEach>
					</c:if>	
				</c:otherwise>
			</c:choose>	
		</fieldset>
	</div>
</div>