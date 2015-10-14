
//@ sourceURL=useskill.js

//realiza a insercao do html dentro da pagina
(function($){
	var url = chrome.extension.getURL("html/useskill.html");
	var html = $('html');
	var iframeId = 'USmySidebarEaSII';
	
	function loadHtmlContent(url) {
		var xmlhttp = new XMLHttpRequest(),
    	synchronous = false,
    	cache = false;
	    xmlhttp.onreadystatechange = function() {
	        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
	           if(xmlhttp.status == 200){
	        	   document.getElementById(iframeId).innerHTML += xmlhttp.responseText;
	        	   loadUseSkillMenu();
	           }else {
	        	   console.error(xmlhttp);
	           }
	        }
	    }
	    
	    xmlhttp.open("GET", url, synchronous);
	    xmlhttp.send();
	};
	
	if (document.getElementById(iframeId)) {
		//console.error('id: ' + iframeId + ' taken please dont use this id!');
	}else{
		html.append('<div id="'+iframeId+'"></div>');
		if(url!=undefined){
			loadHtmlContent(url);
		}
	}
	
	function loadUseSkillMenu(){
		console.info('loadUseSkillMenu!');
		/*Inserindo imagens ao css*/
		var urlImg = chrome.extension.getURL("images/glyphicons-halflings.png");
		var urlImg2 = chrome.extension.getURL("images/glyphicons-halflings-white.png");
		$('[class*="USicon-"]').css("background-image", 'url("'+urlImg+'")');
		$('.USicon-white').css("background-image", 'url("'+urlImg2+'")');

		var enumTypeActions = {
			PULAR:'pular',
			ADIAR:'adiar'
		}
		var enumTypeQualify = {
			GOOD:'true',
			BAD:'false'
		}

		//evitar plugin em perguntas e aprimoramentos na pagina de perguntas
		var urlPergunta = "teste/responder/pergunta";
		var urlAtual = location.protocol+location.host+location.pathname;
		if(urlAtual.indexOf(urlPergunta)!=-1){
			//fadeOutUS($('#USacoesSection'));
			//fadeOutUS($('#USroteiroSection'));
			fadeOutUS($('#USpluginChrome'));

			//"gambiarra" para corrigir id da pergunta nulo
			var count = localStorage.getItem("countRefresh");
			var idPerg = $('#pergid').val();
			if(!count){
				count = 0;
				localStorage.setItem("countRefresh", count);
			}
			if(count<4){
				if(!idPerg){
					localStorage.setItem("countRefresh", count+1);
					window.location.reload(true);
				}
			}else{
				if(idPerg){
					localStorage.setItem("countRefresh", 0);
				}else{
					console.error("Error: ID null;");
				}

			}
		}

		$(document).ready(function(){
			//ao iniciar, carregar as informacoes do teste atual
			var numElAtual, listaEl, elAtual, acoes, loadEl;
			chrome.extension.sendRequest({useskill: "getStorageAndAcoes"}, function(resp) {
				numElAtual = resp.storage.elementoAtualLista;
				listaEl = parseJSON(resp.storage.listaElementos);
				elAtual = listaEl[numElAtual];
				acoes = stringfyJSON(resp.acoes);
				
				//apos capturar as variaveis, setar texto em cima
				$(".USinfoPassos").html(numElAtual+1 +"/"+ listaEl.length);
				
				//alterar o botão de aguarde... para responder
				var btnResponderLabel = "Responder", bntResponder = $('#USperguntaTeste12z3');
				if(bntResponder){
					bntResponder.attr('title', btnResponderLabel).val(btnResponderLabel);
				}

				//apenas se for tarefa, aparece a barra lateral
				if(elAtual.tipo=="T"){
					getRoteiro(elAtual.id);
					//se n estiver openned, fecha
					if(!resp.isOpenned){
						hideUSleftMenu();
					}
					$('#USpluginChrome').removeClass('UShidden');
				}
			});

			//Funcoes para comunicacao com o background
			function getStorage(callback){			
				chrome.extension.sendRequest({useskill: "getStorage"}, function(response) {
					if (callback && typeof(callback) === "function") {
	        			callback.call(this, response);
	    			}
				});
			}
			function setIsOpenned(booleano){
				if(booleano!=null){
					chrome.extension.sendRequest({useskill: "setOpenned", isOpenned: booleano});
				}
			}
			function nextElement(){
				getStorage(function(dado){
					var prox = dado.dados.elementoAtualLista;
					prox++;
					chrome.extension.sendRequest({useskill: "nextElement", atual: prox});
				});
			}
			function getRoteiro(atual){
				chrome.extension.sendRequest({useskillserver: "getRoteiro", idTarefa: atual}, function(response) {
					if(response.dados.string){
						$('#USroteiro').html(response.dados.string);
					}else{
						showMessageError("Não foi possível receber o roteiro, tente novamente!");
					}
				});
			}
			function concluirTarefa(atual){
				chrome.extension.sendRequest({useskillserver: "concluirTarefa", idTarefa: atual}, function(response) {			
					if(response.success){
						nextElement();
					}else{
						//console.log(response.errorType)
						showMessageError("Não foi possível concluir a tarefa, novamente!");
						console.log("erro concluir - not success");
					}
				});
			}
			function pularTarefa(atual, justificativa){
				chrome.extension.sendRequest({useskillserver: "pularTarefa", idTarefa: atual, justificativa: justificativa}, function(response) {	
					if(response.success){
						nextElement();
						acoesReset();
					}else{
						//console.log(response.errorType)
						showMessageError("Não foi possível pular a tarefa, tente novamente!");
						console.log("erro pular - not success");
					}
				});
			}
			function adiarTeste(justificativa){
				chrome.extension.sendRequest({useskill: "testFinish", justificativa: justificativa}, function(response) {	
					if(response.success){
						nextElement();
						acoesReset();
					}else{
						//console.log(response.errorType)
						showMessageError("Não foi possível adiar o teste, tente novamente!");
						console.log("erro pular - not success");
					}
				});
			}
			function enviarComentario(atual, texto, quali){
				chrome.extension.sendRequest({useskillserver: "enviarComentario", idTarefa: atual, texto: texto, quali: quali}, function(response) {	
					if(response.success){
						//nextElement();
						comentarioReset();
						showMessageSuccess("Comentário enviado com sucesso, obrigado por contribuir!");
					}else{
						//console.log(response.errorType)
						showMessageError("Não foi possível enviar o comentário, tente novamente!");
						console.log("erro comentar - not success");
					}
				});
			}
			function responderPergunta(atual, resposta, url){
				chrome.extension.sendRequest({useskillserver: "responderPergunta", idPergunta: atual, resposta: resposta, url: url}, function(response) {							
					if(response.success){
						nextElement();
					}else{
						//console.log(response.errorType)
						$('.USAlertError').html('Não foi possível responder. Tente Novamente!');
						$('.alert').fadeIn(300);
						//alertUS("Tente novamente!");
						console.log("erro responderPergunta - not success");
					}
				});
			}
			
			//Eventos JQUERY
			$('#USbuttonResize').on('click', function(e){
				e.preventDefault();
				toogleUSleftMenu();
			});
			$('#USleftmenuMinimize').on('click', function(e){
				e.preventDefault();
				toogleUSleftMenu();
			});
			$('#USroteiroRefresh').on('click', function(e){
				e.preventDefault();
				getRoteiro(elAtual.id);
			});
			//Acoes
			$('#USconcluirTarefa').on('click', function(e){
				e.preventDefault();
				var $this = $(this);

				var clicked = $this.attr("clicked");
				if(!clicked){
					$('#USconcluirTarefaText').html('Enviando dados...').attr("clicked", "true");
					concluirTarefa(elAtual.id);
				}			
			});
			$('#USadiarTeste').on('click', function(e){
				e.preventDefault();
				//showUSjustificativa(this, enumTypeActions.ADIAR);
				var msg = $('#USjustificativaAcao').val();
				var r = confirm("Deseja adiar este teste? Os dados armazenado até o momento serão perdidos!");
				if (r==true){
					adiarTeste(msg);
				}
			});
			$('#USpularTarefa').on('click', function(e){
				e.preventDefault();
				showUSjustificativa(this, enumTypeActions.PULAR);
			});
			$('.UScancelarAcao').on('click', function(e){
				e.preventDefault();
				hideUSjustificativa(this);
				acoesReset();
			});
			$('#USacoesProsseguir').on('click', function(e){
				var msg = $('#USjustificativaAcao').val();			
				if(msg!=null && msg!=""){
					e.preventDefault();
					pularTarefa(elAtual.id, msg);
				}else{
					showMessageError("Preencha a justificativa!")
				}
			});
			//Comentarios
			$('#USrealizarComentario').on('click', function(e){
				e.preventDefault();
				fadeOutUS($('#UScomentarioSubSection'));
				fadeInUS($('#UScomentarioEtapa1'));
			});
			$('.UScancelarComentario').on('click', function(e){
				e.preventDefault();
				comentarioReset();
			});
			$('.UScomentarioQualifique').on('click', function(e){
				e.preventDefault();
				var quali = $(this).attr('data-type');
				console.log(quali);
				if(quali != null){
					var msg = $('#UScomentarioTexto').val();
					console.log(msg);
					if(msg!=null && msg!=""){
						e.preventDefault();
						enviarComentario(elAtual.id, msg, quali);
					}else{
						showMessageError("Preencha seu Comentário!");
					}
				}else{
					showMessageError('Tente novamente!');
				}
			});
			
			//Questionario
			//metodo para responder questionario
			$('#USperguntaTeste12z3').click(function(e){
				e.preventDefault();
				var $form = $(e.target).closest(".form-pergunta"),
					pergId = $("#pergid").val(),
					respType = $form.attr('data-perg-type'),
					resposta = "", 
					respostaVefiry = "";
				
				//separar resposta
				if(respType == 'alternativa'){
					var $checked = $('input:checked');
					if($checked.length!=0){
						resposta = $checked.val();
						respostaVefiry = $checked.val();
					}
				}else{
					resposta = new String($('textarea').val());
					respostaVefiry = resposta.replace(" ","");
					respostaVefiry = resposta.replace("\n","");
				}
				
				if(respostaVefiry!=""){
					console.log($form, pergId, resposta, respType);
					if(respType){
						responderPergunta(pergId, resposta, '/teste/salvar/resposta/' + respType);
					}else{
						throw "Url problem: " + respType + ", indexOf: " + index;
					}
					
				}else{
					$('.USAlertError').html('Preencha a resposta antes de enviar!');
					$('.alert').fadeIn(300);
				}
			});


			//Animacoes JQUERY
			function toogleUSleftMenu(){
				var $this = $('#USleftMenu');
				
				if($this.css('left')=="0px"){
					setIsOpenned(false);
					$('#USleftMenu').animate({
					    left: '-='+$this.width()
					});
					$('#USbuttonResize').animate({
					    left: '-='+$this.width()
					});
				}else{
					setIsOpenned(true);
					$('#USleftMenu').animate({
					    left: '+='+$this.width()
					});
					$('#USbuttonResize').animate({
					    left: '+='+$this.width()
					});
				}
			}
			function hideUSleftMenu(){
				setIsOpenned(false);
				var w = $('#USleftMenu').width();
				$('#USleftMenu').animate({
				    left: '-='+w
				}, 0);
				$('#USbuttonResize').animate({
				    left: '-='+w
				}, 0);
			}
			function showUSjustificativa(elem, type){
				var $just = $('.USjustificativa').eq(0);
				fadeOutUS($(elem).parent());
				fadeInUS($just);
				$just.find("h2 span").text($(elem).text());
			}
			function hideUSjustificativa(elem){
				var $just = $('.USjustificativa').eq(0);
				var $sec = $(elem).closest('.USsection');
				fadeInUS($sec.find('.UShidden'));
				fadeOutUS($just);
				$just.find("h2 span").text(' ');
			}
			function comentarioReset(){
				fadeOutUS($('#UScomentarioEtapa1'));
				fadeInUS($('#UScomentarioSubSection'));
				$('#UScomentarioTexto').val("");
			}
			function acoesReset(){
				$('#USjustificativaAcao').val("");
			}
			
			function showMessageSuccess(msg){
				alertUS(msg);
			}
			function showMessageInfo(msg){
				alertUS(msg);
			}
			function showMessageError(msg){
				alertUS(msg);
			}
		});

		/*converter json to object*/
		function parseJSON(data) {
	    	return window.JSON && window.JSON.parse ? window.JSON.parse( data ) : (new Function("return " + data))(); 
		}
		function stringfyJSON(data){
			return window.JSON && window.JSON.stringify ? window.JSON.stringify(data) : (new Function("return " + data))();
		}
		function alertUS(msg){
			window.alert(msg);
		}
		function fadeInUS($elem){
			$elem.hide().removeClass('UShidden').fadeIn(400);
		}
		function fadeOutUS($elem){
			$elem.addClass('UShidden').fadeOut(400);
		}
	}
	
})(jQuery);