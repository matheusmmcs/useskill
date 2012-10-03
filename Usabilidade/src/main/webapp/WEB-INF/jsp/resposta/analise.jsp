<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
        ${error.message}<br />
			</c:forEach>
		</div>
	</c:if>
<c:choose>
	<c:when test="${isAlternativa ==true}">
			<div id="chart_div" style="width: 400; height: 300"></div>
			</div>
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
			      var options = {'title':'How Much Pizza I Ate Last Night',
			                     'width':400,
			                     'height':300};
			
			      // Instantiate and draw our chart, passing in some options.
			      var chart =  new google.visualization.PieChart(document.getElementById('chart_div'));
			      chart.draw(data, options);
			    }
			</script>
		</c:when>	
	<c:otherwise>
					<c:if test="${not empty respostasEscrita}">
					<c:forEach items="${respostasEscrita}" var="resposta">
					Resposta: ${resposta.resposta }</br>
					Usuario: ${resposta.nomeUsuario }</br>
					</c:forEach>
				</c:if>	</c:otherwise>
</c:choose>


