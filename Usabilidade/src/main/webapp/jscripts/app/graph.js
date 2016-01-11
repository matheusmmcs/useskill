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


function drawGraph(type, idContainer, frequentPatterns, sessions, actionsSituation, situationsEnum){
	  function getEdge(from, to, arrEdges) {
	    for (var i in arrEdges) {
	      if (arrEdges[i].to == to && arrEdges[i].from == from) {
	        return arrEdges[i];
	      }
	    }
	    return null;
	  }
	
	  function getNode(id, arrNodes) {
	    for (var i in arrNodes) {
	      if (arrNodes[i].id == id) {
	        return arrNodes[i];
	      }
	    }
	    return null;
	  }
	  
	  function getRandomInt(min, max) {
		    return Math.floor(Math.random() * (max - min + 1)) + min;
		}
	  
//	  function patternsContainsNodeBefore(id, patterns) {
//		  for (var pti in patterns) {
//			  var itensFormatted = patterns[pti].itemsetsFormatted;
//			  for (var i = 0; i < itensFormatted.length; i++) {
//				  if (itensFormatted[i].idAction == id && i > 0) {
//					  return true;
//				  }
//			  }
//		  }
//		  return false;
//	  }
	  
	//encontrar ação que representa as primeiras ações das sessões -> auxílio na disposição dos nós
//	  var firstItens = [], realFirstItens = [];
//	  for (var pti in frequentPatterns) {
//		  var id = frequentPatterns[pti].itemsetsFormatted[0].idAction;
//		  if (firstItens.indexOf(id) == -1) {
//			  firstItens.push(id);
//		  }
//	  }
//	  for (var i in firstItens) {
//		  var id = firstItens[i];
//		  if (!patternsContainsNodeBefore(id, frequentPatterns)) {
//			  realFirstItens.push(id);
//		  }
//	  }
	
	  //construir árvore com ações do padrão
	  var arrNodes = [], arrEdges = [], maxVal = 0;
	  for (var pti in frequentPatterns) {
	    var patternAtual = frequentPatterns[pti];
	    //console.log(patternAtual);
	    var itemAnterior = null;
	    for (var i in patternAtual.itemsetsFormatted) {
	      var itemAtual = patternAtual.itemsetsFormatted[i],
	        idItemAtual = itemAtual.idAction;
	      
	      //console.log(idItemAtual);
	      var node = getNode(idItemAtual, arrNodes);
	
	      if (!node) {
	        arrNodes.push({
	          id: idItemAtual, 
	          label: 'Action '+idItemAtual,
	          sessions: [],
	          countPatterns: 1,
	          value: 1,
	          scaling: {
	        	  customScalingFunction: function (min,max,total,value) {
	        		  if (max === min) {
        			    return 0.5;
        			  }
        			  else {
        				  min = min/2;
        			    var scale = 1 / (max - min);
        			    return Math.max(0,(value - min)*scale);
        			  }
	        	  }
	          }
	        });
	        posX++;
	      } else {
	        node.value++;
	        node.countPatterns++;
	        maxVal = node.value > maxVal ? node.value : maxVal; 
	      }
	
	      if (itemAnterior != null) {
	        var idItemAnterior = itemAnterior.idAction;
	
	        var edge = getEdge(idItemAnterior, idItemAtual, arrEdges);
	        if (!edge) {
	          arrEdges.push({
	            from: idItemAnterior, 
	            to: idItemAtual,
	            countPatterns: 1,
	            sessions: [],
	            value: 1
	          });
	        } else {
	          edge.value++;
	          edge.countPatterns++;
	        }
	      }
	
	      itemAnterior = itemAtual;
	    }
	  }
	  
	  //colorir grafo e organizar
	  var posX = 0, scaleX = (50 + 2 * maxVal);
	  arrNodes.sort(function(a, b) {
		    return a.id - b.id;
	  });
	  for (var n in arrNodes) {
		  arrNodes[n].x = posX * scaleX;
		  arrNodes[n].y = getRandomInt(0, 2 * scaleX);
		  posX++;
	  }
	  
	  //redraw
	  
	  //construir grafos com as ações do padrão sequencial, mas o tamanho dos nós e das arestas são relativos à quantidade de sessões que realizaram
	  if (type == 'sessions') {
		  //resetar largura de nós
		  for (var n in arrNodes) {
			  arrNodes[n].value = 0;
		  }
		  //arestas serão reconstruídas
		  arrEdges = [];
		  
		  for (var s in sessions) {
			  var session = sessions[s], 
			  	actions = session.actions,
			  	idNodeBefore = null;
			  for (var a in actions) {
				  var node = getNode(actions[a].identifier, arrNodes);
				  if (node) {
					  node.value++;
					  if (idNodeBefore != null) {
						  var edge = getEdge(idNodeBefore, node.id, arrEdges);
						  if (!edge) {
							  edge = {
					            from: idNodeBefore, 
					            to: node.id,
					            sessions: [],
					            value: 1
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
			  var node = getNode(actions[a].identifier, arrNodes);
			  if (node) {
				  node.action = actions[a];
				  if (node.sessions.indexOf(sessionDetails) == -1) {
					  node.sessions.push(sessionDetails);
				  }
				  if (idNodeBefore != null) {
					  var edge = getEdge(idNodeBefore, node.id, arrEdges);
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
      
      return network;
}

function refreshGraph(network, actionsSituation, situationsEnum){
	var arrNodes = network.body.nodes;
	for (var n in arrNodes) {
		
		console.log(arrNodes[n]);
		var hasChanged = false,
			actionSituation = actionsSituation[arrNodes[n].id];
		
		if (actionSituation != null) {
			for (var s in situationsEnum) {
				if (situationsEnum[s].id == actionSituation.id) {
					arrNodes[n].options.color = optionsGraphUseSkill.groups[actionSituation.group].color;
					hasChanged = true;
				}
			}
		}
		
		if (arrNodes[n].options.action != null && arrNodes[n].options.action.required == true) {
			arrNodes[n].options.color = optionsGraphUseSkill.groups.blue.color;
			hasChanged = true;
		}
		
		if (!hasChanged) {
			arrNodes[n].options.color = optionsGraphUseSkill.nodes.color;
			
		}
	}
	network.redraw();
}
