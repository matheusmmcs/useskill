<ul class="breadcrumb">
    <li>
        <a href="${pageContext.request.contextPath}/usuario">Início</a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1">Editar Teste - Passo 1</a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Passo 2</a> <span class="divider">/</span>
    </li>
    <li class="active">Inserir Pergunta</li>
</ul>

<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>

<form class="form-horizontal form-layout w600" action="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/salvar/pergunta" method="POST">
    <fieldset>
        <legend>Inserir Pergunta</legend>
        <c:if test="${not empty pergunta.id}">
            <input type="hidden" name="pergunta.id" value="${pergunta.id}" />
            <input type="hidden" name="_method" value="put"/>
        </c:if>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="pergunta.titulo" />*</label>
            <div class="controls">
                <input type="text" name="pergunta.titulo" value="${pergunta.titulo}" id="titulo" class="input-xmlarge"/>
                <p class="help-block" style="float: left; margin-top: 3px"><fmt:message key="pergunta.info.titulo"/></p> 
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="pergunta.texto" />*</label>
            <div class="controls">
                <textarea rows="10" cols="" name="pergunta.texto" id="texto" class="input-xmlarge">${pergunta.texto}</textarea>
            </div>
            
            
            	<div class="control-group">
			<label class="control-label">Selecione</label>
			<div class="controls">
		  <label class="radio" style="margin-top: -0px;"> 
                    <input type="radio" name="pergunta.responderFim" value="true" />
                    <fmt:message key="pergunta.responderFim"/>
                </label>
              <label class="radio" style="margin-top: -0px;"> 
                    <input type="radio" name="pergunta.responderFim" value="false" checked/>
                    <fmt:message key="pergunta.responderInicio" />
                </label>
			</div>
		</div>    
      
      
        <div class="control-group">
            <label class="control-label">Selecione</label>
            <div class="controls">
             
            
                <label class="radio" style="margin-top: -30px;"> 
                    <input id="respsub" type="radio" name="pergunta.tipoRespostaAlternativa" value="true"/>
                    <fmt:message key="pergunta.objetiva"/>
                </label>
                <label class="radio">
                    <input id="respobj" type="radio" name="pergunta.tipoRespostaAlternativa" value="false" checked/>                    
                    <fmt:message key="pergunta.subjetiva"/>
                </label>
            </div>
        </div>
    	<div id="grupoalternativas" class="alternativa-group" style="display: none;">
            <label class="control-label" for="input01"><fmt:message key="pergunta.alternativa" />* <a id="addalternativa" class="btn btn-info" href="www.google.com"><i class="icon-plus-sign icon-white"></i></a> </label>
            
        </div>

        <div class="form-actions">
            <input type="submit" name="pergunta.salvar" value="<fmt:message key="pergunta.salvar"/>" title="Criar Pergunta" class="btn btn-primary" style="float: right"/>
        </div>
    </fieldset>
</form>



<script type="text/javascript">
(function($){
	$(document).ready(function() {
		var contalternativas = 0, maxalternativas = 5;
		var $grupoalternativas = $("#grupoalternativas"), $addalternativa = $("#addalternativa");
		
		var alternativa = '<div class="controls campoalternativa"><textarea class="input-xmlarge alternativa" rows="2" cols="" name="pergunta.alternativas[].textoAlternativa" style="resize: none;">${pergunta.texto}</textarea><a class="btn btn-danger delalternativa" href="#"><i class="icon-trash icon-white"></i></a></div>';
		
		$(".delalternativa").live(
			"click", function(e){
				e.preventDefault();
				if(contalternativas>1){
					$(this).parent().remove();
					contalternativas--;
				}
			}
		)
		
		
		$addalternativa.click(function(e){
			e.preventDefault();
			if(contalternativas<maxalternativas){
				$grupoalternativas.append(alternativa);
				contalternativas++;
			}
		})
		
		$("#respsub").click(function(){
			$grupoalternativas.show();
			$grupoalternativas.append(alternativa); 
			contalternativas++;
		})
		
		$("#respobj").click(function(){
			$grupoalternativas.hide();
			$grupoalternativas.children(".campoalternativa").remove();
			contalternativas=0;
		})
		
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
    
	});
})(jQuery);
</script>