var domainUseSkill = "http://localhost:8080/Usabilidade";

//var storage = new Array();
function Store () {
    this.listaElementos;
    this.elementoAtualLista;
    this.gravando = false;
    this.tabs = new Array();
}

var storage = new Store();
//armazena o dado se uma tab acabou de ser criada, para evitar duplicidade no evento onUpdate, já que ao criar a tab as vezes da um update depois
var tabCreated = false;
//armazenar se teste acabou de ser concluido, fazendo com que todos as abas anteriores a dele sejam removidas


Array.prototype.removeIn = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};
Array.prototype.remove = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
        	this.removeIn(i);
        	return true;
        }
    }
    return false;
}
Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}

// Remove the second item from the array -> array.removeIn(1);
// Remove the second-to-last item from the array -> array.removeIn(-2);
// Remove the second and third items from the array -> array.removeIn(1,2);
// Remove the last and second-to-last items from the array -> array.removeIn(-2,-1);

function parseJSON(data) {
	return window.JSON && window.JSON.parse ? window.JSON.parse( data ) : (new Function("return " + data))(); 
}

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

function nextElement(atual, lista){
	var listaElementos;
	if(lista){
		listaElementos = parseJSON(lista.listaElementos);
		storage.listaElementos = lista.listaElementos;
		removeTabs();
	}else{
		listaElementos = parseJSON(storage.listaElementos);
	}

	storage.elementoAtualLista = atual;
	storage.gravando = true;
	
	if(listaElementos.length > atual){
		var elemento = listaElementos[atual];

		if(elemento.tipo == "T"){
			var objJson = ajax(domainUseSkill+"/tarefa/"+elemento.id+"/json", "GET");
			var tarefa = objJson.tarefaVO;

			storage.gravando = true;
			console.log("storage.gravando true");
			chrome.tabs.create({'url': tarefa.url}, function(tab) {
				if(!lista){
					removeTabs("antigas");
				}
				
				chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id});
    		});
		}else if(elemento.tipo == "P"){
			storage.gravando = true;

			console.log("storage.gravando true");
			chrome.tabs.create({'url': domainUseSkill+"/teste/responder/pergunta/"+elemento.id}, function(tab) {
				if(!lista){
					removeTabs("antigas");
				}
				
				chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id});
    		});
		}
	}else{
		storage.gravando = false;

		chrome.tabs.create({'url': domainUseSkill}, function(tab) {
			removeTabs("antigas");
			
			chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id});
		});
	}	
}

function removeTabs(tipo){
	console.log("removeTabs: "+tipo);
	var cont = storage.tabs.length;
	console.log("cont: "+cont);
	if(tipo=="antigas"){
		cont--;
	}
	console.log("cont: "+cont);
	for(var i = 0; i < cont; i++){
		console.log("tabtoremove: "+storage.tabs[i]);
		chrome.tabs.remove(storage.tabs[i]);
	}
}

chrome.extension.onRequest.addListener(function(request, sender, sendResponse){
    if (request.useskill == "nextElement"){

    	nextElement(request.atual, request.lista);
    	//sendResponse({farewell: "goodbye"});
    }else if (request.useskill == "getStorage"){
		console.log(storage);
    	sendResponse({dados: storage});
    }
});

//sempre que criar uma nova tab durante a gravação do teste, esta página deve ser monitorada
//mas estamos acompanhando isto no onupdate, visto que sempre que crio ele atualiza a página
chrome.tabs.onCreated.addListener(function(tab) {
	//console.log("onCreated: "+tab.id)
	if(storage.gravando){
		tabCreated = true;
		insertOnPage(tab.id);
		storage.tabs.push(tab.id);
		console.log("Create -> storage.tabs.push "+tab.id);
		console.log(storage.tabs);
		chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id}, function(){console.log("# icon setted: "+tab.id);});
	}
});

chrome.tabs.onRemoved.addListener(function(tabId) {
	//console.log("onRemoved: "+tabId)
	if(storage.gravando){
		if(storage.tabs.contains(tabId)){
			//se essa tab estava sendo monitorada, removo-a
			console.log("Remove -> storage.tabs.remove "+tabId);
			storage.tabs.remove(tabId);
			console.log(storage.tabs);
			//verifico se ainda há alguma aba sendo monitorada. Se não houver, altero estado de gravando
			if(storage.tabs.length==0){
				//se iniciou outro teste

					storage.gravando = false;
					console.log("storage.gravando false");

			}
		}
	}
});

//para evitar que página seja criada ou refreshed e perca os js nela
chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
	//console.log("onUpdated: "+tabId)
	if(storage.gravando){
		if(changeInfo.status == "loading"){
			if(!tabCreated){
				//se tab não tiver sido criada agora
				if(!storage.tabs.contains(tabId)){
					//não contém na lista de tabs monitoradas, adiciono
					storage.tabs.push(tabId);
					console.log("Update -> storage.tabs.push "+tabId);
					console.log(storage.tabs);
				}else{
					console.log("Update -> página refreshed "+tabId);
					console.log(storage.tabs);
				}
				insertOnPage(tabId);
			}else{
				chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id}, function(){console.log("# icon setted: "+tab.id);});
			}
			tabCreated=false;
		}
	}
});

function insertOnPage(tabId){
	chrome.tabs.insertCSS(tabId, {file: "css/topoteste.css"});
	chrome.tabs.insertCSS(tabId, {file: "css/jquery.fancybox.css"});
	chrome.tabs.executeScript(tabId, {file: "js/topoteste.js"});
	chrome.tabs.executeScript(tabId, {file: "js/jquery.fancybox.pack.js"});
}