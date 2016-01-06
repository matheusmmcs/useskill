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
        scaleFactor: 0.1
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


function drawGraph(type, idContainer, frequentPatterns, sessions, actionsRequired, favorites){
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
	          value: 1
	        });
	        posX++;
	      } else {
	        node.value++;
	        maxVal = node.value > maxVal ? node.value : maxVal; 
	      }
	
	      if (itemAnterior != null) {
	        var idItemAnterior = itemAnterior.idAction;
	
	        var edge = getEdge(idItemAnterior, idItemAtual, arrEdges);
	        if (!edge) {
	          arrEdges.push({
	            from: idItemAnterior, 
	            to: idItemAtual,
	            value: 1
	          });
	        } else {
	          edge.value++;
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
		  for (var f in favorites) {
			  if (arrNodes[n].id == favorites[f]) {
				  arrNodes[n].color = optionsGraphUseSkill.groups.green.color;
			  }
		  }
		  
		  for (var a in actionsRequired) {
			  if (arrNodes[n].id == actionsRequired[a].id) {
				  arrNodes[n].color = optionsGraphUseSkill.groups.blue.color;
			  }
		  }
	  }
	  
	  //construir grafos com as ações do padrão sequencial, mas o tamanho dos nós e das arestas são relativos à quantidade de sessões que realizaram
	  if (type == 'sessions') {
		  //resetar largura de nós
		  for (var n in arrNodes) {
			  arrNodes[n].value = 0;
		  }
		  //arestas serão reconstruídas
		  arrEdges = [];
		  
		  for (var s in sessions) {
			  var actions = sessions[s].actions,
			  	idNodeBefore = null;
			  
			  //console.log('###########');
			  //console.log('SESSION', s, actions);
			  
			  for (var a in actions) {
				  var node = getNode(actions[a].identifier, arrNodes);
				  if (node) {
					  node.value++;
					  //console.log('Node: ', node.id, node.value);
					  
					  if (idNodeBefore != null) {
						  var edge = getEdge(idNodeBefore, node.id, arrEdges);
						  if (!edge) {
					          arrEdges.push({
					            from: idNodeBefore, 
					            to: node.id,
					            value: 1
					          });
					          //console.log('NewEdge: ('+ idNodeBefore + ' -> ' + node.id + ')');
					        } else {
					          edge.value++;
					          //console.log('Edge: ('+ idNodeBefore + ' -> ' + node.id + ') = ' + edge.value);
					        }
					  }
					  
					  idNodeBefore = node.id;
				  }
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
      network.on("selectNode", function (params) {
    	  var node = network.body.nodes[params.nodes[0]];
          console.log("selectNode", node);
          //document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
      });
      network.on("selectEdge", function (params) {
    	  var edge = network.body.edges[params.edges[0]];
          console.log("selectEdge", edge);
          console.log(edge.from.id, edge.to.id, edge.options.value);
      });
      console.log(network);
      
      network.body.edges
      
      return network;
}
