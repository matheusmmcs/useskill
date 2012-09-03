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
	console.log(stringAcao);
	console.log(acao);
	acoes.push(acao);
	localStorage.setItem("BG",stringfyJSON(acoes));
}

function clearAcoes(){
	acoes = new Array();
}

//----------------------------------------------------------------------------------------------------------------------------------------------
var domainUseSkill = "http://localhost:8080/Usabilidade";

function Store () {
    this.listaElementos;
    this.elementoAtualLista;
    this.gravando = false;
    this.tabs = new Array();
}

var storage = new Store();

chrome.extension.onRequest.addListener(function(request, sender, sendResponse){
	switch(request.useskill){
		case "nextElement":
		  	nextElement(request.atual, request.lista);
		  	break;
		case "getStorageAndAcoes":
			sendResponse({storage: storage, acoes: acoes});
			break;
		case "getStorage":
		  	sendResponse({dados: storage});
		  	break;
		case "getAcoes":
			sendResponse({dados: acoes});
			break;
		case "addAcao":
			addAcao(request.acao);
			break;
		case "clearAcoes":
			clearAcoes();
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
		  	removeTabsGravando();
		  	break;
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
		console.log("storage.gravando false");	

		proxUrl = domainUseSkill; //URL pós teste
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
}


function insertOnPage(tabId){
	chrome.tabs.executeScript(tabId, {file: "js/jquery.js"});
	chrome.tabs.executeScript(tabId, {file: "js/capt.js"});
	//chrome.tabs.executeScript(tabId, {file: "js/playback.js"});
	chrome.tabs.insertCSS(tabId, {file: "css/topoteste.css"});
	chrome.tabs.insertCSS(tabId, {file: "css/jquery.fancybox.css"});
	chrome.tabs.executeScript(tabId, {file: "js/topoteste.js"});
	chrome.tabs.executeScript(tabId, {file: "js/jquery.fancybox.pack.js"});
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
function parseJSON(data) {
	return window.JSON && window.JSON.parse ? window.JSON.parse(data) : (new Function("return " + data))(); 
}
function stringfyJSON(data){
	return window.JSON && window.JSON.stringify ? window.JSON.stringify(data) : (new Function("return " + data))();
}