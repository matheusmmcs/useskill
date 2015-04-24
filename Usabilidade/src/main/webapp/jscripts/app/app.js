angular.module('useskill', 
		['ngRoute', 'pascalprecht.translate'])

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
	    .otherwise({
	    	redirectTo:'/'
	    });
	
}])
.factory('HttpInterceptor', ['$q', '$rootScope', '$filter', '$location',
                     function($q, $rootScope, $filter, $location) {
	var responseInterceptor = {
		response: function(response) {
			$rootScope.errors = null;
			$rootScope.success = null;
			console.log(response);
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
        	$location.path(config[env].apiUrl+"/");
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
        
        
        
    };
}])

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
.controller('TaskViewController', function(task) {
	var taskCtrl = this;
	taskCtrl.task = JSON.parse(task.data.string);
})
.controller('TaskNewController', function(test, $filter, ServerAPI) {
	var taskCtrl = this;
	taskCtrl.task = {};
	taskCtrl.test = JSON.parse(test.data.string);
	taskCtrl.actionTitle = $filter('translate')('datamining.tasks.new');
	taskCtrl.save = function() {
		taskCtrl.task.testDataMining = taskCtrl.test;
		var task = angular.toJson(taskCtrl.task);
		ServerAPI.saveTask(task);
	};
})
.controller('TaskEditController', function(task, $filter, ServerAPI) {
	var taskCtrl = this;
	console.log(task);
	taskCtrl.task = JSON.parse(task.data.string);
	taskCtrl.actionTitle = $filter('translate')('datamining.tasks.edit');
	taskCtrl.save = function() {
		var task = angular.toJson(taskCtrl.task);
		ServerAPI.saveTask(task);
	};
})
;
