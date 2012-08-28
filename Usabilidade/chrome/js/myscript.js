/* Action that happens when you load the page */
window.addEventListener('load', function() {

	var config = {
		'url': "/Usabilidade/tarefa/save/fluxo/ideal",
		'type': 'POST',
		'finish': "concluir12qz3"
	};

	/* Create action class */
	function Action(action, time, url, content, tag, tagId, tagClass, tagName, tagValue, tagType, posX, posY) {
		this.sActionType = action;
		this.sTime = time;
		this.sUrl = url;
		this.sContent = String(content).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').substr(0, 200);
		this.sTag = tag;
		this.sTagId = tagId;
		this.sTagClass = tagClass;
		this.sTagName = tagName;
		this.sTagValue = tagValue;
		this.sTagType = tagType;
		this.sPosX = posX;
		this.sPosY = posY;
	}

	/* Variables definitions */
	var initialTime = new Date().getTime();
	var actions = new Array();
	
	/* Change the page listeners */
	(function(capt) {
		capt(document).bind({
			click : function(e) {
				console.log(".bind(), click ok");
				console.log(capt(e.target).html());
				capt(e.target).css("background","red");
			}, mousemove : function(e) {						
				capt(".1").text("Página (X,Y) - " + e.pageX + " - " + e.pageY);
				capt(".2").text("Tela (X,Y)   - " + e.clientX + " - " + e.clientY);						
			}, focusout : function(e) {
				var tagName = e.target.tagName
				tagName = tagName.toUpperCase()
				if (tagName == "INPUT") {
					var tempo = getDiferencaTempo()
					actions.push(new Action("focusout", tempo, document.location.href,
												capt(e.target).html(),
												tagName,
												retira_(capt(e.target).attr("id")),
												retira_(capt(e.target).attr("class")),
												retira_(capt(e.target).attr("name")),
												retira_(capt(e.target).attr("value")),
												retira_(capt(e.target).attr("type")),
												e.pageX,
												e.pageY))
					console.log(actions);
				}
			}
		});
		
		/*
		 * Método para percorrer o html passado, observando
		 * a qual linha/coluna o objeto pertence
		 */
		function percorreHTMLTable(html) {
			var contTR = 0
			var contTD = 0
			var matches

			// remover espaços indesejados
			html = html.replace(/(\s|\n|\t|\f|\r)/g, '')

			// expressão regular para encontrar até o
			// itemClicadoEaSII
			var regex = /(.*)itemClicadoEaSII/g
			var input = html
			var retorno = ""
			if (regex.test(input)) {
				matches = input.match(regex);
				for ( var match1 in matches) {
					retorno += matches[match1]
				}
			}

			// expressão regular para encontrar o <tr>
			regex = /(\<tr\>)/g
			input = retorno
			if (regex.test(input)) {
				matches = input.match(regex);
				for ( var match2 in matches) {
					contTR++;
				}
			}

			regex = /(trClicadoEaSII.*)/g
			input = retorno
			retorno = ""
			if (regex.test(input)) {
				matches = input.match(regex)
				for ( var match3 in matches) {
					retorno += matches[match3]
				}
			}

			regex = /(\<td\>)/g;
			input = retorno;
			if (regex.test(input)) {
				matches = input.match(regex);
				for ( var match4 in matches) {
					contTD++;
				}
			}
			return contTR + ":" + contTD
		}// end
		
		function htmlEntities(str) {
			return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
		}// end

		function retira_(obj) {
			if (obj == undefined) {
				obj = "_";
			}
			return obj;
		}// end

		function getResultadoJson() {
			return JSON.stringify(Acoes)
		}// end

		function getDiferencaTempo() {
			var tempo = new Date().getTime();
			var dif = tempo - initialTime;
			var seg = 1000;// var min = seg*60;
			return dif / seg;
		}// end
		
		function enviaJson(dados, bool, nextUrl) {
			capt.ajax({
				url : config.url,
				type : config.type,
				dataType : 'json',
				async : false,
				data : {
					'dados' : dados,
					'completo' : bool,
					'tarefaId' : get_url_parameter("idTarefa")
				},
				success : function(json) {
					//alert("S: "+bool)
					if(bool){
						console.log("SUCCESS")
						console.log(json)
						window.location = nextUrl;
						window.parent.location = nextUrl;
					}
				},
				error : function(e) {
					if(bool){
						console.log("ERROR")
						console.log(e)
						window.location = nextUrl;
						window.parent.location = nextUrl;
					}
				},
				statusCode : {
					404 : function() {
						console.log('page not found');
					}
				}
			});
		}

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
		
	})(jQuery);
});

window.addEventListener('click', function(e) {
	var tag = "";
	tag += "User action: click\n";
	tag += e.target.toString() + "\n";
	tag += "id: " + e.target.id + "\n";
	tag += "class: " + e.target.className + "\n";
	tag += "tagName: " + e.target.tagName + "\n";	
	(function($){
		tag += "HTML: " + $(e.target).html();
	})(jQuery);
	//alert(tag);
});

var begin = false;

document.addEventListener('DOMContentLoaded', function () {
	if(begin){
		$("#stop").attr("class", "app");
		chrome.browserAction.setBadgeText({text: "ON"});
	}else{
		chrome.browserAction.setBadgeText({text: "OFF"});
	}

	/* add start event listener */
	var start = document.getElementById("start");
	start.addEventListener('click', function(){
		if(!begin){
			$("#start_img").attr("src", "images/img_exclamacao.jpg");
			$("#start").attr("class", "app app_disabled");
			$("#stop").attr("class", "app");	
			begin = true;
			chrome.browserAction.setBadgeText({text: "ON"});
		}
	});
	
	
	/* add stop event listener */
	var stop = document.getElementById("stop");
	stop.addEventListener('click', function(){
		if(begin){
			$("#start_img").attr("src", "images/green_globe_exclamation.jpg");
			$("#start").attr("class", "app");
			$("#stop").attr("class", "app app_disabled");
			begin = false;
			chrome.browserAction.setBadgeText({text: "OFF"});
		}
	});
	
	
	/* add send event listener */
	var send = document.getElementById("send");
	send.addEventListener('click', function(){
		
		/* Use web service to send a request to MySearch tool */
		
		/* Create a simple text notification (example): */
		var notification = webkitNotifications.createNotification(
		  'images/icon.png',  // icon url - can be relative
		  'Extension!',  // notification title
		  'Send Notification...'  // notification body text
		);
		/* Or create an HTML notification:
		var notification = webkitNotifications.createHTMLNotification(
		  'notification.html'  // html url - can be relative
		);
		*/
		// Then show the notification.
		notification.show();
		
	});
});