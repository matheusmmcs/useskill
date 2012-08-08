<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<!-- decorator = teste -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webclient/loadaction.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webclient/preloader.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.css" media="screen" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.js"></script>
		<script type="text/javascript" language="javascript">
			rotate = 1;
			function UShide_preloader() {
				rotate = 0;
				$("#USpreloader").fadeOut(1000);
			}

			$(document).ready(function(){
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
			});
		</script>
	<decorator:head />
	</head>
	<body onload="UShide_preloader();">
		<%@include file="headerscriptUser.jsp"%>
		<div id="USpreloader">
			<fmt:message key="testes.aguardepagina"/>
			<div>
				<img src="${pageContext.request.contextPath}/img/loading.jpg" id="USpreloader_image" />
			</div>
		</div>
		<div id="webclient-conteudo">
			<decorator:body />
		</div>
	</body>
</html>