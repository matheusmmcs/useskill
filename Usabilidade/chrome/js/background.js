var domainUseSkill = "http://localhost:8080/Usabilidade";
var storage = new Array();

// var elementoAtualLista;
// var listaElementos;
// var idTarefa;


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

function nextElement(idTarefa){
	var objJson = ajax(domainUseSkill+"/teste/participar/"+idTarefa+"/aceitar", "GET");
	var listaElementos = parseJSON(objJson.listaElementos);
	var atual = 0;

	storage["listaElementos"] = objJson.listaElementos;
	storage["elementoAtualLista"] = atual;
	
	if(listaElementos.length > atual){
		localStorage.clear("tabIds");
		var elemento = listaElementos[atual];
		if(elemento.tipo == "T"){
			storage["idTarefa"] = elemento.id;
			var objJson = ajax(domainUseSkill+"/tarefa/"+elemento.id+"/json", "GET");
			var tarefa = objJson.tarefaVO;

			chrome.tabs.create({'url': tarefa.url}, function(tab) {
        		//set tabIds separados por ,
        		localStorage.setItem("tabIds", tab.id);
				chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id});
    		});
		}else{
			alert("PERGUNTA "+elemento.id);
		}
	}
	
}

chrome.extension.onRequest.addListener(function(request, sender, sendResponse){
    if (request.useskill == "nextElement"){
    	nextElement(request.idTarefa);
    	//sendResponse({farewell: "goodbye"});
    }else if (request.useskill == "getStorage"){
    	console.log(storage[request.key]);
    	sendResponse({dados: storage[request.key]});
    }
});

// Called when the user create a new tab on the browser action.
chrome.tabs.onCreated.addListener(function(tab) {
	//chrome.tabs.executeScript(tab.id, {file: "teste.js"});
});

chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
    if(changeInfo.status == "loading"){
    	var x = new String(localStorage.getItem("tabIds"));
    	if(x!=null){
			var tabIds = x.split(",");
			for(var item in tabIds){
				if(tabIds[item] == tab.id){
					chrome.tabs.insertCSS(tab.id, {file: "css/topoteste.css"});
		    		chrome.tabs.insertCSS(tab.id, {file: "css/jquery.fancybox.css"});
		    		chrome.tabs.executeScript(tab.id, {file: "js/topoteste.js"});
		    		chrome.tabs.executeScript(tab.id, {file: "js/jquery.fancybox.pack.js"});
		    		chrome.browserAction.setIcon({path: 'images/icon16on.png', tabId: tab.id});
				}
			}
		}
    }

});