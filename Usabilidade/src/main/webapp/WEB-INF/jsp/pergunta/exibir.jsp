<div class="span7 center pergunta UseSkill-nocapt" style="margin-top: 70px">
	<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
		</div>
	</c:if>
	

	<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
		<div class="form-horizontal form-layout form-pergunta" data-action="${pageContext.request.contextPath}/teste/salvar/resposta/escrita">
			<fieldset>
				<legend>
					<span>
						<fmt:message key="questionario" />
					</span>
					<span style="font-size: 14px; float: right;">
						Tarefa do Teste - <span class="USinfoPassos" style="padding-left: 3px;"></span>
					</span>
					<hr />
				</legend>
				
				<div class="alert alert-error hide">
            		<button type="button" class="close" data-dismiss="alert">x</button>
            		<div class="USAlertError">
            	
            		</div>
          		</div>
				
				<input id="pergid" type="hidden" name="pergid" value="${pergunta.id}" />
				
				<div class="control-group notop">
					<div><b><fmt:message key="testeparticipar.pergunta" />:</b> ${pergunta.texto}</div>
				</div>

				<div class="control-group">
                	<div class="span6 center">
                		<textarea rows="6" cols="10" name="resposta" class="span6 center" style="resize:none"></textarea>                    	
                	</div>
            	</div>
				
				<div class="control-group">
					
				</div>
				
				<div class="form-actions">
					<input id="USperguntaTeste12z3" type="submit" value="<fmt:message key="responde.loading"/>" name="salvar" title="<fmt:message key="responde.loading"/>" class="btn btn-primary" style="float: right; width: 120px" />
				</div>
			</fieldset>
		</div>
	</c:if>

	<c:if test="${pergunta.tipoRespostaAlternativa==true}">
	<div class="form-horizontal form-layout form-pergunta" data-action="${pageContext.request.contextPath}/teste/salvar/resposta/alternativa">
		<fieldset>
			<legend>
					<span>
						<fmt:message key="questionario" />
					</span>
					<span style="font-size: 14px; float: right;">
						Tarefa do Teste - <span class="USinfoPassos" style="padding-left: 3px;"></span>
					</span>
					<hr />
			</legend>
			
			<div class="alert alert-error hide">
            	<button type="button" class="close" data-dismiss="alert">x</button>
            	<div class="USAlertError">
            	
            	</div>
          	</div>
			
			<input id="pergid" type="hidden" name="pergid" value="${pergunta.id}" />

			<div class="control-group notop">
				<div><b><fmt:message key="testeparticipar.pergunta" />:</b> ${pergunta.texto}</div>
			</div>

			<div class="control-group">
            	<div>
            	<c:forEach items="${pergunta.alternativas}" var="alternativa">
					<label class="radio">
						<input type="radio" name="alternativa.id" value="${alternativa.id}" />${alternativa.textoAlternativa}
					</label>
				</c:forEach>
            	</div>
          	</div>
			
			<div class="form-actions">
				<input id="USperguntaTeste12z3" type="submit" value="<fmt:message key="responde.loading"/>" name="salvar" title="<fmt:message key="responde.loading"/>" class="btn btn-primary" style="float: right; width: 120px" />
			</div>
		</fieldset>
	</div>
	</c:if>
	<script>
		(function($){
			$(document).on("click", '.close', function(e){
				e.preventDefault();
				$(this).parent().fadeOut(300);
			})
		})(jQuery);
	</script>
</div>