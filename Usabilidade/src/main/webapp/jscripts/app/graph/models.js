/* Graph */

function Graph () {
  this.nodes = [];
  this.edges = [];
  this.countIdNode = 1;
  this.countIdEdge = 1;
}

Graph.prototype.searchNode = function (nodeLabel) {
  for(var i in this.nodes){
    if(this.nodes[i].label === nodeLabel){
      return this.nodes[i];
    }
  }
  return null;
}

Graph.prototype.addNode = function (opts) {
  var node = this.searchNode(opts.label);
  if(node ===  null){
    node = new Node(this.countIdNode, opts);
    this.nodes.push(node);
    this.countIdNode++;
  }else{
    node.value++;
  }
}

Graph.prototype.searchEdge = function (fromId, toId) {
  for(var i in this.edges){
    if(this.edges[i].from === fromId && this.edges[i].to === toId){
      return this.edges[i];
    }
  }
  return null;
}

Graph.prototype.addEdge = function (fromLabel, toLabel) {
  var nodeFrom = this.searchNode(fromLabel);
  var nodeTo = this.searchNode(toLabel);

  if(nodeFrom ===  null || nodeTo === null){
    console.error('nodes of edge doesnt exists')
  }else{
    var edge = this.searchEdge(nodeFrom.id, nodeTo.id);
    if(edge === null){
      edge = new Edge(nodeFrom.id, nodeTo.id);
      this.edges.push(edge);
      this.countIdEdge++;
    }else{
      edge.width++;
    }
  }
}

/* Node */

function Node (id, opts) {
  this.id = id;
  this.value = opts.value ? opts.value : 1;
  this.label = opts.label;
  this.title = opts.title;

  this.allowedToMoveY = opts.allowedToMoveY !== undefined ? opts.allowedToMoveY : true;
  this.allowedToMoveX = opts.allowedToMoveX !== undefined ? opts.allowedToMoveX : false;
  this.x = opts.x !== undefined ? opts.x * 120 : undefined;
  this.y = opts.y !== undefined ? opts.y : undefined;

  this.group = opts.group;
}

function Edge (from, to) {
  this.from = from;
  this.to = to;
  this.width = 1;
  this.title = 'Title';
}

var container = document.getElementById('useskill-network');

var options = {
  stabilize: false,
  freezeForStabilization: true,
  clustering: false,
  clickToUse: true,
  hover: true,
  physics: {
    barnesHut:{
      springLength:150
    }
  },
  smoothCurves: {
    dynamic: true,
    type: 'continuous',
    roundness: 0.5
  },
  edges: {
    style: 'arrow',
    color: 'gray',
    arrowScaleFactor: 1
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
    }
  },
  tooltip: {
    delay: 300,
    fontColor: "#426392",
    fontSize: 12, // px
    fontFace: "verdana",
    color: {
      border: "#426392",
      background: "#dee5f0"
    }
  }
};

function drawGraph(){
	var graph = new Graph();
	graph.addNode({label: 'Init', x: 0, y: 0, allowedToMoveY: false, group: 'black'});
    graph.addNode({label: 'Page1-Elem1', x: 1, group: 'green', value: 20});
    graph.addNode({label: 'Page1-Elem2', x: 2, group: 'green', value: 7});
    graph.addNode({label: 'Page1-Elem3', x: 2, group: 'red', value: 13});
    graph.addNode({label: 'Page1-Elem4', x: 3, group: 'blue', value: 13});
    graph.addNode({label: 'Page1-Elem5', x: 4, group: 'green', value: 17});
    graph.addNode({label: 'Page1-Elem6', x: 4, group: 'blue', value: 3});
    graph.addNode({label: 'Page2-Elem1', x: 5, group: 'red', value: 20});
    graph.addNode({label: 'Page2-Elem2', x: 5, group: 'green', value: 7});
    graph.addNode({label: 'Page2-Elem3', x: 6, group: 'blue', value: 13});
    graph.addNode({label: 'Page3-Elem1', x: 7, group: 'red', value: 13});
    graph.addNode({label: 'Page3-Elem2', x: 7, group: 'green', value: 17});
    graph.addNode({label: 'Page3-Elem3', x: 8, group: 'blue', value: 3});
    graph.addNode({label: 'End', x: 9, y: 0, allowedToMoveY: false, group: 'black'});

    graph.addEdge('Init', 'Page1-Elem1');
    graph.addEdge('Page1-Elem1', 'Page1-Elem2');
    graph.addEdge('Page1-Elem1', 'Page1-Elem2');
    graph.addEdge('Page1-Elem1', 'Page1-Elem3');
    graph.addEdge('Page1-Elem2', 'Page1-Elem5');
    graph.addEdge('Page1-Elem3', 'Page1-Elem4');
    graph.addEdge('Page1-Elem4', 'Page1-Elem5');
    graph.addEdge('Page1-Elem4', 'Page1-Elem6');
    graph.addEdge('Page1-Elem2', 'Page1-Elem6');
    graph.addEdge('Page1-Elem6', 'Page2-Elem1');
    graph.addEdge('Page1-Elem6', 'Page2-Elem2');
    graph.addEdge('Page1-Elem5', 'Page2-Elem2');
    graph.addEdge('Page2-Elem2', 'Page2-Elem3');
    graph.addEdge('Page2-Elem2', 'Page3-Elem2');
    graph.addEdge('Page2-Elem3', 'Page3-Elem1');
    graph.addEdge('Page2-Elem3', 'Page3-Elem3');
    graph.addEdge('Page3-Elem2', 'Page3-Elem3');
    graph.addEdge('Page3-Elem3', 'End');
    graph.addEdge('Page3-Elem2', 'End');

    graph.addEdge('Page1-Elem4', 'Page1-Elem1');
    
    var data = {
        nodes: graph.nodes,
        edges: graph.edges
    };
    
    var network = new vis.Network(container, data, options);

    network.on('hoverNode', function (props) {
      console.log('hoverNode', props);
    });

    network.on('select', function (props) {
      console.log('select', props);
    });
}

drawGraph();