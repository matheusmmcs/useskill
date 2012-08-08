<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<!-- decorator = teste -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webclient/loadaction.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webclient/preloader.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.css" media="screen" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/capt.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.js"></script>
		<script type="text/javascript" language="javascript">
		var rotate = 1;
		function UShide_preloader() {
			rotate = 0;
			jQuery("#USpreloader").fadeOut(1000);
			var str = $('.USconcluir').eq(0).attr('class');
			jQuery('.USconcluir').eq(0).attr('class', str.replace("USdisabled","")).attr('href','${pageContext.request.contextPath}/tarefa/loadtaskuser');
		}
		function USinit_preloader(){
			// calculate height 
			var screen_ht = $(window).height();
			var preloader_ht = 10;
			var padding =(screen_ht/2)-preloader_ht;

			$("#USpreloader").css("padding-top",padding+"px");

			// loading animation using script
			function anim(){ 
				$("#USpreloader_image").animate({left:'-1400px'}, 8000,
				function(){ 
					$("#USpreloader_image").animate({left:'0px'}, 5000); 
					if(rotate==1){ 
						anim();
					}
				});
			}
			anim();
		}

		(function($) {			
			$(document).ready(function(){
				//iniciar fancybox e o plugin capt
				$('.fancybox').fancybox();
				$.webclientCapt({'url': "${pageContext.request.contextPath}/tarefa/save/fluxo/usuario"});
		
				$('#roteiro').click(function(e){
					$.ajax({
							url : "${pageContext.request.contextPath}/tarefa/roteiro",
							type : 'POST',
							dataType : 'json',
							async : false,
							data : {
								'idTarefa' : get_url_parameter("idTarefa")
								},
								success : function(json) {
									console.log("SUCCESS");
									$('#USroteiro').children('p').html(json.string);
									},
									error : function(jqXHR, textStatus, errorThrown){
										console.log("ERRO");
										}
									});
					});
		
				function get_url_parameter(param) {
					param = param.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
					var r1 = "[\\?&]" + param + "=([^&#]*)";
					var r2 = new RegExp(r1);
					var r3 = r2.exec(window.location.href);
					if (r3 == null)
						return "";
					else
						return r3[1];
				}
			});
		})(jQuery);
		</script>
	<decorator:head />
	</head>
	<body onload="UShide_preloader();">
		<!-- TOPO PRELOADER -->
		<div id="USpreloader">
			<fmt:message key="testes.aguardepagina"/>
			<div>
				<img src="${pageContext.request.contextPath}/img/loading.jpg" id="USpreloader_image" />
			</div>
		</div>
		<script>USinit_preloader();</script>

		<!-- ROTEIRO -->
		<div id="USroteiro">
			<h3>Roteiro</h3>
			<p></p>
		</div>
		<!-- TOPO INSERIDO -->
		<div id="USwebclient-topo-todo">
			<div id="USwebclient-topo">
			<div class="USdireita">
				<a class="USbotao USbotao-verde USconcluir USdisabled" id="concluir12qz3" href="#">
					<fmt:message key="concluirTarefa" /> </a>
			</div>
			<div class="UScentro" id="roteiroContent">
				<a class="USbotao fancybox" id="roteiro" href="#USroteiro">Roteiro</a>
			</div>
			<div class="USesquerda">
				<a class="USbotao USbotao-info">Bom</a> 
				<a class="USbotao USbotao-vermelho">Ruim</a>
				<a class="USbotao"><fmt:message key="comentario" /> </a>
			</div>
			</div>
		</div>		
		
		<!-- CONTEUDO DA PAGINA -->
		<div id="USwebclient-conteudo">
			<decorator:body />
		</div>
	</body>
</html>