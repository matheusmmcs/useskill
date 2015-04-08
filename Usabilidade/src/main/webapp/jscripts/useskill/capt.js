
//BACKFIX
var bajb_backdetect={Version:'1.0.0',Description:'Back Button Detection',Browser:{IE:!!(window.attachEvent&&!window.opera),Safari:navigator.userAgent.indexOf('Apple')>-1,Opera:!!window.opera},FrameLoaded:0,FrameTry:0,FrameTimeout:null,OnBack:function(){console.log('Back Button Clicked')},BAJBFrame:function(){var BAJBOnBack=document.getElementById('BAJBOnBack');if(bajb_backdetect.FrameLoaded>1){if(bajb_backdetect.FrameLoaded==2){bajb_backdetect.OnBack();history.back()}}bajb_backdetect.FrameLoaded++;if(bajb_backdetect.FrameLoaded==1){if(bajb_backdetect.Browser.IE){bajb_backdetect.SetupFrames()}else{bajb_backdetect.FrameTimeout=setTimeout("bajb_backdetect.SetupFrames();",700)}}},SetupFrames:function(){clearTimeout(bajb_backdetect.FrameTimeout);var BBiFrame=document.getElementById('BAJBOnBack');var checkVar=BBiFrame.src.substr(-11,11);if(bajb_backdetect.FrameLoaded==1&&checkVar!="HistoryLoad"){BBiFrame.src="blank.html?HistoryLoad"}else{if(bajb_backdetect.FrameTry<2&&checkVar!="HistoryLoad"){bajb_backdetect.FrameTry++;bajb_backdetect.FrameTimeout=setTimeout("bajb_backdetect.SetupFrames();",700)}}},SafariHash:'false',Safari:function(){if(bajb_backdetect.SafariHash=='false'){if(window.location.hash=='#b'){bajb_backdetect.SafariHash='true'}else{window.location.hash='#b'}setTimeout("bajb_backdetect.Safari();",100)}else if(bajb_backdetect.SafariHash=='true'){if(window.location.hash==''){bajb_backdetect.SafariHash='back';bajb_backdetect.OnBack();history.back()}else{setTimeout("bajb_backdetect.Safari();",100)}}},Initialise:function(){if(bajb_backdetect.Browser.Safari){setTimeout("bajb_backdetect.Safari();",600)}else{document.write('<iframe src="blank.html" style="display:none;" id="BAJBOnBack" onunload="alert(\'de\')" onload="bajb_backdetect.BAJBFrame();"></iframe>')}}};bajb_backdetect.Initialise();

/*! tocca v0.1.4 || Gianluca Guarini */
//Mobile events
if(!window.initTocca){
	!function(a,b){"use strict";if("function"!=typeof a.createEvent)return!1;var c,d,e,f,g,h,i,j="undefined"!=typeof jQuery,k=!!navigator.pointerEnabled||navigator.msPointerEnabled,l=!!("ontouchstart"in b)&&navigator.userAgent.indexOf("PhantomJS")<0||k,m=function(a){var b=a.toLowerCase(),c="MS"+a;return navigator.msPointerEnabled?c:b},n={touchstart:m("PointerDown")+" touchstart",touchend:m("PointerUp")+" touchend",touchmove:m("PointerMove")+" touchmove"},o=function(a,b,c){for(var d=b.split(" "),e=d.length;e--;)a.addEventListener(d[e],c,!1)},p=function(a){return a.targetTouches?a.targetTouches[0]:a},q=function(){return(new Date).getTime()},r=function(b,e,f,g){var h=a.createEvent("Event");if(g=g||{},g.x=c,g.y=d,g.distance=g.distance,j)jQuery(b).trigger(e,g);else{h.originalEvent=f;for(var i in g)h[i]=g[i];h.initEvent(e,!0,!0),b.dispatchEvent(h)}},s=function(a){var b=p(a);e=c=b.pageX,f=d=b.pageY,h=q(),A++},t=function(a){var b=[],j=f-d,k=e-c;if(clearTimeout(g),-v>=k&&b.push("swiperight"),k>=v&&b.push("swipeleft"),-v>=j&&b.push("swipedown"),j>=v&&b.push("swipeup"),b.length)for(var l=0;l<b.length;l++){var m=b[l];r(a.target,m,a,{distance:{x:Math.abs(k),y:Math.abs(j)}})}else h+w-q()>=0&&e>=c-y&&c+y>=e&&f>=d-y&&d+y>=f&&(r(a.target,2===A&&i===a.target?"dbltap":"tap",a),i=a.target),g=setTimeout(function(){A=0},x)},u=function(a){var b=p(a);c=b.pageX,d=b.pageY},v=b.SWIPE_THRESHOLD||100,w=b.TAP_THRESHOLD||150,x=b.DBL_TAP_THRESHOLD||200,y=b.TAP_PRECISION/2||30,z=b.JUST_ON_TOUCH_DEVICES||l,A=0;o(a,n.touchstart+(z?"":" mousedown"),s),o(a,n.touchend+(z?"":" mouseup"),t),o(a,n.touchmove+(z?"":" mousemove"),u)}(document,window);
	window.initTocca = true;
}

/*
Melhorias:
- Capturar eventos de rolagem de barra -> content = posiçao no eixo y;
- Capturar eventos referentes a retorno JSON ou alteração no DOM (inserção/remoção de nós);

Funcionamento
- Botao voltar: MUDOU HASH; BACK; SAINDO...; LOAD;
- F5 ou typed com hash: SAINDO...; LOAD;
- Mudou hash: SAINDO...; LOAD; MUDOU HASH;
*/

function useskill_capt_onthefly(obj){

	if(!obj){
		obj = {};
	}
	
	//"http://localhost:3000/actions/create";
	//"http://96.126.116.159:3000/actions/create"

	//PARAMS
	var URL = obj.url ? obj.url : "",
		CLIENT = obj.client ? obj.client : "",
		VERSION = obj.version ? obj.version : 0,
		USERNAME = obj.username ? obj.username : "",
		ROLE = obj.role ? obj.role : "",
		SEND_MESSAGES = obj.sendactions ? obj.sendactions : false,
		CONNECTED_ON_THE_FLY = obj.onthefly ? obj.onthefly : false,
		CONNECTED_ON_JHEAT = obj.jheat ? obj.jheat : false,
		CONNECTED_PLUGIN = obj.plugin ? obj.plugin : false;

	//VARIABLES
	var TIME_SUBMIT_SEG = 300,
		TIME_SUBMIT = TIME_SUBMIT_SEG * 1000,
		sending = false, ultimaAcao = null,
		lastMouseMove = 0, lastMouseX, lastMouseY, mouseOffSet = 5;
	
	//MOBILE VERIFICATION
	var isMobileType = {
	    Android: function() {
	        return /Android/i.test(navigator.userAgent);
	    },
	    BlackBerry: function() {
	        return /BlackBerry/i.test(navigator.userAgent);
	    },
	    iOS: function() {
	        return /iPhone|iPad|iPod/i.test(navigator.userAgent);
	    },
	    Windows: function() {
	        return /IEMobile/i.test(navigator.userAgent);
	    },
	    any: function() {
	        return (isMobileType.Android() || isMobileType.BlackBerry() || isMobileType.iOS() || isMobileType.Windows());
	    }
	};
	function isMobile() {
		return isMobileType.any();
	}
	
	//ENVIO DE EVENTOS
	if(SEND_MESSAGES){
		var interval = setInterval(function(){
			sendAcoes();
		}, TIME_SUBMIT);

		window.onbeforeunload = function(e) {
			sendAcoes();
		};
	}
	function sendAcoes(){
		if(SEND_MESSAGES){
			var acoesString = getAcoesString();
			if(acoesString && !sending){
				sending = true;

			    var request = new XMLHttpRequest();
				request.open('POST', URL, false);
				request.send("acoes="+acoesString);

				if (request.status === 200) {
					logg("Send Actions SUCESS: ", request);
	            	clearStorage();
	            	sending = false;
				} else {
					logg("Send Actions ERROR: ", request);
		            sending = false;
				}
			}
		}
	}

	//ENUM DE TIPOS DE ACOES
	var actionCapt = {
			CLICK : "click",
			DBLCLICK : "dblclick", 
			FOCUSOUT: "focusout",
			MOUSEOVER: "mouseover",

			TAP: "tap",
			DBLTAP: "dbltap",
			SWIPEUP: "swipeup",
			SWIPEDOWN: "swipedown",
			SWIPELEFT: "swipeleft",
			SWIPERIGHT: "swiperight",
			ORIENTARION: "orientationchage",

			BROWSER_BACK : "back",
			BROWSER_FORM_SUBMIT : "form_submit",
			BROWSER_ONLOAD : "onload",
			BROWSER_RELOAD : "reload",
			BROWSER_CLOSE : "close",
			
			ROTEIRO : "roteiro",
			CONCLUIR : "concluir",
			COMENTARIO : "comentario",
			PULAR : "pular"
	};

	//CONSTRUTOR DE ACOES
	function Action(action, time, url, content, tag, tagIndex, id, classe, name, xPath, posX, posY, viewportX, viewportY, useragent) {
		this.sActionType = action;
		this.sTime = time;
		this.sUrl = url;
		this.sContent = String(content).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
		this.sContent = this.sContent.substr(0, 200);
		this.sTag = tag;
		this.sTagIndex = tagIndex;
		this.sId = id;
		this.sClass = classe;
		this.sName = name;
		this.sXPath = xPath;
		this.sPosX = posX;
		this.sPosY = posY;
		this.sViewportX = viewportX;
		this.sViewportY = viewportY;
		this.sUserAgent = useragent;

		this.sRealTime = new Date().getTime();
		this.sTimezoneOffset = new Date().getTimezoneOffset();

		if(CONNECTED_ON_THE_FLY){
			this.sUsername = USERNAME;
			this.sRole = ROLE;
			
			this.sClient = CLIENT;
			this.sVersion = VERSION;
		}
		
		if(CONNECTED_ON_JHEAT){
			//jHeath info
			this.sJhm = getJhmName();
			this.sActionJhm = getActionJhm();
			this.sSectionJhm = getSectionJhm();
			this.sStepJhm = getStepJhm();
		}
	}

	//LISTENERS DE EVENTOS
	if(isMobile()){
		document.addEventListener('tap', function(e){
			insertNewAcao(e, actionCapt.TAP);
		});
		// document.addEventListener('dbltap', function(e){
		// 	insertNewAcao(e, actionCapt.DBLTAP);
		// });
		document.addEventListener('swipeup', function(e){
			insertNewAcao(e, actionCapt.SWIPEUP);
		});
		document.addEventListener('swipedown', function(e){
			insertNewAcao(e, actionCapt.SWIPEDOWN);
		});
		document.addEventListener('swipeleft', function(e){
			insertNewAcao(e, actionCapt.SWIPELEFT);
		});
		document.addEventListener('swiperight', function(e){
			insertNewAcao(e, actionCapt.SWIPERIGHT);
		});
		window.addEventListener("orientationchange", function() {
			insertNewAcao(null, actionCapt.ORIENTARION);
		}, false);
	}else{
		document.addEventListener('click', function(e){
			insertNewAcao(e, actionCapt.CLICK);
		});
		document.addEventListener('dblclick', function(e){
			insertNewAcao(e, actionCapt.DBLCLICK);
		});
		document.addEventListener("mousemove", function(e) {
			if(e.timeStamp - lastMouseMove >= 750){
				if(e.pageX <= lastMouseX+mouseOffSet &&
				   e.pageX >= lastMouseX-mouseOffSet &&
				   e.pageY <= lastMouseY+mouseOffSet &&
				   e.pageY >= lastMouseY-mouseOffSet){
					insertNewAcao(e, actionCapt.MOUSEOVER);
				}
			}
			lastMouseMove = e.timeStamp;
			lastMouseX = e.pageX;
			lastMouseY = e.pageY;
		});
	}

	document.addEventListener('focusout', function(e){
		insertNewAcao(e, actionCapt.FOCUSOUT);
	});
	document.addEventListener("submit", function(e) {
		insertNewAcao(e, actionCapt.BROWSER_FORM_SUBMIT);
	});

	
	//JHEAT DATA
	function getJhmName(){
		var $elem = getAllElementsWithAttribute('name', 'flowName');
		if($elem.length > 0 && $elem[0].value != ''){
			return $elem[0].value;
		}else{
			return getRegexValue(/(reportName|flowName|className)\=([^\&|\#]*)/g, true);
		}
	}
	function getSectionJhm(){
		var $elem = getAllElementsWithAttribute('name', 'sectionName');
		if($elem.length > 0 && $elem[0].value != ''){
			return $elem[0].value;
		}else{
			return getRegexValue(/(sectionName)\=([^\&|\#]*)/g, false);
		}
	}
	function getActionJhm(){
		var $elem = getAllElementsWithAttribute('name', 'action');
		if($elem.length > 0 && $elem[0].value != ''){
			return $elem[0].value;
		}else{
			return getRegexValue(/(action)\=([^\&|\#]*)/g, false);
		}
	}
	function getStepJhm(){
		var $elem = getAllElementsWithAttribute('name', 'step');
		if($elem.length > 0 && $elem[0].value != ''){
			return $elem[0].value;
		}else{
			return '';
		}
	}

	//eventos extras, referentes ao carregamento da página e ao botao voltar
	window.onload = function(e){
		insertNewAcao(e, actionCapt.BROWSER_ONLOAD);
	}

	// window.onpopstate = function(event) {
	// 	logg("MUDOU HASH: " + JSON.stringify(event.state));
	// };

	bajb_backdetect.OnBack = function(e){
		insertNewAcao({
			target: document.getElementsByTagName("html"),
			pageX: 0,
			pageY: 0
		}, actionCapt.BROWSER_BACK);
	}
	document.onkeydown = function(event) {
	    if(typeof event != 'undefined'){
	    	//Ctrl+R ou F5
	    	if(event.keyCode == 116 || (event.ctrlKey && event.keyCode == 82)){
	        	insertNewAcao(event, actionCapt.BROWSER_RELOAD);
	    	}
	    	//Ctrl+W ou ALT+F4
		    if((event.ctrlKey && event.keyCode == 87) || (event.altKey && event.keyCode == 114)) { 
				insertNewAcao(event, actionCapt.BROWSER_CLOSE);
			}
			if(event.ctrlKey && event.keyCode == 88){
				sendAcoes();
			}
	    }
	};

	/*	FUNÇÕES EXTRAS	*/
	function insertNewAcao(e, action){
		var target, tagName;

		if(e != null){
			target = e.target;
			tagName = target.tagName;
			if(CONNECTED_PLUGIN){
				action = filterAction(e, target, action);
			}
		}else{
			e = {pageY:0, pageX:0};
			target = document.getElementsByTagName('HTML')[0];
			tagName = target.tagName;
		}
		
		if(action){
			var conteudo = getContent(target, action);
			//filtros de captura de dados
			//focusout em campo vazio, não preencheu nada
			//if(!(action == actionCapt.FOCUSOUT && conteudo == "")){
				var acao = new Action(action, new Date().getTime(), getUrl(), conteudo, target.tagName, indexTagName(tagName, target), target.id, target.className, target.name, createXPathFromElement(target), e.pageX, e.pageY, window.innerWidth, window.innerHeight, (get_browser()+" "+get_browser_version()));
				//verificar se a acao eh semelhante com a acao passada
				//logg(acao, acao.sXPath);
				//TODO: se for concluir, verificar se já há outro concluir
				if(ultimaAcao){
					var acoesRapidas = false;//(action == actionCapt.CLICK);
					//se for acao permitida, nao houver acao anterir ou se for mais demorado que 10ms
					if(acoesRapidas || !ultimaAcao.sTime || acao.sActionType != ultimaAcao.sActionType || (acao.sTime && acao.sTime - ultimaAcao.sTime > 10)){
						ultimaAcao = acao;
						addAcao(acao);
					}else{
						ultimaAcao = acao;
						logg("Evitou repetição:", ultimaAcao);
					}
				}else{
					//primeira ação
					ultimaAcao = acao;
					addAcao(acao);
				}
				
			//}
		}
	}

	/**
	Receber o conteudo da ação de acordo com o elemento e a ação
	*/
	function getContent($target, action){
		var val = null, action = action.toLowerCase();

		if(action==actionCapt.CLICK || 
			action==actionCapt.DBLCLICK ||
			action==actionCapt.TAP ||
			action==actionCapt.DBLTAP ||
			action==actionCapt.SWIPEUP ||
			action==actionCapt.SWIPEDOWN ||
			action==actionCapt.SWIPELEFT ||
			action==actionCapt.SWIPERIGHT){
			val = getTextByElement($target);
		}else if(action==actionCapt.FOCUSOUT){
			val = $target.value;
		}else if(action==actionCapt.ORIENTARION){
			val = window.orientation;
		}else if(action==actionCapt.BROWSER_FORM_SUBMIT){
			val = "ACTION=["+$target.getAttribute("action")+"], METHOD=["+$target.getAttribute("method")+"]";
		}else if(action==actionCapt.COMENTARIO){
			val = document.getElementById('UScomentarioTexto').value;
		}else if(action==actionCapt.MOUSEOVER){
			val = getContentMouseOver($target);
			//receber o title caso seja de um no pai
			var count = 0, parent = $target;
			while(val == ""){
				count++;
				parent = parent.parentNode;
				val = getContentMouseOver(parent);
				if(count >= 2){
					val = " ";
				}
			}
		}

		if(val){
			return val;
		}else{
			//PULAR, CONCLUIR, ROTEIRO, BROWSER_CLOSE, BROWSER_RELOAD, BROWSER_ONLOAD, BROWSER_BACK
			return "";
		}
	}

	function getContentMouseOver($target){
		if($target.getAttribute("title")){
			return $target.getAttribute("title");
		}else if($target.getAttribute("alt")){
			return $target.getAttribute("alt");
		}else if($target.value){
			return $target.value;
		}else if(getTextByElement($target)){
			return getTextByElement($target);
		}
		return "";
	}
	
	/**
	Função para filtrar a ação realizada
	*/
	function filterAction(e, $e, defaolt){
		var isOnUseSkill = findParentNodeContainsClass($e, 'UseSkill-nocapt') !== null,
			action = isOnUseSkill;

		if(isOnUseSkill &&
			(e.type == actionCapt.CLICK ||
			 e.type == actionCapt.DBLCLICK ||
			 e.type == actionCapt.TAP ||
			 e.type == actionCapt.DBLTAP)){
			var id = $e.id;
			var idParent = $e.parentNode.id;
			action = isOnUseSkillDIV(id, idParent, $e);
		}
		
		if(action == true){
			action = null;
		}else if(action == false){
			action = defaolt;
		}
		return action;
	}
	/**
	Função para alterar a ação em elementos específicos da UseSkill
	*/
	function isOnUseSkillDIV(id, idParent, elem){
		var action = true;
		
		if(id=="USroteiroFull"||idParent=="USroteiroFull"){
			action = actionCapt.ROTEIRO;
		}else if(id=="USconcluirTarefa"||idParent=="USconcluirTarefa"){
			action = actionCapt.CONCLUIR;
		}else if(findParentNodeContainsClass(elem, 'UScomentarioQualifique')){
			action = actionCapt.COMENTARIO;
		}else if(id=="USpularTarefa"||idParent=="USpularTarefa"){
			action = actionCapt.PULAR;
		}
		
		return action;
	}
	
	
	//LOCALSTORAGE
	function getAcoesString(){
		return localStorage.getItem("useskill_actions");
	}

	function getAcoes(){
		var str = localStorage.getItem("useskill_actions");
		return str != 'null' && str != null ? parseJSON(str) : [];
	}
	
	function printAcoes(){
		if(CONNECTED_PLUGIN){
			chrome.extension.sendRequest({useskill: "getAcoes"}, function(dados){
				logg("Acoes: ", dados);
			});
		}else{
			logg(getAcoes());
		}
	}

	function addAcao(acao){

		if(CONNECTED_PLUGIN){
			var stringAcao = stringfyJSON(acao);
			chrome.extension.sendRequest({useskill: "addAcao", acao: stringAcao});
		}else{
			var arr = getAcoes();
			if(Array.isArray(arr)){
				arr.push(acao);
			}else{
				arr = [acao];
			}

			var str = stringfyJSON(arr);
			localStorage.setItem("useskill_actions", str);

			//permitir acesso as acoes via variavel no window do browser
			window.useSkillActions = arr;
		}

		// logg(acao.sActionType);
		// logg(acao);
		// printAcoes();
	}

	function logg(text){
		console.log(text);
	}

	function setItem(name, acoes){
		var x = stringfyJSON(acoes);
		localStorage.setItem(name, x);
	}

	function clearStorage(){
		localStorage.removeItem("useskill_actions");
	}

	function getItem(name){
		var strObj = localStorage.getItem(name);
		return parseJSON(strObj);
	}

	//AUXILIAR FUNCTIONS

	function getUrl(){
		return location.protocol+"//"+location.host+location.pathname;
	}

	function parseJSON(data) {
		return window.JSON && window.JSON.parse ? window.JSON.parse(data) : (new Function("return " + data))(); 
	}

	function stringfyJSON(data){
		return window.JSON && window.JSON.stringify ? window.JSON.stringify(data) : (new Function("return " + data))();
	}

	function getRegexValue(reg, fullSize){
		var matches = reg.exec(window.location.href);
		return matches != null ? (fullSize ? matches[1]+"="+matches[2] : matches[2]) : "";
	}

	function findParentNodeContainsClass(childObj, classe) {
		var result = childObj;
	    do{
	    	if(result != null &&
	    		result.getAttribute != null &&
	    		result.getAttribute("class") != null &&
	    		result.getAttribute("class") != '' &&
	    		result.getAttribute("class").split(" ").indexOf(classe) != -1){
	    		return result;
	    	}
	    	result = result.parentNode;
	    }while(result != null && result.tagName.toLowerCase() !== 'html');
	    return null;
	}

	function getAllElementsWithAttribute(attribute, value){
		var matchingElements = [];
		var allElements = document.getElementsByTagName('*');
		for (var i = 0, n = allElements.length; i < n; i++){
			var attr = allElements[i].getAttribute(attribute);
		  	if ( attr !== null){
			  	if (value !== undefined && attr !== value){
					continue;
			  	}
			    matchingElements.push(allElements[i]);
			}
		}
		return matchingElements;
	}

	function createXPathFromElement(elm) {
	    var allNodes = document.getElementsByTagName('*');
	    for (segs = []; elm && elm.nodeType == 1; elm = elm.parentNode){
	        if (elm.hasAttribute('id')) {
	                var uniqueIdCount = 0;
	                for (var n=0;n < allNodes.length;n++) {
	                    if (allNodes[n].hasAttribute('id') && allNodes[n].id == elm.id) uniqueIdCount++;
	                    if (uniqueIdCount > 1) break;
	                };
	                if ( uniqueIdCount == 1) {
	                    segs.unshift('id("' + elm.getAttribute('id') + '")');
	                    return segs.join('/');
	                } else {
	                    segs.unshift(elm.localName.toLowerCase() + '[@id="' + elm.getAttribute('id') + '"]');
	                }
	        } else if (elm.hasAttribute('class')) {
	            segs.unshift(elm.localName.toLowerCase() + '[@class="' + elm.getAttribute('class') + '"]');
	        } else {
	            for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
	                if (sib.localName == elm.localName)  i++; };
	                segs.unshift(elm.localName.toLowerCase() + '[' + i + ']');
	        };
	    };
	    return segs.length ? '/' + segs.join('/') : null;
	}; 

	function lookupElementByXPath(path) {
	    var evaluator = new XPathEvaluator();
	    var result = evaluator.evaluate(path, document.documentElement, null,XPathResult.FIRST_ORDERED_NODE_TYPE, null);
	    return  result.singleNodeValue;
	}

	function indexTagName(tag, element){
		var arr = document.getElementsByTagName(tag), count = 0;				
		for(var i in arr){
			if(arr[i] === element){
				return count;
			}
			count++;
		}
		return -1;
	}

	function getTextByElement(elem){
		return getText([elem]);
	}

	function getText(elems){
	    var ret = "", elem;

	    for ( var i = 0; elems[i]; i++ ) {
	        elem = elems[i];

	        // Get the text from text nodes and CDATA nodes
	        if ( elem.nodeType === 3 || elem.nodeType === 4 ) {
	            ret += elem.nodeValue;

	        // Traverse everything else, except comment nodes
	        } else if ( elem.nodeType !== 8 ) {
	            ret += getText( elem.childNodes );
	        }
	    }

	    return ret;
	};

	function get_browser(){
	    var ua=navigator.userAgent,tem,M=ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || []; 
	    if(/trident/i.test(M[1])){
	        tem=/\brv[ :]+(\d+)/g.exec(ua) || []; 
	        return 'IE '+(tem[1]||'');
	        }   
	    if(M[1]==='Chrome'){
	        tem=ua.match(/\bOPR\/(\d+)/)
	        if(tem!=null)   {return 'Opera '+tem[1];}
	        }   
	    M=M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];
	    if((tem=ua.match(/version\/(\d+)/i))!=null) {M.splice(1,1,tem[1]);}
	    return M[0];
	    }

	function get_browser_version(){
	    var ua=navigator.userAgent,tem,M=ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];                                                                                                                         
	    if(/trident/i.test(M[1])){
	        tem=/\brv[ :]+(\d+)/g.exec(ua) || [];
	        return 'IE '+(tem[1]||'');
	        }
	    if(M[1]==='Chrome'){
	        tem=ua.match(/\bOPR\/(\d+)/)
	        if(tem!=null)   {return 'Opera '+tem[1];}
	        }   
	    M=M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];
	    if((tem=ua.match(/version\/(\d+)/i))!=null) {M.splice(1,1,tem[1]);}
	    return M[1];
	}

}

function useskill_capt_clearStorage(){
	localStorage.removeItem("useskill_actions");
	window.useSkillActions = [];
}