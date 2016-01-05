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


function drawGraph(idContainer, frequentPatterns){
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
	
	  var arrNodes = [], arrEdges = [];
	  for (var pti in frequentPatterns) {
	    var patternAtual = frequentPatterns[pti];
	    console.log(patternAtual);
	    var itemAnterior = null;
	    for (var i in patternAtual.itemsetsFormatted) {
	      var itemAtual = patternAtual.itemsetsFormatted[i],
	        idItemAtual = itemAtual.idAction;
	      
	      console.log(idItemAtual);
	      var node = getNode(idItemAtual, arrNodes);
	
	      if (!node) {
	        arrNodes.push({
	          id: idItemAtual, 
	          label: 'Action '+idItemAtual,
	          value: 1
	        });
	      } else {
	        node.value++;
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
}
