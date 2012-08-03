<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
	</c:if>

	<ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}/usuario"> <fmt:message
					key="testes.meus" /> </a> <span class="divider">/</span>
		</li>
		<li class="active"><fmt:message key="testes.editar" /><span
			class="divider">/</span></li>
		<li class="active"><fmt:message key="testes.passo2" /></li>
	</ul>

	<div class="btn-toolbar">
		<div class="pull-right">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/criar/tarefa">
				Inserir Tarefa</a> 
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/criar/pergunta"> 
				Inserir Pergunta</a>
		</div>
	</div>

	<ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1"><fmt:message
					key="testes.passo1" /> </a></li>
		<li class="active"><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2"><fmt:message
					key="testes.passo2" /> </a></li>
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"><fmt:message
					key="testes.passo3" /> </a></li>
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4"><fmt:message
					key="testes.passo4" /> </a></li>
	</ul>
	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span> <fmt:message key="testes.passo2" /> </span>
				<hr />
			</legend>
		</fieldset>


		<fieldset>
			<legend>
				<span><fmt:message key="tarefas"/></span>
				<hr />
			</legend>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="table.titulo" /></th>
						<th style="width: 70px"><fmt:message key="tarefa.perguntas" /></th>
						<th style="width: 85px"><fmt:message key="table.acoes" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty tarefas}">
						<c:forEach items="${tarefas}" var="tarefa">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${tarefa.id}/tarefa">
										${tarefa.nome}</a>
								</td>
								<td>
	                                <a class="btn"  href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${tarefa.id}/tarefa/questionario" style="margin-left: 15px;">
	                                <i class="icon-search"></i>
	                                </a>
                                </td>
								<td><a class="btn"
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${tarefa.id}/tarefa">
										<span class="icon-pencil"></span> </a> <a class="btn btn-primary"
									href="${pageContext.request.contextPath}/teste/removed/tarefa/idTeste/${testeView.teste.id}/idTarefa/${tarefa.id }"
									title="<fmt:message key="table.remover"/>"> <span
										class="icon-trash icon-white"></span> </a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</fieldset>


		<fieldset>
			<legend>
				<span><fmt:message key="testes.perguntas"/></span>
				<hr />
			</legend>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="table.titulo" /></th>
						<th style="width: 85px"><fmt:message key="table.acoes" />
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty perguntas}">
						<c:forEach items="${perguntas}" var="pergunta">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">
										${pergunta.titulo}</a>
								</td>
								<td><a class="btn"
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta"
									title="<fmt:message key="table.editar"/>"> <span
										class="icon-pencil"></span> </a> 
									<a class="btn btn-primary"
									href="${pageContext.request.contextPath}/teste/apagar/pergunta/testeId/${testeView.teste.id}/perguntaId/${pergunta.id}"
									title="<fmt:message key="table.remover"/>"> <span
										class="icon-trash icon-white"></span> </a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<div class="form-actions">
				<a
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"
				title="<fmt:message key="testes.proximopasso" />"
				class="btn btn-primary pull-right"> <fmt:message
					key="testes.continuar" /> </a>
			</div>
		</fieldset>
	</div>
</div>