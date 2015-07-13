
var _tabs = [];

chrome.browserAction.onClicked.addListener(function(tab) { 
	console.log(tab.url.indexOf('chrome://'))
	if(tab.url.indexOf('chrome://') != 0){
		changeStateCapture(tab.id, true);
	}else{
		alert('UseSkill Capt Element cant be used in this page!');
	}
});

chrome.tabs.onRemoved.addListener(function(tabId, changeInfo, tab) {
	var pos = _tabs.indexOf(tabId);
	if(pos != -1){
		_tabs.pop(pos);
	}
});

//para evitar que página seja criada ou refreshed e perca os js nela
chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
	if(changeInfo.status == "complete" && tab.url.indexOf('chrome://') != 0){
		chrome.tabs.insertCSS(tabId, {file: "css/captelement.css"});
		chrome.tabs.executeScript(tabId, {file: "js/jquery-1.11.3.min.js"}, function(){
			chrome.tabs.executeScript(tabId, {file: "js/captelement.js"});
			initialStateCapture(tabId, true);
		});
	}
});

chrome.extension.onRequest.addListener(function(request, sender, sendResponse){
	if(request.useskillelement){
		switch(request.useskillelement){
			case "changeIcon":
				changeStateCapture(sender.tab.id, false);
			  	break;
			case "changeIconOff":
				setState(false, sender.tab.id, request.close);
				break;
			case "changeIconOn":
				setState(true, sender.tab.id, request.close);
				break;
		}
	}
});


function changeStateCapture(tabId, insertScript){
	var pos = _tabs.indexOf(tabId);
	if(pos == -1){
		setState(true, tabId, insertScript);
	}else{
		setState(false, tabId, insertScript);
	}
}

function initialStateCapture(tabId, insertScript){
	var pos = _tabs.indexOf(tabId);
	if(pos != -1){
		setState(true, tabId, insertScript);
	}else{
		setState(false, tabId, insertScript);
	}
}

function setState(on, tabId, insertScript){
	if(on){
		_tabs.push(tabId);
		chrome.browserAction.setIcon({path: 'images/icon64on.png', tabId: tabId});
		if(insertScript){
			chrome.tabs.executeScript(tabId, {file: "js/captelement.open.js"});
		}
	}else{
		_tabs.pop(_tabs.indexOf(tabId));
		chrome.browserAction.setIcon({path: 'images/icon64.png', tabId: tabId});
		if(insertScript){
			chrome.tabs.executeScript(tabId, {file: "js/captelement.close.js"});
		}
	}
}

