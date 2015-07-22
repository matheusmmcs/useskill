angular.module('useskill', 
		['ngRoute', 'pascalprecht.translate', 'tableSort', 'chart.js', 'ui.bootstrap'])

.constant('env', 'dev')
//.constant('env', 'prod')
		
.constant('config', {
    appVersion: 0.1,
    dev: {
    	apiUrl: '/Usabilidade'
    },
    prod: {
    	apiUrl: ''
    }
})
		
.config(['$translateProvider', '$routeProvider', '$httpProvider', 'env', 'config',
         function($translateProvider, $routeProvider, $httpProvider, env, config) {
	
	$httpProvider.interceptors.push('HttpInterceptor');
	
	$translateProvider.useUrlLoader(config[env].apiUrl+'/jscripts/app/messages.json');
	$translateProvider.preferredLanguage('pt');
	
	var routeInit = {
		controller:'TestController as testCtrl',
		templateUrl:config[env].apiUrl+'/templates/tests/list.html',
		resolve: {
			tests: function (ServerAPI) {
	        	return ServerAPI.getTests();
	        }
	    }
	};
	
	$routeProvider
		.when('/', routeInit)
		.when('/testes/', routeInit)
		.when('/testes/criar', {
	    	controller:'TestNewController as testCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tests/create.html'
	    })
	    .when('/testes/editar/:testId', {
	    	controller:'TestEditController as testCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tests/create.html',
	    	resolve: {
				test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
		.when('/testes/:testId', {
			controller:'TestViewController as testCtrl',
			templateUrl:config[env].apiUrl+'/templates/tests/detail.html',
			resolve: {
				test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
	    
	    //Tasks
	    
	    .when('/testes/:testId/tarefas/criar', {
	    	controller:'TaskNewController as taskCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tasks/create.html',
	    	resolve: {
				test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
	    .when('/testes/:testId/tarefas/editar/:taskId', {
	    	controller:'TaskEditController as taskCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tasks/create.html',
	    	resolve: {
		        task: function (ServerAPI, $route) {
		        	return ServerAPI.getTask($route.current.params.testId, $route.current.params.taskId);
		        }
		    }
	    })
	    .when('/testes/:testId/tarefas/:taskId', {
	    	controller:'TaskViewController as taskCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tasks/detail.html',
	    	resolve: {
		        task: function (ServerAPI, $route) {
		        	return ServerAPI.getTask($route.current.params.testId, $route.current.params.taskId);
		        }
		    }
	    })
	    .when('/testes/:testId/tarefas/:taskId/avaliar', {
	    	controller:'TaskEvaluateController as taskCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tasks/evaluate.html',
	    	resolve: {
		        task: function (ServerAPI, $route) {
		        	return ServerAPI.getTask($route.current.params.testId, $route.current.params.taskId);
		        },
		        evaluate: function (ServerAPI, $route) {
		        	return ServerAPI.evaluateTask($route.current.params.testId, $route.current.params.taskId);
		        }
		    }
	    })
	    
	    //Actions
	    .when('/testes/:testId/tarefas/:taskId/acoes/add', {
	    	controller:'ActionNewController as actionCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/actions/create.html',
	    	resolve: {
	    		task: function (ServerAPI, $route) {
		        	return ServerAPI.getTask($route.current.params.testId, $route.current.params.taskId);
		        }
		    }
	    })
	    
	    
	    .otherwise({
	    	redirectTo:'/'
	    });
	
}])
.factory('HttpInterceptor', ['$q', '$rootScope', '$filter', '$location', 'env', 'config',
                     function($q, $rootScope, $filter, $location, env, config) {
	var responseInterceptor = {
		response: function(response) {
			$rootScope.errors = null;
			$rootScope.success = null;
			//console.log(response);
			if(response.status === 200 && angular.isDefined(response.data.string)){
				resp = JSON.parse(response.data.string);
				if(resp.status == 'ERRO'){
					$rootScope.errors = resp.errors;
				}else if(resp.status == 'SUCESSO'){
					$rootScope.success = $filter('translate')(resp.message);
				}
			}
            return ( response );
        },
        responseError: function(error) {
        	console.log('FALHOU!', error);
        	//$location.path(config[env].apiUrl+"/");
        	$rootScope.errors = [error.status+' - '+error.statusText];
        }
    };
    return responseInterceptor;
}])
.factory("ServerAPI", ['$q', '$http', 'env', 'config', 
                function($q, $http, env, config){
    return {
        getTests: function(){
            var promise = $http({ method: 'GET', url: config[env].apiUrl+'/datamining/testes'});
            return promise;
        },
        getTest: function(testId){
            var promise = $http({ method: 'GET', url: config[env].apiUrl+'/datamining/testes/'+testId});
            return promise;
        },
        saveTest: function(test){
        	var promise = $http.post(config[env].apiUrl+'/datamining/testes/salvar', test);
        	return promise;
        },
        
        getTask: function(testId, taskId){
            var promise = $http({ method: 'GET', url: config[env].apiUrl+'/datamining/testes/'+testId+'/tarefas/'+taskId});
            return promise;
        },
        saveTask: function(task){
        	var promise = $http.post(config[env].apiUrl+'/datamining/testes/tarefas/salvar', task);
        	return promise;
        },
        evaluateTask: function(testId, taskId){
        	var promise = $http({ method: 'GET', url: config[env].apiUrl+'/datamining/testes/'+testId+'/tarefas/'+taskId+'/avaliar'});
        	return promise;
        },
        
        saveAction: function(actionJHeatVO){
        	var promise = $http.post(config[env].apiUrl+'/datamining/testes/tarefas/acoes/salvar', actionJHeatVO);
        	return promise;
        },
        deleteAction: function(actionJHeatVO, list){
        	var promise = $http.post(config[env].apiUrl+'/datamining/testes/tarefas/acoes/deletar', actionJHeatVO);
        	promise.success(function(data){
        		var resp = JSON.parse(data.string);
    			if(resp.status == 'SUCESSO' && list != undefined){
    				angular.forEach(list, function(obj, idx){
        				if(obj == actionJHeatVO){
        					list.splice(idx, 1);
        				}
        			});
    			}
    		});
        	return promise;
        }
        
        
    };
}])

//Enums

.factory("ActionTypeEnum", function(){
	return {
		types : [
	 	    {name:'Carregamento', value:'onload'},
	 	    {name:'Clique', value:'click'},
	 	    {name:'Envio de Formulário', value:'form_submit'},
	 	    {name:'Preenchimento de Campo', value:'focusout'},
	 	  	{name:'Mouse Sobre', value:'mouseover'},
	 	 	{name:'Voltar', value:'back'},
	 		{name:'Recarregar', value:'reload'}
	 	]
	};
})
.factory("MomentTypeEnum", function(){
	return {
		moments : [
	 	    {name:'Início', value:'START'},
	 	    {name:'Obrigatória (durante a tarefa)', value:'REQUIRED'},
	 	    {name:'Fim', value:'END'}
	 	]
	};
})

//Tests Controllers

.controller('TestController', function(tests) {
	var testCtrl = this;
	testCtrl.tests = JSON.parse(tests.data.string);
})
.controller('TestViewController', function(test) {
	var testCtrl = this;
	testCtrl.test = JSON.parse(test.data.string);
})
.controller('TestNewController', function($filter, ServerAPI) {
	var testCtrl = this;
	testCtrl.test = {};
	testCtrl.actionTitle = $filter('translate')('datamining.testes.new');
	testCtrl.save = function() {
		var test = angular.toJson(testCtrl.test);
		ServerAPI.saveTest(test);
	};
})
.controller('TestEditController', function(test, $filter, ServerAPI) {
	var testCtrl = this;
	console.log(test);
	testCtrl.test = JSON.parse(test.data.string);
	testCtrl.actionTitle = $filter('translate')('datamining.testes.edit');
	testCtrl.save = function() {
		var test = angular.toJson(testCtrl.test);
		ServerAPI.saveTest(test);
	};
})

//Tasks Controllers
.controller('TaskViewController', function($scope, task, ServerAPI, ActionTypeEnum) {
	var taskCtrl = this;
	taskCtrl.task = JSON.parse(task.data.string);
	console.log(taskCtrl.task);
	
	$scope.deleteAction = function(action, list){
		ServerAPI.deleteAction(action, list);
	}
	
	taskCtrl.popup = {
	  options: {
		  html: true,
		  trigger: 'hover',
		  placement: 'bottom'
	  }
	};
	
	angular.forEach(taskCtrl.task.actionsInitial, function(action){
		action.popovercontent = contentPopover(action);
	});
	angular.forEach(taskCtrl.task.actionsEnd, function(action){
		action.popovercontent = contentPopover(action);
	});
	angular.forEach(taskCtrl.task.actionsRequired, function(action){
		action.popovercontent = contentPopover(action);
	});
	
	taskCtrl.getActionTypeName = function(obj){
		var str = "-";
		angular.forEach(ActionTypeEnum.types, function(type){
			if(obj === type.value){
				str = type.name;
			}
		});
		return str;
	}
	
	function contentPopover(action){
		var content = 'Ação = '+action.actionType+'<br/>';
		angular.forEach(action.elementFiedlSearch, function(elem){
			content += elem.field + ' = ' + elem.value + '<br/>';
		});
		angular.forEach(action.urlFieldSearch, function(url){
			content += url.field + ' = ' + url.value + '<br/>';
		});
		return content;
	}
})
.controller('TaskSaveController', function($scope, test, ServerAPI, ActionTypeEnum) {
	this.task = {};
	this.test = test;
	this.actionTypes = ActionTypeEnum.types;
	this.actionTypes = ActionTypeEnum.types;
	this.task.disregardActions = [];

	// toggle selection for a given fruit by name
	this.toggleTypeSelection = function toggleSelection(type) {
	    var idx = this.task.disregardActions.indexOf(type.value);
	    if (idx !== -1) {
	    	this.task.disregardActions.splice(idx, 1);
	    } else {
	    	this.task.disregardActions.push(type.value);
	    }
	};
	
	this.save = function() {
		this.task.testDataMining = this.test;
		var task = angular.toJson(this.task);
		ServerAPI.saveTask(task);
	};
})
.controller('TaskNewController', function($scope, test, $controller, $filter) {
	angular.extend(this, $controller('TaskSaveController', {$scope: $scope, test: JSON.parse(test.data.string)}));
	var taskCtrl = this;
	taskCtrl.actionTitle = $filter('translate')('datamining.tasks.new');
})
.controller('TaskEditController', function($scope, task, $controller, $filter) {
	var task = JSON.parse(task.data.string);
	angular.extend(this, $controller('TaskSaveController', {$scope: $scope, test: task.testDataMining}));
	var taskCtrl = this;
	taskCtrl.task = task;
	taskCtrl.actionTitle = $filter('translate')('datamining.tasks.edit');
})
.controller('TaskEvaluateController', function($scope, task, evaluate, $filter, ServerAPI) {
	var taskCtrl = this;
	
	var modesArr = [{
				name: 'users', 
				title: $filter('translate')('datamining.tasks.evaluate.users.sessions.title'),
				desc: $filter('translate')('datamining.tasks.evaluate.group.users'),
				notAnOption: false
			},{
				name: 'userSessions', 
				title: $filter('translate')('datamining.tasks.evaluate.user.sessions.title'),
				desc: '-',
				notAnOption: true
			},{
				name: 'sessions', 
				title: $filter('translate')('datamining.tasks.evaluate.sessions.title'),
				desc: $filter('translate')('datamining.tasks.evaluate.group.sessions'),
				notAnOption: false
			},{
				name: 'actions', 
				title: $filter('translate')('datamining.tasks.evaluate.user.session.actions.title'),
				desc: '-',
				notAnOption: true
			},{
				name: 'actionsCount', 
				title: $filter('translate')('datamining.tasks.evaluate.actions.count.title'),
				desc: $filter('translate')('datamining.tasks.evaluate.actions.count.title'),
				notAnOption: false
			},{
				name: 'actionsRequiredCount', 
				title: $filter('translate')('datamining.tasks.evaluate.actions.required.count.title'),
				desc: $filter('translate')('datamining.tasks.evaluate.actions.required.count.title'),
				notAnOption: false
	}];
	
	var modes = {
			'users': modesArr[0],
			'userSessions': modesArr[1],
			'sessions': modesArr[2],
			'actions': modesArr[3],
			'actionsCount': modesArr[4]
	}
	
	taskCtrl.modes = modes;
	taskCtrl.modesArr = modesArr;
	taskCtrl.mode = modes.users;
	
	taskCtrl.task = JSON.parse(task.data.string);
	taskCtrl.evaluate = JSON.parse(evaluate.data.string);
	console.log(taskCtrl);
	
	$scope.showUserSessions = function(user){
		var sessions = [];
		for(var i in user.sessionsId){
			var userSession = user.sessionsId[i];
			for(var s in taskCtrl.evaluate.sessions){
				var session = taskCtrl.evaluate.sessions[s];
				if(session.id == userSession){
					sessions.push(session);
					break;
				}
			}
		}
		taskCtrl.userSessions = sessions;
		changeMode('userSessions');
	}
	
	$scope.showActionsSession = function(session){
		taskCtrl.userSession = session;
		changeMode('actions');
	}
	
	$scope.showUsers = function(){
		changeMode('users');
	}
	
	$scope.showSessions = function(){
		changeMode('sessions');
	}
	
	function changeMode(newMode){
		taskCtrl.previousMode = taskCtrl.mode;
		taskCtrl.mode = modes[newMode];
	}
	
	$scope.backMode = function(){
		taskCtrl.mode = taskCtrl.previousMode;
	}
	
	//history graph
	$scope.historyLabels = ["January", "February", "March", "April", "May", "June", "July"];
	$scope.historySeries = ['Series A', 'Series B'];
	$scope.historyData = [
	    [65, 59, 80, 81, 56, 55, 40],
	    [28, 48, 40, 19, 86, 27, 90]
	 ];
	
	//success graph
	$scope.graphSuccessLabels = [
	                             $filter('translate')('datamining.tasks.evaluate.complete'), 
	                             $filter('translate')('datamining.tasks.evaluate.restart'),
	                             $filter('translate')('datamining.tasks.evaluate.threshold')];
    $scope.graphSuccessData = [
                               	taskCtrl.evaluate.countSessionsSuccess, 
                               	taskCtrl.evaluate.countSessionsRepeat,
                               	taskCtrl.evaluate.countSessionsThreshold];
    $scope.graphSuccessColours = ["#46BFBD", "#FDB45C", "#F7464A"];
    $scope.graphSuccessType = 'Pie';
    $scope.graphSuccessToggle = function () {
    	$scope.graphSuccessType = $scope.graphSuccessType === 'Pie' ? 'PolarArea' : 'Pie';
    };
    
})

//Actions Controllers
//ActionsNewController
.controller('ActionViewController', function(action) {
	//todo
})
.controller('ActionNewController', function(task, $filter, ServerAPI, ActionTypeEnum, MomentTypeEnum) {
	var actionCtrl = this;
	actionCtrl.action = {};
	actionCtrl.types = ActionTypeEnum.types;
	actionCtrl.moments = MomentTypeEnum.moments;
	actionCtrl.task = JSON.parse(task.data.string);
	actionCtrl.actionTitle = $filter('translate')('datamining.tasks.actions.add');
	console.log(task.data.string);
	
	actionCtrl.save = function() {
		actionCtrl.action.task = actionCtrl.task;
		if(actionCtrl.action.actionType){
			actionCtrl.action.actionType = actionCtrl.action.actionType.value;
		}
		if(actionCtrl.action.momentType){
			actionCtrl.action.momentType = actionCtrl.action.momentType.value;
		}
		console.log(actionCtrl.action);
		var action = angular.toJson(actionCtrl.action);
		ServerAPI.saveAction(action);
	};
})
.controller('ActionEditController', function(action, $filter, ServerAPI) {
	//todo
})

/* DIRECTIVES */
/* <a href="#" ng-click="confirmClick() && deleteItem(item)" confirm-click>Delete</a> */
.directive('confirmClick', ['$q', 'dialogModal', '$filter', function($q, dialogModal, $filter) {
    return {
        link: function (scope, element, attrs) {
            var ngClick = attrs.ngClick.replace('confirmClick()', 'true')
                .replace('confirmClick(', 'confirmClick(true,');
            // setup a confirmation action on the scope
            scope.confirmClick = function(msg) {
                // if the msg was set to true, then return it (this is a workaround to make our dialog work)
                if (msg===true) {
                    return true;
                }
                // confirm-click attribute on the <a confirm-click="Are you sure?"></a>
                msg = msg || attrs.confirmClick || $filter('translate')('datamining.areyousure');
                // open a dialog modal, and then continue ngClick actions if it's confirmed
                dialogModal(msg).result.then(function() {
                    scope.$eval(ngClick);
                });
                // return false to stop the current ng-click flow and wait for our modal answer
                return false;
            };
        }
    }
}])
.service('dialogModal', ['$modal', '$filter', function($modal, $filter) {
    return function (message, title, okButton, cancelButton) {
        // setup default values for buttons
        // if a button value is set to false, then that button won't be included
        okButton = okButton===false ? false : (okButton || $filter('translate')('datamining.areyousure.confirm'));
        cancelButton = cancelButton===false ? false : (cancelButton || $filter('translate')('datamining.areyousure.cancel'));

        // setup the Controller to watch the click
        var ModalInstanceCtrl = function ($scope, $modalInstance, settings) {
            // add settings to scope
            angular.extend($scope, settings);
            // ok button clicked
            $scope.ok = function () {
                $modalInstance.close(true);
            };
            // cancel button clicked
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        };

        // open modal and return the instance (which will resolve the promise on ok/cancel clicks)
        var modalInstance = $modal.open({
            template: '<div class="dialog-modal"> \
                <div class="modal-header" ng-show="modalTitle"> \
                    <h3 class="modal-title">{{modalTitle}}</h3> \
                </div> \
                <div class="modal-body">{{modalBody}}</div> \
                <div class="modal-footer"> \
                    <button class="btn btn-primary" ng-click="ok()" ng-show="okButton">{{okButton}}</button> \
                    <button class="btn btn-warning" ng-click="cancel()" ng-show="cancelButton">{{cancelButton}}</button> \
                </div> \
            </div>',
            controller: ModalInstanceCtrl,
            resolve: {
                settings: function() {
                    return {
                        modalTitle: title,
                        modalBody: message,
                        okButton: okButton,
                        cancelButton: cancelButton
                    };
                }
            }
        });
        // return the modal instance
        return modalInstance;
    }
}])
.directive('popup', function() {
	  return {
		    restrict: 'A',
		    require: 'ngModel',
		    scope: {
		      ngModel: '=',
		      options: '=popup',
		      popupTitle: '='
		    },
		    link: function(scope, element) {
		      scope.$watch('ngModel', function(val) {
		    	  element.attr('data-content', val);
		      });
		      
		      var options = scope.options || {} ; 
		      var title = (scope.popupTitle || options.title) || null;
		      var placement = options.placement || 'right';
		      var html = options.html || false;
		      var delay = options.delay ? angular.toJson(options.delay) : null;
		      var trigger = options.trigger || 'hover';
		      
		      element.attr('title', title);
		      element.attr('data-placement', placement);
		      element.attr('data-html', html);
		      element.attr('data-delay', delay);
		      element.popover({ trigger: trigger });
		    }
	  };
})

/* FILTER */

.filter('msConverter', function() {
  return function(n) {
	  n = Math.round(n);
	  var minutes = Math.floor(n / 60000);
	  var seconds = ((n % 60000) / 1000).toFixed(0);
	  return minutes + "m, " + (seconds < 10 ? '0' : '') + seconds + "s"; 
  }
})
.filter('cut', function () {
    return function (value, wordwise, max, tail) {
        if (!value) return '';

        max = parseInt(max, 10);
        if (!max) return value;
        if (value.length <= max) return value;

        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace != -1) {
                value = value.substr(0, lastspace);
            }
        }

        return value + (tail || ' …');
    };
});
