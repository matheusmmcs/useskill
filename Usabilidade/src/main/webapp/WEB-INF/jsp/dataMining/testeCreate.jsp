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
			<a href="${pageContext.request.contextPath}/datamining/">
				DataMining
			</a> 
			<span class="divider">/</span>
		</li>
		
    	<li class="active">Inserir Teste</li>
	</ul>

	<form action="${pageContext.request.contextPath}/datamining/testes/salvar" method="post" class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span>Inserir Teste</span>
				<hr/>
			</legend>
			
			<div class="control-group">
            	<label class="control-label" for="testTitle">Titulo*</label>
            	<div class="controls">
                	<input type="text" value="${teste.nome}" name="test.title"  id="testTitle" class="span6"/> 
            	</div>
        	</div>
        	
        	<div class="control-group">
            	<label class="control-label" for="testClientAbbreviation">Abreviacao do Cliente*</label>
            	<div class="controls">
                	<input type="text" value="${teste.nome}" name="test.clientAbbreviation"  id="testClientAbbreviation" class="span6"/> 
            	</div>
        	</div>
        	
        	<div class="control-group">
            	<label class="control-label" for="testUrlSystem">URL do sistema*</label>
            	<div class="controls">
                	<input type="text" value="${teste.limiar}" name="test.urlSystem"  id="testUrlSystem" class="span6"/> 
            	</div>
        	</div>

			<hr/>


        	<div class="form-actions">
            	<input type="submit" value="<fmt:message key="tarefa.criar" />" 
            		name="criarTarefa" title="<fmt:message key="tarefa.criar" />" 
            		class="btn btn-primary" style="float: right; margin-right: 60px"/>
        	</div>
		</fieldset>
	</form>
</div>

<script>
(function($) {
	
	
	
	/* 
		<div id="acoesiniciais" class="alternativa-group">
				<label class="control-label" for="input01">
					Acoes Iniciais*
					<br/> 
					<a id="addacao" class="btn" href="#">
						<i class="icon-plus"></i> 
					</a> 
				</label>
			</div>
			
			<hr/>
			
			<div id="acoesfinais" class="alternativa-group">
				<label class="control-label" for="input01">
					Acoes Finais*
					<br/> 
					<a id="addacao" class="btn" href="#">
						<i class="icon-plus"></i> 
					</a> 
				</label>
			</div>
			
		$(document).ready(
		function() {
			
			var $grupoacoesinicias = $("#acoesiniciais"),
				$grupoacoesfinais = $("#acoesfinais"),
				$addacao = $("#addacao"),
				$delacao = $(".delacao");

			$addacao.live('click', function(e) {
				e.preventDefault();
				console.log($(this).closest($grupoacoesinicias));
				if($(this).closest($grupoacoesinicias).length > 0){
					$grupoacoesinicias.append(constructAcao('acoesIniciais'));
				}else{
					$grupoacoesfinais.append(constructAcao('acoesFinais'));
				}
			});
			
			$delacao.live("click", function(e) {
				e.preventDefault();
				$(this).parent().remove();
			});
			
			function constructAcao(attr){
				var obj = 'teste.'+attr+'[]';
				return 	'<div class="controls campoacao">'+
						'<input type="text" class="span6 acao" name="'+obj+'.pageView" placeholder="PageView"/>'+
						'<input type="text" class="span6 acao" name="'+obj+'.element" placeholder="Element"/>'+
						'<input type="text" class="span6 acao" name="'+obj+'.action" placeholder="Action"/>'+
						'<a class="btn btn-primary delacao" href="#"><i class="icon-trash icon-white"></i></a></div>';
			}
		}); */
})(jQuery);

</script>