<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript"src="${pageContext.request.contextPath}/jscripts/jquery.js"></script>
        
        <link rel='stylesheet' id='style-css'  href='${pageContext.request.contextPath}/plugin/diapo/diapo.css' type='text/css' media='all'>
        <!--[if !IE]><!--><script type='text/javascript' src='${pageContext.request.contextPath}/plugin/diapo/scripts/jquery.mobile-1.0rc2.customized.min.js'></script><!--<![endif]-->
		<script type='text/javascript' src='${pageContext.request.contextPath}/plugin/diapo/scripts/jquery.easing.1.3.js'></script> 
		<script type='text/javascript' src='${pageContext.request.contextPath}/plugin/diapo/scripts/jquery.hoverIntent.minified.js'></script> 
		<script type='text/javascript' src='${pageContext.request.contextPath}/plugin/diapo/scripts/diapo.js'></script> 

		<script>
		$(function(){
			$('.pix_diapo').diapo({'loaderColor':'white', 'time':'5000'});
		});
		</script>
		<style>
			.mini-titulo{
				margin: 0 auto;
				text-align: center;
				border-bottom: 1px solid #4A5E78;
				padding: 5px 0;
			}
			.mini-conteudo{
				text-indent: 20px;
				padding: 10px;
				text-align: justify;
			}
			
		</style>
		
    </head>
    <body> 
        
        <div class="span12">
        <!-- 
        	<div class="form-horizontal form-layout">
        		<h1 style="text-align: center; font-size: 48px; margin-top: 20px">UseSkill</h1>
        		<hr/>
        		<h3>?</h3>
        		<br/>
        		<p style="text-indent: 10px"></p>
        		<hr/>
        		<h3></h3>
        		<br/>
        		<p style="text-indent: 10px"></p>
        		<hr/>
        		<h3></h3>
        		<br/>
        		
        	</div>
        	-->
        	
        	<div class="pix_diapo">
        			<div data-thumb="${pageContext.request.contextPath}/img/diapo/thumbs/brain.jpg">
                        <img src="${pageContext.request.contextPath}/img/diapo/brain.jpg"> 
                        <div class="caption elemHover fromRight" style="bottom:65px; padding-bottom:5px; color:#64c5ff; text-transform:uppercase">
                            <span style="padding-bottom: 10px; font-size: 16px;">UseSkill - Ferramenta para Automação de Testes de Usabilidade Remotos</span>
                        </div>
                        <div class="caption elemHover fromLeft" style="padding-top:5px;">
                            Avalia mais do que a Eficiência e Eficácia... avalia também a Satisfação do usuário.
                        </div>
                    </div>
                    
                    
                    <div data-thumb="${pageContext.request.contextPath}/img/diapo/thumbs/video.jpg">
                        <iframe width="940" height="300" src="http://www.youtube.com/embed/tcVWMklFeMU?wmode=transparent&autoplay=1" data-fake="${pageContext.request.contextPath}/img/diapo/video.jpg" frameborder="0" allowfullscreen></iframe>
                    </div>
        	
                    <div data-thumb="${pageContext.request.contextPath}/img/diapo/thumbs/pointer.jpg">
                        <img src="${pageContext.request.contextPath}/img/diapo/pointer.jpg">
                        <div class="caption elemHover fromLeft">
                            Como o seus clientes realmente utilizam o seu Sistema? Cadastre-se e compare a utilização esperada com a real utilização do Sistema. 
                        </div>
                    </div>
                    
                    <div data-thumb="${pageContext.request.contextPath}/img/diapo/thumbs/ubique.jpg">
                        <img src="${pageContext.request.contextPath}/img/diapo/ubique.jpg">
                        <div class="caption elemHover fromLeft">
                            Sistemas Ubíquos - A UseSkill já está se preparando para essa nova era! Aguarde... 
                        </div>
                    </div>
               </div><!-- #pix_diapo -->
               
              <div class="row show-grid" style="margin: 70px auto; width: 940px; padding-left: 10px;">
              	<div class="span4 form-layout" style="height: 280px;">
					<h1 class="mini-titulo">O que é a UseSkill?</h1>
              		<p class="mini-conteudo">Ferramenta para automação de testes de usabilidade. Para utilizar esta ferramenta, cadastre-se no link do menu acima.</p>
				</div>
              	<div class="span4 form-layout" style="margin-left: 15px; height: 280px;">
    				<h1 class="mini-titulo">Como realizar um Teste de Usabilidade na UseSkill?</h1>
              		<p class="mini-conteudo">Ao acessar sua conta, crie um teste e defina tarefas e perguntas. Ao definir tarefas, selecione um usuário experiente para realizar a tarefa. Após isto, convide outros usuários da UseSkill para realizar o seu teste e aproveite todas as vantagens de um teste de usabilidade a um baixo custo.</p>
    			</div>
    			<div class="span4 form-layout" style="margin-left: 15px; height: 280px;">
              		<h1 class="mini-titulo">Quem Somos?</h1>
              		<p class="mini-conteudo">Pesquisadores na área de Engenharia de Software, especificamente em Testes de Usabilidade.</p>
              	</div>
              </div>
        </div>
    </body>
</html>