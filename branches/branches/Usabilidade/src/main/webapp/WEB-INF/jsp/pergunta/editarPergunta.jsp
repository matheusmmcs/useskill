<ul class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/usuario">Início</a>
		<span class="divider">/</span></li>
	<li><a
		href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1">Editar
			Teste - Passo 1</a> <span class="divider">/</span></li>
	<li><a
		href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Passo
			2</a> <span class="divider">/</span></li>
	<li class="active">Inserir Pergunta</li>
</ul>

<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
            ${error.message}<br />
		</c:forEach>
	</div>
</c:if>

<form class="form-horizontal form-layout w600"
	action="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/salvar/pergunta"
	method="POST">
	<fieldset>
		<legend>Editar Pergunta</legend>
		<c:if test="${not empty pergunta.id}">
			<input type="hidden" name="pergunta.id" value="${pergunta.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<div class="control-group">
			<label class="control-label" for="input01"><fmt:message
					key="pergunta.titulo" />*</label>
			<div class="controls">
				<input type="text" name="pergunta.titulo" value="${pergunta.titulo}"
					id="titulo" class="input-xmlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="input01"><fmt:message
					key="pergunta.texto" />*</label>
			<div class="controls">
				<textarea rows="10" cols="" name="pergunta.texto" id="texto"
					class="input-xmlarge">${pergunta.texto}</textarea>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">Selecione</label>
			<div class="controls">
				<label class="radio" style="margin-top: -0px;"> <input
					type="radio" name="pergunta.responderFim"
					value="true"
					<c:if test="${pergunta.responderFim== true}" >
               checked
                    </c:if> />
					<fmt:message key="pergunta.responderFim" />
				</label> <label class="radio" style="margin-top: -0px;"> <input
					type="radio" name="pergunta.responderFim"
					value="false"
					<c:if test="${pergunta.responderFim== false}" >
                 checked
                    </c:if> />
					<fmt:message key="pergunta.responderInicio" />
				</label>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">Selecione</label>
			<div class="controls">
				<label class="radio" style="margin-top: -30px;"> <input
					type="radio" name="pergunta.tipoRespostaAlternativa" value="true"
					<c:if test="${pergunta.tipoRespostaAlternativa== true}" >
               checked
                    </c:if> />

					<fmt:message key="pergunta.subjetiva" />
				</label> <label class="radio"> <input type="radio"
					name="pergunta.tipoRespostaAlternativa" value="false"
					<c:if test="${pergunta.tipoRespostaAlternativa== false}" >
               checked
                    </c:if> />
					<fmt:message key="pergunta.objetiva" />
				</label>
			</div>
		</div>


		<c:forEach items="${pergunta.alternativas}" var="alternativa">
			<div class="alternativa-group">
				<label class="control-label" for="input01"><fmt:message
						key="pergunta.alternativa" />*</label>
				<div class="controls">
					<textarea rows="10" cols=""
						name="pergunta.alternativas[].textoAlternativa" id="texto"
						class="input-xmlarge">${alternativa.textoAlternativa}</textarea>
				</div>
			</div>


		</c:forEach>

		<div class="form-actions">
			<input type="submit" name="pergunta.salvar"
				value="<fmt:message key="pergunta.salvar"/>" title="Criar Pergunta"
				class="btn btn-primary" style="float: right" />
		</div>
	</fieldset>
</form>



<script type="text/javascript">
    $(document)
    .ready(
    function() {
   
        $("#editUsuario_Form").validate({
            rules : {
                "usuario.nome" : {
                    required : true
                },
                "usuario.email" : {
                    required : true,
                    email : true
                },
                "usuario.senha" : {
                    required : true,
                    minLength : 6
                },
                "confirmaSenha" : {
                    required : true,
                    minLength : 6
                }
            }
        });
</script>