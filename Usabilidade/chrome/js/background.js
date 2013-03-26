var domainUseSkill = "http://sistemaseasy.ufpi.br/useskill";
//var domainUseSkill = "http://localhost:8080/Usabilidade";

//----------------------------------------------------------------------------------------------------------------------------------------------

Array.prototype.contains = function(o){
	if(this.indexOf(o)!=-1){
		return true;
	}else{
		return false;
	}
}
Array.prototype.add = function(o){
	if(this.indexOf(o)==-1){
		this.push(o);
	}
}
Array.prototype.remove = function(i){
	if(i<this.length){
		this.splice(i,1);
	}
}
Array.prototype.removeElement = function(o){
	var index = this.indexOf(o);
	if(index!=-1){
		this.splice(index,1);
	}
}
Array.prototype.removeBetween = function(from, to){
	var dif = to - from + 1;
	if(from>=0 && to<this.length && dif>0){
		this.splice(from,dif);
	}	
}
Array.prototype.toConsole = function(){
	for(var i in this){
		if(new String(this[i]).indexOf("function")==-1){
			console.log("["+i+"]->"+this[i]+";");
		}
	}
}

//----------------------------------------------------------------------------------------------------------------------------------------------
localStorage.clear("BG");
var acoes = parseJSON(localStorage.getItem("BG"));

if(!acoes){
	acoes = new Array();
}

function addAcao(stringAcao){
	var acao = parseJSON(stringAcao);
	//console.log(stringAcao);
	//console.log("ADD ACAO");
	console.log(acao);
	acoes.push(acao);
	localStorage.setItem("BG",stringfyJSON(acoes));
}

function clearAcoes(){
	console.log("CLEAR:");
	console.log(acoes);
	acoes = new Array();
}

function acoesContainsConcluir(){
	for(var i in acoes){
		if(acoes[i].sActionType!=null){
			if(new String(acoes[i].sActionType).toUpperCase()=="CONCLUIR"){
				return true;
			}
		}
	}
	return false;
}

//----------------------------------------------------------------------------------------------------------------------------------------------

function Store () {
    this.listaElementos;
    this.elementoAtualLista;
    this.gravando = false;
    this.tabs = new Array();
}

var storage = new Store();

var isOpenned = true;

chrome.extension.onRequest.addListener(function(request, sender, sendResponse){
	if(request.useskill){
		switch(request.useskill){
			case "nextElement":
			  	nextElement(request.atual, request.lista);
			  	break;
			case "getStorageAndAcoes":
				sendResponse({storage: storage, acoes: acoes, isOpenned: isOpenned});
				break;
			case "getStorage":
			  	sendResponse({dados: storage});
			  	break;
			case "getAcoes":
				//console.log("Retornou: ");
				//console.log(acoes);
				sendResponse({dados: acoes});
				break;
			case "addAcao":
				addAcao(request.acao);
				break;
			case "clearAcoes":
				clearAcoes();
				sendResponse({clear: true});
				break;
			case "setNewTab":
			  	//vem de abas com new target
	    		chrome.tabs.create({'url': request.url}, function(tab) {
					if(storage.gravando){
						//adiciono a tab para a lista das tabs monitoradas
						storage.tabs.add(tab.id);
					}
				});
			  	break;
			case "testFinish":
				//finalizar o teste que está em execução
				suspendTest();
			  	break;
			case "getDomain":
				sendResponse({domain: domainUseSkill});
				break;
			case "setOpenned":
				console.log("setOpenned");
				console.log(request);
				if(request.isOpenned!=null){
					isOpenned = request.isOpenned;
				}
				break;
		}
	}else if(request.useskillserver){

		/*area para comunicacao com o servidor*/
		switch(request.useskillserver){
			case "getRoteiro":
				var retorno = null;
				if(request.idTarefa){
					retorno = ajax(domainUseSkill+"/tarefa/roteiro", 'POST', {'idTarefa' : request.idTarefa});
					console.log(retorno)
				}
				sendResponse({dados: retorno});
			  	break;

			case "concluirTarefa":
				var enviado = false;
				var intervalo = window.setInterval(function(){
					console.log(acoesContainsConcluir());
					if(acoesContainsConcluir() && !enviado){
						var dados = concluirOuPularTarefa(request.idTarefa, true, "");
						enviado = true;
						sendResponse({success: dados});
						clearInterval(intervalo);
					}
				},200);
				break;
			case "pularTarefa":
				var dados = concluirOuPularTarefa(request.idTarefa, false, request.justificativa);
				sendResponse({success: dados});
				break;
			case "enviarComentario":
				if(request.idTarefa){
					/*
					processXHR(domainUseSkill+"/tarefa/enviarcomentario", 'POST', {
						'idTarefa' : request.idTarefa,
						'texto' : request.texto,
						'qualificacao' : request.quali,
					});
					*/
					
					$.ajax({
						url: domainUseSkill+"/tarefa/enviarcomentario",
						cache: false,
						type: 'POST',
						dataType : 'json',
						async: false,
						data: {
							'idTarefa' : request.idTarefa,
							'texto' : request.texto,
							'qualificacao' : request.quali,
						},
						success: function(dados){
							sendResponse({success: true});
						},
						error: function(jqXHR, status, err){
							console.log(jqXHR);
							sendResponse({success: false});
						}
					});
				}
				break;
			case "responderPergunta":
				console.log("resposta: ");
				console.log(request);
				if(request.idPergunta){
					var thisDomainUseSkill = domainUseSkill.replace("/Usabilidade","");
					thisDomainUseSkill = thisDomainUseSkill.replace("/useskill","");
					$.ajax({
						url : thisDomainUseSkill+request.url,
						cache: false,
						type : 'POST',
						dataType : 'json',
						async : false,
						data : {
							'perguntaId' 	: request.idPergunta,
							'resposta'		: request.resposta
							},
						success : function(json) {
							sendResponse({success: true});
						},
						error : function(jqXHR, textStatus, errorThrown){
							console.log(jqXHR);
							sendResponse({success: false});
						}
					});
				}else{
					sendResponse({success: false});
				}
				break;
		}
	}
});

//sempre que criar uma nova tab durante a gravação do teste, esta página deve ser monitorada
//mas estamos acompanhando isto no onupdate, visto que sempre que crio ele atualiza a página
chrome.tabs.onCreated.addListener(function(tab) {
	console.log("onCreated: "+tab.id);
});

chrome.tabs.onRemoved.addListener(function(tabId) {
	if(storage.gravando){
		if(storage.tabs.contains(tabId)){
			console.log("remove: "+tabId);
			removeTab(tabId);
		}
	}
});

//para evitar que página seja criada ou refreshed e perca os js nela
chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
	if(storage.gravando){
		if(changeInfo.status == "loading"){
			if(storage.tabs.contains(tabId)){
				console.log("update: "+tabId);
				insertOnPage(tabId);
			}
		}
	}
});

//----------------------------------------------------------------------------------------------------------------------------------------------

//função para carregar o próximo elemento do teste ou encerra-lo
function nextElement(atual, lista){
	var listaElementos;
	var proxUrl;

	//verificar se está no inicio do teste, para setar no storage a sequencia de elementos a serem percorridos
	if(lista){
		listaElementos = parseJSON(lista.listaElementos);
		storage.listaElementos = lista.listaElementos;
	}else{
		listaElementos = parseJSON(storage.listaElementos);
	}

	//atualizo o que está armazenado no storage
	storage.elementoAtualLista = atual;
	
	//se ainda há elementos do teste a serem realizados
	if(listaElementos.length > atual){
		var elemento = listaElementos[atual];

		storage.gravando = true;
		console.log("storage.gravando true");
		//iniciando nova tarefa/pergunta... reinicia as acoes
		clearAcoes();

		//diferenciando tarefas, perguntas e quais quer outros tipos
		if(elemento.tipo == "T"){
			var objJson = ajax(domainUseSkill+"/tarefa/"+elemento.id+"/json", "GET");
			var tarefa = objJson.tarefaVO;
			proxUrl = tarefa.url;
		}else if(elemento.tipo == "P"){
			proxUrl = domainUseSkill+"/teste/responder/pergunta/"+elemento.id;
		}
	}else{
		storage.gravando = false;
		console.log("TESTE CONCLUIDO");
		console.log("storage.gravando false");

		proxUrl = domainUseSkill+"/teste/participar/termino"; //URL pós teste
	}

	//abrindo a nova aba e fechando as antigas
	chrome.tabs.create({'url': proxUrl}, function(tab) {
		if(!lista){
			removeTabsGravando();
		}
		//se ainda for gravar
		if(storage.gravando){
			//adiciono a tab para a lista das tabs monitoradas
			storage.tabs.add(tab.id);			
		}
	});
}

function removeTab(id){
	storage.tabs.removeElement(id);
	console.log("removeTab: "+id);
	console.log(storage.tabs);
	if(storage.tabs.length<=0){
		storage.gravando = false;
		console.log("storage.gravando false")
	}
}

function removeTabsGravando(){
	var count = storage.tabs.length;	
	if(count>=0){
		for(var i = 0; i < count; i++){
			console.log("ChromeRemoveTabO: "+storage.tabs[i]);
			chrome.tabs.remove(storage.tabs[i]);
		}
	}
	//reset isOpenned
	isOpenned = true;
}

function suspendTest(){
	var responseUseSkill = ajax(domainUseSkill+"/teste/participar/adiar", "GET");
	console.log(responseUseSkill);
	if(responseUseSkill.boolean){
		console.log("Teste Adiado");
		clearAcoes();
		removeTabsGravando();
	}
}


function insertOnPage(tabId){
	chrome.tabs.executeScript(tabId, {file: "js/jquery.js"});
	chrome.tabs.executeScript(tabId, {file: "js/capt.js"});
	chrome.tabs.executeScript(tabId, {file: "js/canvas/jquery.color.js"});
	chrome.tabs.executeScript(tabId, {file: "js/canvas/jquery.JCrop.js"});
	chrome.tabs.executeScript(tabId, {file: "js/canvas/html2canvas.js"});
	//chrome.tabs.executeScript(tabId, {file: "js/playback.js"});
	chrome.tabs.insertCSS(tabId, {file: "css/useskill.css"});
	chrome.tabs.executeScript(tabId, {file: "js/useskill.js"});
	//chrome.tabs.insertCSS(tabId, {file: "css/jquery.fancybox.css"});
	//chrome.tabs.executeScript(tabId, {file: "js/jquery.fancybox.pack.js"});
	chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tabId});
}

function insertOnQuestion(){
	chrome.tabs.executeScript(tabId, {file: "js/jquery.js"});
	chrome.tabs.executeScript(tabId, {file: "js/inserquestion.js"});
	chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tabId});
}

//----------------------------------------------------------------------------------------------------------------------------------------------
/*método genérico para realizar ajax*/
function ajax(caminho, tipo, dados){
	var retorno;
	$.ajax({
		url: caminho,
		cache: false,
		type: tipo,
		dataType : 'json',
		async: false,
		data: dados,
		success: function(dados){
			retorno = dados;
		},
		error: function(jqXHR, status, err){
			console.log(jqXHR);
		}
	});
	return retorno;
}
function processXHR(caminho, tipo, dados) {
    var formData = new FormData();
    var xhr = new XMLHttpRequest();

    for(var d in dados){
    	formData.append(d, dados[d]);
    }

    xhr.open(tipo, caminho, true);
    //xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.onload = function(e) {
        console.log("loaded!")
    };

    xhr.send(formData);
    return false;
}
function parseJSON(data) {
	return window.JSON && window.JSON.parse ? window.JSON.parse(data) : (new Function("return " + data))(); 
}
function stringfyJSON(data){
	return window.JSON && window.JSON.stringify ? window.JSON.stringify(data) : (new Function("return " + data))();
}

//----------------------------------------------------------------------------------------------------------------------------------------------
/*métodos para auxiliar comunicacao com o servidor*/
function concluirOuPularTarefa(idTarefa, isFinished, justificativa){
	var retorno = null;
	var acoesString = stringfyJSON(acoes);
	var dados = {
		'tarefaId': idTarefa, 
		'dados': acoesString,
		'isFinished': isFinished,
	}
	if(justificativa){
		dados['comentario'] = justificativa;
	}
	console.log(dados);
	if(idTarefa){
		retorno = ajax(domainUseSkill+"/tarefa/save/fluxo", 'POST', dados);
		if(retorno.string==true||retorno.string=="true"){
			clearAcoes();
			retorno = true;
		}else{
			//problema no servidor
			retorno = false;
			console.log('prob servidor');
		}
	}else{
		//problema de id = null
		retorno = false;
		console.log('id null');
	}
	return retorno;
}