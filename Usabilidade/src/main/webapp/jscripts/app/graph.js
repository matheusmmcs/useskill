var optionsGraphUseSkill = {
  physics: {
    enabled: false,
    barnesHut: {
      gravitationalConstant: -20000,
      centralGravity: 0.1,
      springLength: 150,
      springConstant: 0.1,
      damping: 0.1,
      avoidOverlap: 0
    },
    //minVelocity: 0.75
  },

  // layout: {
  //     hierarchical:{
  //         direction: "LR"
  //     }
  // },

  edges: {
    arrows: {
      to: {
        enabled: true,
        scaleFactor: 0.4
      }
    },
    color: 'gray',
    smooth: {
        type:'cubicBezier',
        forceDirection: 'horizontal',
        roundness: 0.4
    }
  },

  nodes: {
    // default for all nodes
    shape: 'dot',
    color: {
      border: '#bdc3c7',
      background: '#ecf0f1',
      highlight: {
        border: 'gray',
        background: '#bdc3c7'
      },
      hover: {
        border: '#bdc3c7',
        background: '#bdc3c7'
      }
    }
  },
  groups: {
    black: {
      color: {
        border: '#333',
        background: '#555',
        highlight: {
          border: '#111',
          background: '#111'
        },
        hover: {
          border: '#111',
          background: '#333'
        }
      }
    },
    blue: {
      color: {
        border: '#2980b9',
        background: '#3498db',
        highlight: {
          border: '#0d6094',
          background: '#0d6094'
        },
        hover: {
          border: '#0d6094',
          background: '#2980b9'
        }
      }
    },
    green: {
      color: {
        border: '#27ae60',
        background: '#2ecc71',
        highlight: {
          border: '#08803a',
          background: '#08803a'
        },
        hover: {
          border: '#08803a',
          background: '#27ae60'
        }
      }
    },
    red: {
      color: {
        border: '#c0392b',
        background: '#e74c3c',
        highlight: {
          border: '#992116',
          background: '#992116'
        },
        hover: {
          border: '#992116',
          background: '#c0392b'
        }
      }
    },
    yellow: {
        color: {
          border: '#C0912B',
          background: '#E7C83C',
          highlight: {
            border: '#997A16',
            background: '#997A16'
          },
          hover: {
            border: '#997A16',
            background: '#C0912B'
          }
        }
      }
  }
};

var customScalingFunctionUseSkill = function(min, max, total, value) {
	if (max === min) {
		return 0.5;
	} else {
		min = min / 2;
		var scale = 1 / (max - min);
		return Math.max(0, (value - min) * scale);
	}
};

function getEdgeUseSkill(from, to, arrEdges) {
	for ( var i in arrEdges) {
		if (arrEdges[i].to == to && arrEdges[i].from == from) {
			return arrEdges[i];
		}
	}
	return null;
}
function getNodeUseSkill(id, arrNodes) {
	for ( var i in arrNodes) {
		if (arrNodes[i].id == id) {
			return arrNodes[i];
		}
	}
	return null;
}
function getRandomIntUseSkill(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateGraphSession (frequentPatterns, session) {
	var arrNodes = [], arrEdges = [], maxVal = 0;
	
	var actionAnterior = null, idSeq = 0;
	for (var a in session.actions) {
		var action = session.actions[a], 
			idActionAtual = action.identifier,
			node = getNodeUseSkill(idActionAtual, arrNodes);
		
		if (!node) {
			node = {
				id : idActionAtual,
				label : 'Action ' + idActionAtual,
				seq: idSeq,
				sessions : [],
				countPatterns : 1,
				value : 1,
				scaling : {
					customScalingFunction : customScalingFunctionUseSkill
				}
			};
			arrNodes.push(node);
			idSeq++;
		} else {
			node.value++;
			node.countPatterns++;
		}
		maxVal = node.value > maxVal ? node.value : maxVal;
		
		if (actionAnterior != null) {
			var idActionAnterior = actionAnterior.identifier;

			var edge = getEdgeUseSkill(idActionAnterior, idActionAtual, arrEdges);
			if (!edge) {
				arrEdges.push({
					from : idActionAnterior,
					to : idActionAtual,
					countPatterns : 1,
					sessions : [],
					value : 1,
					scaling : {
						customScalingFunction : customScalingFunctionUseSkill
					}
				});
			} else {
				edge.value++;
				edge.countPatterns++;
			}
		}

		actionAnterior = action;
	}
	
	for (var n in arrNodes) {
		arrNodes[n].shape = 'square';
	}
	
	for ( var pti in frequentPatterns) {
		var patternAtual = frequentPatterns[pti];
		var itemAnterior = null;
		for ( var i in patternAtual.itemsetsFormatted) {
			var itemAtual = patternAtual.itemsetsFormatted[i], idItemAtual = itemAtual.idAction;
			var node = getNodeUseSkill(idItemAtual, arrNodes);
			
			//busca nó no arraynodes e marca como de frequent pattern
			for (var n in arrNodes) {
				if (arrNodes[n].id == idItemAtual) {
					arrNodes[n].shape = 'dot';//borderWidth = 4;
				}
			}
		}
	}
	
	return {
		arrNodes: arrNodes,
		arrEdges: arrEdges,
		maxVal: maxVal
	};
}

function generateGraphFrequentPatterns (type, frequentPatterns, sessions) {
	// construir árvore com ações do padrão
	var arrNodes = [], arrEdges = [], maxVal = 0, idSeq = 0;
	for ( var pti in frequentPatterns) {
		var patternAtual = frequentPatterns[pti];
		// console.log(patternAtual);
		var itemAnterior = null;
		for ( var i in patternAtual.itemsetsFormatted) {
			var itemAtual = patternAtual.itemsetsFormatted[i], idItemAtual = itemAtual.idAction;

			// console.log(idItemAtual);
			var node = getNodeUseSkill(idItemAtual, arrNodes);

			if (!node) {
				node = {
					id : idItemAtual,
					label : 'Action ' + idItemAtual,
					seq: idSeq,
					sessions : [],
					countPatterns : 1,
					value : 1,
					scaling : {
						customScalingFunction : customScalingFunctionUseSkill
					}
				};
				arrNodes.push(node);
				idSeq++;
			} else {
				node.value++;
				node.countPatterns++;
			}
			maxVal = node.value > maxVal ? node.value : maxVal;

			if (itemAnterior != null) {
				var idItemAnterior = itemAnterior.idAction;

				var edge = getEdgeUseSkill(idItemAnterior, idItemAtual, arrEdges);
				if (!edge) {
					arrEdges.push({
						from : idItemAnterior,
						to : idItemAtual,
						countPatterns : 1,
						sessions : [],
						value : 1,
						scaling : {
							customScalingFunction : customScalingFunctionUseSkill
						}
					});
				} else {
					edge.value++;
					edge.countPatterns++;
				}
			}

			itemAnterior = itemAtual;
		}
	}

	// construir grafos com as ações do padrão sequencial, mas o tamanho dos nós
	// e das arestas são relativos à quantidade de sessões que realizaram
	if (type == 'sessions') {
		// resetar largura de nós
		for ( var n in arrNodes) {
			arrNodes[n].value = 0;
		}
		// arestas serão reconstruídas
		arrEdges = [];

		for ( var s in sessions) {
			var session = sessions[s], actions = session.actions, idNodeBefore = null;
			for ( var a in actions) {
				var node = getNodeUseSkill(actions[a].identifier, arrNodes);
				if (node) {
					node.value++;
					if (idNodeBefore != null) {
						var edge = getEdgeUseSkill(idNodeBefore, node.id, arrEdges);
						if (!edge) {
							edge = {
								from : idNodeBefore,
								to : node.id,
								sessions : [],
								value : 1,
								scaling : {
									customScalingFunction : customScalingFunctionUseSkill
								}
							};
							arrEdges.push(edge);
						} else {
							edge.value++;
						}
					}
					idNodeBefore = node.id;
				}
			}
		}
	}
	
	return {
		arrNodes: arrNodes,
		arrEdges: arrEdges,
		maxVal: maxVal
	};
}

function drawGraph(idContainer, graphData, sessions, actionsSituation, situationsEnum, factorScaleX, preserveY){
	var arrNodes = graphData.arrNodes, 
		arrEdges = graphData.arrEdges,
		maxVal = graphData.maxVal,
		actualNodesPosY = {};
	
	console.log("render graph", idContainer, graphData, sessions, actionsSituation, situationsEnum, factorScaleX, preserveY);
	
	factorScaleX = factorScaleX || 3;
	  
	  //colorir grafo e organizar
	  maxVal = maxVal < 7 ? 7 : maxVal;
	  maxVal = maxVal > 50 ? 50 : maxVal;
	  
	  var posX = 0, scaleX = (50 + factorScaleX * maxVal), newPosY = 0;
		
	  arrNodes.sort(function(a, b) {
		    return a.seq - b.seq;
	  });
	  for (var n in arrNodes) {
		  arrNodes[n].x = posX * scaleX;
		  newPosY = getRandomIntUseSkill(0, 2 * scaleX);
		  
		  if (preserveY) {
			  for (var oid in preserveY) {
				  if (oid == arrNodes[n].id) {
					  newPosY = preserveY[oid];
					  break;
				  }
			  }
		  }
		  
		  arrNodes[n].y = newPosY;
		  actualNodesPosY[arrNodes[n].id] = newPosY;
		  posX++;
	  }
	  

	  //add more info
	  for (var s in sessions) {
		  var session = sessions[s], 
		  	actions = session.actions,
		  	idNodeBefore = null;
		  var sessionDetails = {
			  id : session.id,
			  username : session.username,
			  required : session.userRateRequired,
			  success : session.userRateSuccess,
			  time : session.time,
			  qdtActions : session.actions.length,
			  effectiveness : session.effectiveness,
			  efficiency : session.efficiency,
			  classification: session.classification
		  };
		  for (var a in actions) {
			  var node = getNodeUseSkill(actions[a].identifier, arrNodes);
			  if (node) {
				  node.action = actions[a];
				  if (node.sessions.indexOf(sessionDetails) == -1) {
					  node.sessions.push(sessionDetails);
				  }
				  if (idNodeBefore != null) {
					  var edge = getEdgeUseSkill(idNodeBefore, node.id, arrEdges);
					  if (edge != null && edge.sessions.indexOf(sessionDetails) == -1) {
						  edge.sessions.push(sessionDetails);
					  }
				  }
				  idNodeBefore = node.id;
			  }
		  }
	  }
	
	  var nodes = new vis.DataSet(arrNodes);
	  var edges = new vis.DataSet(arrEdges);
	  
	  // create a network
	  var container = document.getElementById(idContainer);
	
	  // provide the data in the vis format
	  var data = {
	      nodes: nodes,
	      edges: edges
	  };
	  //var options = {};
	
	  // initialize your network!
      var network = new vis.Network(container, data, optionsGraphUseSkill);
      
      //add information on edges
      for (var e in network.body.edges) {
		  for (var a in arrEdges) {
			  if (arrEdges[a].sessions != undefined && arrEdges[a].id == network.body.edges[e].id) {
				  network.body.edges[e].options.sessions = arrEdges[a].sessions;
			  } 
		  }
	  }
      
      refreshGraph(network, actionsSituation, situationsEnum);
      network.preserveY = actualNodesPosY;
      
      return network;
}

function refreshGraph(network, actionsSituation, situationsEnum){
	if (network) {
		var arrNodes = network.body.nodes;
		for (var n in arrNodes) {
			
			var hasChanged = false,
				actionSituation = actionsSituation[arrNodes[n].id];
			
			if (actionSituation != null) {
				for (var s in situationsEnum) {
					if (situationsEnum[s].id == actionSituation.id && actionSituation.group != undefined) {
						arrNodes[n].options.color = optionsGraphUseSkill.groups[actionSituation.group].color;
						hasChanged = true;
					}
				}
			}
			
			if (arrNodes[n].options.action != null) {
				if (arrNodes[n].options.action.required == true) {
					arrNodes[n].options.color = optionsGraphUseSkill.groups.blue.color;
					hasChanged = true;
				} else if (arrNodes[n].options.action.initial == true ||
							arrNodes[n].options.action.end == true) {
					arrNodes[n].options.color = optionsGraphUseSkill.groups.black.color;
					hasChanged = true;
				}
			}
			
			if (!hasChanged) {
				arrNodes[n].options.color = optionsGraphUseSkill.nodes.color;
				
			}
		}
		network.redraw();
	}
}

function generateGraphSmells (sessionGraph) {
	var arrNodes = [], arrEdges = [], maxVal = 0;
	
	//mapear todos os ids de nodes
	var nodeIds = {};
	var nodeIdsCount = 0;
	for (var i in sessionGraph.edgeMap) {
		var obj = sessionGraph.edgeMap[i];
		if (nodeIdsCount == 0) {
			nodeIds[obj.source] = {
				id: nodeIdsCount,
				value: 1,
				desc: obj.source
			}
			nodeIdsCount++;
		}
		if (nodeIds[obj.target]) {
			nodeIds[obj.target].value++;
		} else {
			nodeIds[obj.target] = {
				id: nodeIdsCount,
				value: 1,
				desc: obj.target
			}
			nodeIdsCount++;
		}
	}
	
	for (var i in nodeIds) {
		var obj = nodeIds[i];
		arrNodes.push({
			id: obj.id,
			value: obj.value,
			seq: obj.id,
			desc: obj.desc,
			label: "Action "+obj.id
		});
		maxVal = maxVal > obj.value ? maxVal : obj.value;
	}
	for (var i in sessionGraph.edgeMap) {
		var obj = sessionGraph.edgeMap[i];
		arrEdges.push({
			from: nodeIds[obj.source].id,
			to: nodeIds[obj.target].id,
			value: obj.weight
		});
	}
	
	return {
		arrNodes: 	arrNodes, 
		arrEdges: 	arrEdges, 
		maxVal:		maxVal
	}
}
