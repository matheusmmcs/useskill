angular.module('useskill', 
		['ngRoute', 
		 'ngAnimate',
		 'ngResource',
		 'pascalprecht.translate', 
		 'tableSort', 
		 'chart.js', 
		 'datePicker', 
		 'ui.bootstrap', 
		 'angularMoment', 
		 'angular-loading-bar',
		 'nvd3'
		 ])

.constant('env', 'dev')
//.constant('env', 'prod')
		
.constant('config', {
    appVersion: 0.1,
    dev: {
    	apiUrl: '/Usabilidade'
    },
    prod: {
    	apiUrl: '/useskill'
    }
})
		
.config(['$translateProvider', '$routeProvider', '$httpProvider', 'env', 'config', 'cfpLoadingBarProvider',
         function($translateProvider, $routeProvider, $httpProvider, env, config, cfpLoadingBarProvider) {
	
	$httpProvider.interceptors.push('HttpInterceptor');

	cfpLoadingBarProvider.spinnerTemplate = '<div class="loader__bg usdm-animate-show-hide"><span class="loader__img"/></div>';
	
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
	    	templateUrl:config[env].apiUrl+'/templates/tests/create.html',
	    	resolve: {
				testsControl: function (ServerAPI) {
		        	return ServerAPI.getTestsControl();
		        }
		    }
	    })
	    .when('/testes/editar/:testId', {
	    	controller:'TestEditController as testCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tests/create.html',
	    	resolve: {
				test: function (ServerAPI, $route, $rootScope) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        },
		        testsControl: function (ServerAPI) {
		        	return ServerAPI.getTestsControl();
		        }
		    }
	    })
		.when('/testes/:testId', {
			controller:'TestViewController as testCtrl',
			templateUrl:config[env].apiUrl+'/templates/tests/detail.html',
			resolve: {
				test: function (ServerAPI, $route, $rootScope) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
	    .when('/testes/:testId/maisacessadas', {
			controller:'TestMostAccessController as testCtrl',
			templateUrl:config[env].apiUrl+'/templates/tests/mostaccess.html',
			resolve: {
		        test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
	    .when('/testes/:testId/sessaoespecifica', {
			controller:'TestSpecificSessionController as testCtrl',
			templateUrl:config[env].apiUrl+'/templates/tests/viewsession.html',
			resolve: {
		        test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
	    .when('/testes/:testId/tarefas', {
			controller:'TestTasksDatesViewController as testCtrl',
			templateUrl:config[env].apiUrl+'/templates/tests/tasklistdates.html',
			resolve: {
				test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        }
		    }
	    })
	    .when('/testes/:testId/avaliar/:evalId', {
			controller:'TestTasksViewController as testCtrl',
			templateUrl:config[env].apiUrl+'/templates/tests/tasklist.html',
			resolve: {
				test: function (ServerAPI, $route) {
		        	return ServerAPI.getTest($route.current.params.testId);
		        },
		        evalTest: function (ServerAPI, $route) {
		        	return ServerAPI.getEvaluationTest($route.current.params.testId, $route.current.params.evalId);
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
	    .when('/testes/:testId/avaliacao/:evalTestId/tarefas/:taskId/avaliar', {
	    	controller:'TaskEvaluateController as taskCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tasks/evaluate.html',
	    	resolve: {
		        evaluate: function (ServerAPI, $route) {
		        	return ServerAPI.evaluateTask($route.current.params.testId, $route.current.params.evalTestId, $route.current.params.taskId);
		        },
		        evalTestId: function ($route) {
		        	return $route.current.params.evalTestId;
		        }
		    },
		    data: {
		        longLoading: true
		    }
	    })
	    .when('/testes/:testId/avaliacao/:evalTestId/tarefas/:taskId/avaliar/minsup/:minSup/minitens/:minItens', {
	    	controller:'TaskEvaluateController as taskCtrl',
	    	templateUrl:config[env].apiUrl+'/templates/tasks/evaluate.html',
	    	resolve: {
		        evaluate: function (ServerAPI, $route) {
		        	return ServerAPI.evaluateTaskParams($route.current.params.testId, $route.current.params.evalTestId, $route.current.params.taskId,
		        									$route.current.params.minSup, $route.current.params.minItens);
		        },
		        evalTestId: function ($route) {
		        	return $route.current.params.evalTestId;
		        }
		    },
		    data: {
		        longLoading: true
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
.factory('HttpInterceptor', ['$q', '$rootScope', '$filter', '$location', 'env', 'config', '$timeout',
                     function($q, $rootScope, $filter, $location, env, config, $timeout) {
	var responseInterceptor = {
		response: function(response) {
			$rootScope.errors = null;
			$rootScope.success = null;
			//console.log(response);
			if(response.status === 200 && angular.isDefined(response.data.string)){
				resp = JSON.parse(response.data.string);
				if(resp.status == 'ERRO'){
					$rootScope.errors = [];
					if (resp.message) {
						console.log($filter('translate')(resp.message));
						console.log(resp.message);
						$rootScope.errors.push($filter('translate')(resp.message));
					}
					$rootScope.errors.concat(resp.errors);
					console.log($rootScope.errors);
				}else if(resp.status == 'SUCESSO'){
					$rootScope.success = $filter('translate')(resp.message);
				}
			}
			
			if($rootScope.mgsRealod){
				$rootScope.success = $rootScope.success != null ? $rootScope.success + ". " + $rootScope.mgsRealod.success : $rootScope.mgsRealod.success;
				$rootScope.error = $rootScope.error != null ? $rootScope.error + ". " + $rootScope.mgsRealod.error : $rootScope.mgsRealod.error;
				//gambis para evitar apagar msgReload antes de carregar
				setTimeout(function(){$rootScope.mgsRealod = null;},100);
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
.factory("ShowMessage", ['$rootScope', '$filter', function($rootScope, $filter){
	var showMessage = {
		clear: function () {
			$rootScope.success = null;
			$rootScope.errors = null;
		},
		success: function (msg) {
			showMessage.clear();
			if (msg) {
				$rootScope.success = $filter('translate')(msg);
			}
		},
		error: function (error) {
			showMessage.clear();
			if (error) {
				var msgs = [];
				msgs.push(error);
				showMessage.errors(msgs);
			}
		},
		errors: function (errors) {
			showMessage.clear();
			$rootScope.errors = [];
			if (errors) {
				for (var i in errors) {
					var msg = errors[i];
					console.log('error: ', msg, $filter('translate')(msg));
					$rootScope.errors.push($filter('translate')(msg));
				}
			}
			$rootScope.errors.concat(resp.errors);
		}
	};
	
	return showMessage;
}])
.factory("ServerAPI", ['$rootScope', '$q', '$http', 'env', 'config', 'cfpLoadingBar',
                function($rootScope, $q, $http, env, config, cfpLoadingBar) {
	
	function doRequest(type, url, data) {
		var deferred = $q.defer();
		
		$rootScope.onRequest = true;
		cfpLoadingBar.start();
		
		var req, reqUrl = config[env].apiUrl + url;
		
		if (type.toLowerCase() == 'post') {
			req = $http.post(reqUrl, data);
		} else {
			req = $http({ method: type, url: reqUrl });
		}
		
		req.then(function successCallback(response) {
			console.log('request ok: ', response);
			deferred.resolve(response);
		}, function errorCallback(response) {
			console.log('request error: ', response);
			
			deferred.reject(response);
		}).finally(function(){
			$rootScope.onRequest = false;
			cfpLoadingBar.complete();
		});
		return deferred.promise;
	};
	
    return {
    	getTests: function(){
            return doRequest('GET', '/datamining/testes');
        },
        getTest: function(testId){
            return doRequest('GET', '/datamining/testes/'+testId);
        },
        priorityTest: function(testId){
            return doRequest('GET', '/datamining/testes/'+testId+'/priorizar');
        },
        saveTest: function(test){
        	return doRequest('POST', '/datamining/testes/salvar', test);
        },
        getTestMostAccess: function(testId, field, init, end){
            return doRequest('GET', '/datamining/testes/'+testId+'/maisacessados/'+field+'/init/'+init+'/end/'+end);
        },
        getSpecificSession: function(testId, user, local, init, end, limit){
            return doRequest('GET', '/datamining/testes/'+testId+'/sessaoespecifica/'+user+'/local/'+local+'/init/'+init+'/end/'+end+'/limit/'+limit);
        },
        
        
        saveNewEvaluationTest: function(evaluationTest){
        	return doRequest('POST', '/datamining/testes/newevaluationtest', evaluationTest);
        },
        getEvaluationTest: function(testId, evalId) {
        	return doRequest('GET', '/datamining/testes/'+testId+'/avaliacao/'+evalId);
        },
        
        
        getTask: function(testId, taskId) {
            return doRequest('GET', '/datamining/testes/'+testId+'/tarefas/'+taskId);
        },
        saveTask: function(task) {
        	return doRequest('POST', '/datamining/testes/tarefas/salvar', task);
        },
        evaluateTask: function(testId, evalTestId, taskId){
        	return doRequest('GET', '/datamining/testes/'+testId+'/avaliacao/'+evalTestId+'/tarefas/'+taskId+'/avaliar');
        },
        evaluateTaskParams: function(testId, evalTestId, taskId, minSup, minItens){
        	return doRequest('GET', '/datamining/testes/'+testId+'/avaliacao/'+evalTestId+'/tarefas/'+taskId+'/avaliar/minsup/'+minSup+'/minitens/'+minItens);
        },
        
        
        saveAction: function(actionJHeatVO){
        	return doRequest('POST', '/datamining/testes/tarefas/acoes/salvar', actionJHeatVO);
        },
        deleteAction: function(actionJHeatVO, list){
        	var promise = $http.post(config[env].apiUrl+'/datamining/testes/tarefas/acoes/deletar', { actionId: actionJHeatVO.id });
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
        },
        
        //CONTROL
        getTestsControl: function(){
        	return doRequest('GET', '/datamining/testes/control');
        },
        updateTaskControl: function(testId){
        	return doRequest('GET', '/datamining/testes/control/'+testId+'/taskupdate');
        },
        saveActionSituation: function(testId, evalTestId, taskId, actions){
        	var data = {
        		testId: testId, 
        		evalTestId: evalTestId, 
        		taskId: taskId, 
        		actions: actions
        	}
        	return doRequest('POST', '/datamining/testes/avaliacao/tarefas/saveActionSituation', data);
        }
        
    };
}])
.factory("ValidateService", function(){
	var validate = {
		isNumeric: function(n) {
			return !isNaN(parseFloat(n)) && isFinite(n);
		},
		task : function(task) {
			if (task.title != null && task.title != "" &&
				task.threshold != null && validate.isNumeric(task.threshold)) {
				return true;
			}
			return false;
		},
		action: function(action) {
			if (action != null &&
				action.momentType != null && action.momentType != ""  &&
				action.actionType != null && action.actionType != ""  &&
				action.description != null && action.description != ""  &&
				action.element != null && action.element != ""  &&
				action.url != null && action.url != "") {
				return true;
			}
			return false;
			
		}
	};
	return validate;
})
.factory("UtilsService", ['ValidateService', function(ValidateService){
	var utils = {
		formatDate : function(date) {
			return moment(date, 'MMM DD, YYYY hh:mm:ss A').format('DD/MM/YYYY, HH:mm:ss');
		},
		datepickerToTimestamp: function(n) {
			if (!ValidateService.isNumeric(n)) {
				var time = moment(n, "DD-MM-YYYY HH:mm");
				return time.valueOf();
			} else {
				return n;
			}
		}
	};
	return utils;
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
.factory("MostAccessTypeEnum", function(){
	return {
		datatypes : [
	 	    {name:'JHM', value:'sJhm'},
	 	    {name:'URL', value:'sUrl'}
	 	]
	};
})


//Tests Controllers

.controller('TestController', function(tests) {
	var testCtrl = this;
	testCtrl.tests = JSON.parse(tests.data.string);
})
.controller('TestViewController', function(test, ServerAPI, $route, $rootScope, $filter) {
	var testCtrl = this;
	testCtrl.test = JSON.parse(test.data.string);
})
.controller('TestTasksDatesViewController', function($filter, test, ServerAPI, UtilsService) {
	var testCtrl = this;
	testCtrl.test = JSON.parse(test.data.string);
	console.log(testCtrl.test);
	testCtrl.actionTitle = $filter('translate')('datamining.testes.evaluations');
	
	testCtrl.showTasks = false;
	testCtrl.showEvaluations = false;
	
	testCtrl.minDate = new Date().getTime();
	testCtrl.maxDate = new Date().getTime();
	
	testCtrl.formatDate = UtilsService.formatDate;
	
	testCtrl.createDates = function(){
		var minDate = UtilsService.datepickerToTimestamp(testCtrl.minDate);
		var maxDate = UtilsService.datepickerToTimestamp(testCtrl.maxDate);
		
		var newEval = angular.toJson({
			idTeste: testCtrl.test.id, 
			initDate: minDate, 
			endDate: maxDate
		});
		
		ServerAPI.saveNewEvaluationTest(newEval).then(function(data) {
			console.log(data);
			testCtrl.test = JSON.parse(data.data.string);
		}, function(data) {
			console.log(data);
		});
	}
	
	testCtrl.updateTasksControl = function(){
		ServerAPI.updateTaskControl(testCtrl.test.id).then(function(data){
			console.log(data);
			testCtrl.test = JSON.parse(data.data.string);
		}, function(data) {
			console.log(data);
		});
	}
	
})
.controller('TestTasksViewController', function(test, evalTest, ServerAPI, $route, $scope, $rootScope, $filter, UtilsService) {
	var testCtrl = this;
	
	testCtrl.test = JSON.parse(test.data.string);
	testCtrl.evalTest = JSON.parse(evalTest.data.string);
	
	angular.forEach(testCtrl.evalTest.evaluationsTask, function(eval){
		angular.forEach(testCtrl.test.tasks, function(task){
			if (task.id == eval.idTaskDataMining) {
				task.actualEvaluation = eval;
			}
		});
	});
	
	testCtrl.formatDate = UtilsService.formatDate;
	
	testCtrl.popup = {
		  options: {
			  html: true,
			  trigger: 'hover',
			  placement: 'bottom'
		  }
	};
	
	angular.forEach(testCtrl.test.tasks, function(task){
		task.popovercontent = contentPopover(task);
	});
	
	function reduceNumber(n){
		return $filter('number')(n, 2);
	}
	
	//TODO: melhoria no método, renderizando um popup com html
	function contentPopover(task){
		var content = "";
		task = task.actualEvaluation;
		if (task) {
			content = 'Data da Avaliação = '+UtilsService.formatDate(task.evalLastDate)+'<br/>';
			content += 'Média de Ações = '+reduceNumber(task.evalMeanActions)+' [z='+reduceNumber(task.evalZScoreActions)+']<br/>';
			content += 'Média de Tempos = '+reduceNumber(task.evalMeanTimes)+', [z='+reduceNumber(task.evalZScoreTime)+']<br/>';
			content += 'Completude = '+reduceNumber(task.evalMeanCompletion)+', Exatidão = '+reduceNumber(task.evalMeanCorrectness)+'<br/>';
			
			content += 'Sessões = '+reduceNumber(task.evalCountSessions)+', ['+reduceNumber(task.evalCountSessionsNormalized)+']<br/>';
			//content += 'Eficácia = (Completude * Exatidão)/100<br/>';
			content += 'Eficácia = '+reduceNumber(task.evalEffectiveness)+', ['+reduceNumber(task.evalEffectivenessNormalized)+']<br/>';
			//content += 'Eficiência = Eficácia / ((AçõesZscore + TemposZscore)/2)<br/>';
			content += 'Eficiência = '+reduceNumber(task.evalEfficiency)+', ['+reduceNumber(task.evalEfficiencyNormalized)+']<br/>';
			content += 'Prioridade (Fuzzy) = '+reduceNumber(task.evalFuzzyPriority)+'<br/>';
			content += "Tempo Estimado da Avaliação = " + $filter('msConverter')(task.meanTimeLoading || 0);
		} else {
			content = $filter('translate')('datamining.testes.evaluations.none');
		}
		return content;
	}
	
	$scope.setLongLoaderTime = function(meanTimeLoading){
		if (meanTimeLoading) {
			$rootScope.longLoadingTime = meanTimeLoading/1000;
		}
	}
})
.controller('TestMostAccessController', function($scope, $filter, $timeout, test, MostAccessTypeEnum, ServerAPI, UtilsService) {
	var testCtrl = this;
	testCtrl.actionTitle = $filter('translate')('datamining.testes.featuresmostaccessed.search');
	testCtrl.test = JSON.parse(test.data.string);
	testCtrl.testId = testCtrl.test.id;
	
	testCtrl.minDate = new Date().getTime();
	testCtrl.maxDate = new Date().getTime();
	
	testCtrl.actions = null;
	testCtrl.datatypes = MostAccessTypeEnum.datatypes;
	
	
	
	testCtrl.mostaccess = function() {
		
		var minDate = UtilsService.datepickerToTimestamp(testCtrl.minDate);
		var maxDate = UtilsService.datepickerToTimestamp(testCtrl.maxDate);
		
		if (testCtrl.datatype !== undefined) {
			console.log(testCtrl.testId, testCtrl.datatype.value, new Date(minDate), new Date(maxDate));
			ServerAPI.getTestMostAccess(testCtrl.testId, testCtrl.datatype.value, minDate, maxDate).then(function(data){
				console.log(data.data);
				testCtrl.actions = JSON.parse(data.data.string);
				
				//sort actions desc
				testCtrl.actions.sort(function(a, b) {
				    return b.count - a.count;
				});
				
				$scope.pieOptions = {
		            chart: {
		                type: 'pieChart',
		                height: 300,
		                x: function(d){
		                	return d.description;
		                },
		                y: function(d){
		                	return d.count;
		                },
		                //color: ['#0D3C55', '#0F5B78', '#117899', '#1395BA', '#5CA793', '#A5A8AA'],
		                showLabels: false,
		                transitionDuration: 500,
		                labelThreshold: 0.01,
		                showLegend: true,
		                legend: {
		                    margin: {
		                        top: 5,
		                        right: 35,
		                        bottom: 5,
		                        left: 0
		                    }
		                },
		                tooltip: {
		                	contentGenerator: function(obj) {
		                        return '<h4>' + obj.data.description + '</h4>' +'<p>' + obj.data.count + '</p>';
		                    }
		                },
		            }
		        };
				
				var MIN = 3, MAX = 25;
				$scope.sizePie = 5;
				$scope.canChange = true;
				
				$scope.renderPieMost = function(size) {
					size = size == null ? 5 : size;
					size = size < MIN ? MIN : size;
					size = size > MAX ? MAX : size;
					
					if ($scope.canChange) {
						$scope.canChange = false;
						//best 5
						$scope.pieData = testCtrl.actions.slice(0,size);
						
						//others
						var sum = 0;
						angular.forEach(testCtrl.actions.slice(size), function(elem){
							sum += elem.count;
						});
						$scope.pieData.push({
							description: $filter('translate')('others...'), 
							count: sum
						});
						
						//rerender
						$timeout(function(){
							$scope.pieApi.refresh();
							$timeout(function(){ $scope.canChange = true; }, 1000);
						});
					}
				}
				
				$scope.renderPieMost($scope.sizePie);
				
				
			}, function(data){
				console.log(data);
			});
		}
	}
	
})
.controller('TestSpecificSessionController', function($filter, test, MostAccessTypeEnum, ServerAPI, UtilsService) {
	var testCtrl = this;
	testCtrl.actionTitle = $filter('translate')('datamining.testes.specificsession.search');
	testCtrl.test = JSON.parse(test.data.string);
	testCtrl.testId = testCtrl.test.id;
	
	testCtrl.minDate = new Date().getTime();
	testCtrl.maxDate = new Date().getTime();
	
	testCtrl.actions = [];
	testCtrl.specificSession = function() {
		var minDate = UtilsService.datepickerToTimestamp(testCtrl.minDate);
		var maxDate = UtilsService.datepickerToTimestamp(testCtrl.maxDate);
		
		console.log(testCtrl.testId, testCtrl.username, minDate, maxDate, testCtrl.limit);
		ServerAPI.getSpecificSession(testCtrl.testId, testCtrl.username, testCtrl.local, minDate, maxDate, testCtrl.limit).then(function(data){
			testCtrl.actions = JSON.parse(data.data.string);
			console.log(testCtrl.actions);
		}, function(data){
			console.log(data);
		});
	}
	//getSpecificSession
})
.controller('TestNewController', function($filter, ServerAPI, testsControl) {
	var testCtrl = this;
	testCtrl.test = {};
	testCtrl.actionTitle = $filter('translate')('datamining.testes.new');
	testCtrl.testsControl = JSON.parse(testsControl.data.string);
	console.log(testCtrl);
	testCtrl.save = function() {
		var test = angular.toJson({
			'test' : testCtrl.test,
			'testConrolId' : testCtrl.testControl
		});
		ServerAPI.saveTest(test);
	};
})
.controller('TestEditController', function(test, $filter, ServerAPI, testsControl) {
	var testCtrl = this;
	console.log(test);
	testCtrl.test = JSON.parse(test.data.string);
	testCtrl.actionTitle = $filter('translate')('datamining.testes.edit');
	testCtrl.testsControl = JSON.parse(testsControl.data.string);
	testCtrl.save = function() {
		var test = angular.toJson({
			'test' : testCtrl.test,
			'testConrolId' : testCtrl.testControl
		});
		ServerAPI.saveTest(test);
	};
})

//Tasks Controllers
.controller('TaskViewController', function($scope, task, ServerAPI, ActionTypeEnum, $rootScope) {
	var taskCtrl = this;
	taskCtrl.task = JSON.parse(task.data.string);
	console.log(taskCtrl.task);
	
	$scope.deleteAction = function(action, list){
		ServerAPI.deleteAction(action, list);
	}
	
	$scope.selectAction = function(action) {
		$scope.actionSelectedContent = contentPopover(action);
	}
	
	//gambys para a apresentação
	jQuery('.popover').remove();
	
	taskCtrl.popup = {
	  options: {
		  html: true,
		  trigger: 'hover',
		  placement: 'top'
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
.controller('TaskSaveController', function($scope, test, ServerAPI, ActionTypeEnum, ShowMessage, ValidateService) {
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
		if (ValidateService.task(this.task)) {
			var task = angular.toJson({
				'task' : this.task
			});
			ServerAPI.saveTask(task);
		} else {
			ShowMessage.error('datamining.validate.default');
		}
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
.controller('TaskEvaluateController', function($scope, evaluate, evalTestId, $filter, ServerAPI, $timeout, $sce) {
	var taskCtrl = this;
	
	taskCtrl.evalTestId = evalTestId;
	var map = evaluate.data.string;
	map = JSON.parse( map );
	
	console.log(map);
	
	for (var i in map) {
		taskCtrl[i] = map[i];
	}
	
	console.log("init taskEvaluate: ", evaluate, evalTestId);
	console.log(taskCtrl);
	
	//gambys para a apresentação
	jQuery('.popover').remove();
	
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
				notAnOption: true
			},{
				name: 'actionsRequiredCount', 
				title: $filter('translate')('datamining.tasks.evaluate.actions.required.count.title'),
				desc: $filter('translate')('datamining.tasks.evaluate.actions.required.count.title'),
				notAnOption: false
			},{
				name: 'frequentPatterns', 
				title: $filter('translate')('datamining.patterns'),
				desc: $filter('translate')('datamining.patterns'),
				notAnOption: false
			},{
				name: 'pattern', 
				title: $filter('translate')('datamining.pattern'),
				desc: $filter('translate')('datamining.pattern'),
				notAnOption: true
	}];
	
	var modes = {
			'users': modesArr[0],
			'userSessions': modesArr[1],
			'sessions': modesArr[2],
			'actions': modesArr[3],
			'pattern': modesArr[7]
	}
	
	taskCtrl.modes = modes;
	taskCtrl.modesArr = modesArr;
	taskCtrl.mode = modes.users;
	taskCtrl.actionsSituation = {};
	taskCtrl.actionsSpecialSituation = {};
	taskCtrl.actionsSituationChanged = {};
	taskCtrl.actionsSpecialSituationChanged = {};
	
	
	//count actions
	var actionsArr = $filter('toArray')(taskCtrl.result.pageViewActionIds),
		maxCount = 0;
	angular.forEach(actionsArr, function(action){
		action.count = taskCtrl.result.pageViewActionCount[action.$key];
		if(maxCount < action.count){
			maxCount = action.count;
		}
	});
	
	taskCtrl.actionsMaxCount = maxCount;
	taskCtrl.actionsArr = actionsArr;
	taskCtrl.actionsRequiredArr = $filter('toArray')(taskCtrl.result.actionsRequiredTask);
	for (var i in taskCtrl.actionsRequiredArr) {
		var act = taskCtrl.actionsRequiredArr[i];
		for (var a in actionsArr) {
			if (actionsArr[a].value == act.$key) {
				taskCtrl.actionsRequiredArr[i].id = actionsArr[a].$key;
				break;
			}
		}
	}
	
	//adjust frequentPatterns
	angular.forEach(taskCtrl.frequentPatterns, function(fp, keyFp){
		fp.key = keyFp;
		fp.itemsetsFormatted = [];
		fp.sessionsFormatted = [];
		fp.effectivenessMean = 0;
		fp.efficiencyMean = 0;
		fp.successMean = 0;
		fp.requiredMean = 0;
		fp.usersSessions = {};
		
		angular.forEach(fp.itemsets, function(itset, keyItset){
			angular.forEach(itset.items, function(item){
				//update action referente to frequentpatterns and itemsets
				var action = {
					idAction: item,
					desc: taskCtrl.result.pageViewActionIds[item],
					count: taskCtrl.result.pageViewActionCount[item],
					itemset: keyItset
				};
				//actionsArr
				fp.itemsetsFormatted.push(action);
			});
		});
		
		angular.forEach(fp.sequencesIds, function(seq){
			var sess = taskCtrl.result.sessions[seq];
			fp.sessionsFormatted.push(sess);
			
			fp.effectivenessMean += (sess.effectivenessNormalized * 100);
			fp.efficiencyMean += (sess.efficiencyNormalized * 100);
			fp.successMean += sess.userRateSuccess;
			fp.requiredMean += sess.userRateRequired;
			
			if (fp.usersSessions[sess.username] === undefined) {
				fp.usersSessions[sess.username] = {
					count: 0,
					sessions: []
				};
			}
			fp.usersSessions[sess.username].count++;
			fp.usersSessions[sess.username].sessions.push(sess.id);
		});
		
		fp.effectivenessMean = fp.effectivenessMean / fp.sequencesIds.length;
		fp.efficiencyMean = fp.efficiencyMean / fp.sequencesIds.length;
		fp.successMean = fp.successMean / fp.sequencesIds.length;
		fp.requiredMean = fp.requiredMean / fp.sequencesIds.length;
		fp.itemsetsFormattedText = fp.itemsetsFormatted.map(function(elem){
		    return elem.idAction;
		}).join(", ");
	});
	
	
	/************ Classifications ************/
	
	$scope.classificationEnum = {
		success : {
			id: "SUCCESS",
			desc: $filter('translate')('datamining.tasks.evaluate.complete'),
			val: 0,
			classLabel: 'label-success'
		},
		threshold : {
			id: "THRESHOLD",
			desc: $filter('translate')('datamining.tasks.evaluate.threshold'),
			val: 2,
			classLabel: 'label-important'
		},
		repeat : {
			id: "REPEAT",
			desc: $filter('translate')('datamining.tasks.evaluate.restart'),
			val: 1,
			classLabel: 'label-warning'
		},
		error : {
			id: "ERROR",
			desc: $filter('translate')('datamining.tasks.evaluate.error'),
			val: 3,
			classLabel: 'label-important'
		}
	}
	
	$scope.getClassificationFromEnum = function(classif) {
		for (var c in $scope.classificationEnum) {
			if ($scope.classificationEnum[c].id == classif) {
				return $scope.classificationEnum[c];
			}
		}
		return {
			id: "-",
			desc: '-',
			val: 9
		};
	}
	
	/************ Sitations ************/
	$scope.situationsEnum = {
    	OK: {
    		id: 'ok',
    		desc: 'Correta',
    		group: 'green',
    		classLabel: 'label-success'
    	},
    	ERROR: {
    		id: 'error',
    		desc: 'Incorreta',
    		group: 'red',
    		classLabel: 'label-important'
    	},
    	DEFAULT: {
    		id: 'default',
    		desc: 'Sem Situação Definida',
    		classLabel: ''
    	}
    }
	$scope.resetSendDataChange = function(){
		$scope.sendDataChange = false;
	}
	$scope.resetAlertChangeTask = function(){
		$scope.alertChangeTask = false;
	}
	function resetSituationAux(actionId) {
		var sit = taskCtrl.actionsSituation[actionId];
		$scope.situationAux = sit !== undefined ? sit : $scope.situationsEnum.DEFAULT;
	}
	$scope.setSituationAction = function(actionId, situation) {
		for (var i in $scope.situationsEnum) {
			if ($scope.situationsEnum[i].id === situation.id) {
				var sit = taskCtrl.actionsSituation[actionId];
				if (sit !== undefined && sit.id === situation.id){
					taskCtrl.actionsSituation[actionId] = undefined;
				}else {
					taskCtrl.actionsSituation[actionId] = situation;
				}
				$scope.sendDataChange = true;
				taskCtrl.actionsSituationChanged[actionId] = taskCtrl.actionsSituation[actionId];
				break;
			}
		}
		resetSituationAux(actionId);
	}
	$scope.isSituationAction = function(actionId, situation) {
		var sit = taskCtrl.actionsSituation[actionId];
		if (sit !== undefined && sit.id === situation.id) {
			return true;
		}
		return false;
	}
	$scope.selectSituationAction = function(actionId, situation) {
		if (actionId != null && situation != null) {
			$scope.setSituationAction(actionId, situation);
			$scope.redrawAll();
		}
	}
	$scope.descSituationAction = function(actionId) {
		var sit = taskCtrl.actionsSituation[actionId];
		return sit !== undefined ? sit.desc : '-';
	}
	$scope.idSituationAction = function(actionId) {
		var sit = taskCtrl.actionsSituation[actionId];
		return sit !== undefined ? sit.id : '';
	}
	$scope.getActionInfoFromId = function(actionId) {
		for (var s in taskCtrl.result.sessions) {
			var session = taskCtrl.result.sessions[s];
			for (var a in session.actions) {
				if (session.actions[a].identifier == actionId) {
					return session.actions[a];
				}
			}
		}
		return null;
	}
	
	//acoes especiais (inicial/final/obrigatória):
	$scope.specialActionsEnum = {
    	INITIAL: {
    		id: 'initial',
    		desc: 'Inicial'
    	},
    	END: {
    		id: 'end',
    		desc: 'Final'
    	},
    	REQUIRED: {
    		id: 'required',
    		desc: 'Obrigatória'
    	},
    	DEFAULT: {
    		id: 'default',
    		desc: 'Ação Normal'
    	}
    }
	//taskCtrl.result.sessions
	$scope.sendDataChange = false;
	$scope.alertChangeTask = false;
	for (var s in taskCtrl.result.sessions) {
		var sess = taskCtrl.result.sessions[s];
		for (var a in sess.actions) {
			if (sess.actions[a].required) {
				taskCtrl.actionsSpecialSituation[sess.actions[a].identifier] = $scope.specialActionsEnum.REQUIRED;
			}
		}
	}
	function setSpecialSituationNetwork(network, actionId, sit) {
		if (network) {
			var arrNodes = network.body.nodes;
			for (var n in arrNodes) {
				if (arrNodes[n].options.action != null && arrNodes[n].options.action.identifier == actionId) {
					arrNodes[n].options.action[$scope.specialActionsEnum.INITIAL.id] = null;
					arrNodes[n].options.action[$scope.specialActionsEnum.END.id] = null;
					arrNodes[n].options.action[$scope.specialActionsEnum.REQUIRED.id] = null;
					if (sit) {
						arrNodes[n].options.action[sit.id] = true;
					}
				}
			}
		}
	}
	function setSpecialSituationRealSessions(actionId, sit) {
		for (var s in taskCtrl.result.sessions) {
			var sess = taskCtrl.result.sessions[s];
			for (var a in sess.actions) {
				if (sess.actions[a].identifier == actionId) {
					sess.actions[a][$scope.specialActionsEnum.INITIAL.id] = null;
					sess.actions[a][$scope.specialActionsEnum.END.id] = null;
					sess.actions[a][$scope.specialActionsEnum.REQUIRED.id] = null;
					if (sit) {
						sess.actions[a][sit.id] = true;
					}
				}
			}
		}
	}
	function resetSpecialSituationAux(actionId) {
		var sit = taskCtrl.actionsSpecialSituation[actionId];
		$scope.specialSituationAux = sit !== undefined ? sit : $scope.specialActionsEnum.DEFAULT;
	}
	$scope.setSpecialSituationAction = function(actionId, situation) {
		for (var i in $scope.specialActionsEnum) {
			if ($scope.specialActionsEnum[i].id === situation.id) {
				//var sit = taskCtrl.actionsSpecialSituation[actionId];
				$scope.sendDataChange = true;
				taskCtrl.actionsSpecialSituation[actionId] = situation;
				taskCtrl.actionsSpecialSituationChanged[actionId] = situation;
				setSpecialSituationRealSessions(actionId, situation);
				setSpecialSituationNetwork($scope[$scope.graphIdsEnum.GRAPH_DEFAULT], actionId, situation);
				setSpecialSituationNetwork($scope[$scope.graphIdsEnum.GRAPH_SESSION], actionId, situation);
				break;
			}
		}
	}
	$scope.isSpecialSituationAction = function(actionId, situation) {
		var sit = taskCtrl.actionsSpecialSituation[actionId];
		if (sit !== undefined && sit.id === situation.id) {
			return true;
		}
		return false;
	}
	$scope.selectSpecialSituationAction = function(actionId, situation) {
		if (actionId != null && situation != null) {
			$scope.setSpecialSituationAction(actionId, situation);
			$scope.redrawAll();
		}
	}
	
	
	
	
	$scope.showUserSessions = function(user){
		var sessions = [];
		for(var i in user.sessionsId){
			var userSession = user.sessionsId[i];
			for(var s in taskCtrl.result.sessions){
				var session = taskCtrl.result.sessions[s];
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
		$scope.showContentAdvanced();
		taskCtrl.userSession = session;
		changeMode('actions');
	}
	
	$scope.showUsers = function(){
		changeMode('users');
	}
	
	$scope.showSessions = function(){
		changeMode('sessions');
	}
	
	$scope.showFrequentPattern = function(pattern){
		taskCtrl.frequentPatternActive = pattern;
		changeMode('pattern');
		$scope.renderGraph();
	}
	
	function changeMode(newMode){
		$scope.actionViewSelected = null;
		taskCtrl.previousMode = taskCtrl.mode;
		taskCtrl.mode = modes[newMode];
	}
	
	$scope.backMode = function(){
		taskCtrl.mode = taskCtrl.previousMode;
	}
	
	/*************   LISTA DE AÇÕES   *************/
	
	$scope.setActionViewSelected = function(action, isTask){
		if (isTask) {
			if (!taskCtrl.actionViewSelected) {
				taskCtrl.actionViewSelected = {};
			}
			taskCtrl.actionViewSelected.action = action;
			taskCtrl.actionViewSelected.positionStep = null;
			var graph = $scope[$scope.graphIdsEnum.GRAPH_SESSION];
			var node = nodeFromGraph(action.identifier, graph);
			updateNodeGraph(node, graph);
	    	selectNode(node, "actionViewSelected", "edgeSelectedSession");
		} else {
			$scope.actionViewSelected = action;
			$scope.actionViewSelected.positionStep = null;
			resetSituationAux(action.identifier);
	  	    resetSpecialSituationAux(action.identifier);
		}
		
		$scope.redraw($scope.graphIdsEnum.GRAPH_SESSION);
	}
	$scope.setActionViewGuideSelected = function(actionId){
		$scope.actionViewSelected = $scope.getActionInfoFromId(actionId);
		resetSituationAux(actionId);
  	    resetSpecialSituationAux(actionId);
	}
	$scope.setActionViewSelectedMostRealized = function(actionId){
		$scope.actionViewSelectedMost = $scope.getActionInfoFromId(actionId);
		resetSituationAux(actionId);
  	    resetSpecialSituationAux(actionId);
	}
	
	//history graph
	$scope.historyLabels = ["January", "February", "March", "April", "May", "June", "July"];
	$scope.historySeries = ['Series A', 'Series B'];
	$scope.historyData = [
	    [65, 59, 80, 81, 56, 55, 40],
	    [28, 48, 40, 19, 86, 27, 90]
	 ];
	
	//success graph
	console.log(taskCtrl.result);
	$scope.graphSuccessLabels = [
	                             $filter('translate')('datamining.tasks.evaluate.complete'), 
	                             $filter('translate')('datamining.tasks.evaluate.restart'),
	                             $filter('translate')('datamining.tasks.evaluate.threshold'),
	                             $filter('translate')('datamining.tasks.evaluate.error')];
    $scope.graphSuccessData = [
                               	taskCtrl.result.countSessionsSuccess, 
                               	taskCtrl.result.countSessionsRepeat,
                               	taskCtrl.result.countSessionsThreshold,
                               	taskCtrl.result.countSessionsError];
    $scope.graphSuccessColours = ["#46BFBD", "#FDB45C", "#F7464A", "#999"];
    $scope.graphSuccessType = 'Pie';
    $scope.graphSuccessToggle = function () {
    	$scope.graphSuccessType = $scope.graphSuccessType === 'Pie' ? 'PolarArea' : 'Pie';
    };
    
    
    /*************   CONTEUDO GERAL AVALIACAO   *************/
    var contentShowEnum = {
    	GRAPH: 'graph',
    	GUIDE: 'guide',
    	ADVANCED: 'advanced'
    }
    
    $scope.contentShow = contentShowEnum.GRAPH;
    $scope.showContentGraph = function() {
    	$scope.redraw($scope.graphIdsEnum.GRAPH_DEFAULT);
    	$scope.contentShow = contentShowEnum.GRAPH;
    }
    $scope.showContentGuide = function() {
    	if (!$scope.step) {
    		$scope.goToGuideStep("1");
    	}
    	$scope.contentShow = contentShowEnum.GUIDE;
    }
    $scope.showContentAdvanced = function() {
    	$scope.contentShow = contentShowEnum.ADVANCED;
    }
    $scope.isShowContentGraph = function() {
    	return $scope.contentShow === contentShowEnum.GRAPH;
    }
    $scope.isShowContentGuide = function() {
    	return $scope.contentShow === contentShowEnum.GUIDE;
    }
    $scope.isShowContentAdvanced = function() {
    	return $scope.contentShow === contentShowEnum.ADVANCED;
    }
    
    /*************   GRAFO   *************/
    
    $scope.minSup = (taskCtrl.result.lastMinSup * 100);
    $scope.minItens = taskCtrl.result.lastMinItens;
    
    var DEFAULT_SCALE = 3;
    $scope.factorScaleX = DEFAULT_SCALE;
    
    $scope.changeFactorScaleX = function() {
    	if (!isNumeric($scope.factorScaleX)) {
        	$scope.factorScaleX = DEFAULT_SCALE;
        }
    	$scope.renderGraph();
    }
    
    function isNumeric (n) {
    	return !isNaN(parseFloat(n)) && isFinite(n);
    }
    
    $scope.graphTypeEnum = {
    	PATTERNS: {
    		id: 'patterns',
    		desc: 'Padrões de Uso'
    	},
    	SESSIONS: {
    		id: 'sessions',
    		desc: 'Sessões dos Usuários'
    	}
    }
    $scope.graphIdsEnum = {
		GRAPH_DEFAULT: 'graph',
		GRAPH_SESSION: 'graphsession'
    }
    var isPatternsActual;
    $scope.changeGraphType = function(isPatterns) {
    	isPatternsActual = isPatterns;
    	$scope.renderGraph();
    }
    $scope.renderGraphSession = function(session) {
    	var graphData = generateGraphSession(taskCtrl.frequentPatterns, session);
    	$scope[$scope.graphIdsEnum.GRAPH_SESSION] = drawGraph('mynetworkSession', graphData, taskCtrl.result.sessions, taskCtrl.actionsSituation, $scope.situationsEnum, $scope.factorScaleX);
    	$scope.resetGraph($scope.graphIdsEnum.GRAPH_SESSION, "actionViewSelected", "edgeSelectedSession");
    	verifyInitialZoom($scope[$scope.graphIdsEnum.GRAPH_SESSION]);
    	$scope[$scope.graphIdsEnum.GRAPH_SESSION].positionStep = 0;
    }
    $scope.renderGraph = function() {
    	if (isPatternsActual) {
    		$scope.graphType = $scope.graphTypeEnum.PATTERNS;
    	} else {
    		$scope.graphType = $scope.graphTypeEnum.SESSIONS;
    	}
    	
    	var graphData = generateGraphFrequentPatterns($scope.graphType.id, taskCtrl.frequentPatterns, taskCtrl.result.sessions);
    	$scope[$scope.graphIdsEnum.GRAPH_DEFAULT] = drawGraph('mynetwork', graphData, taskCtrl.result.sessions, taskCtrl.actionsSituation, $scope.situationsEnum, $scope.factorScaleX);
    	$scope.resetGraph($scope.graphIdsEnum.GRAPH_DEFAULT, "nodeSelected", "edgeSelected");
    	verifyInitialZoom($scope[$scope.graphIdsEnum.GRAPH_DEFAULT]);
    	$scope[$scope.graphIdsEnum.GRAPH_DEFAULT].positionStep = 0;
    }
    
    function selectNode(node, nodeSelectedName, edgeSelectedName) {
    	var edgesFrom = [], edgesTo = [];
  	  	resetSituationAux(node.id);
  	    resetSpecialSituationAux(node.id);
  	    
  	    /*
  	  	angular.forEach(node.edges, function(edge){
  	  		if (edge.from.id != node.id) {
  	  			edgesFrom.push(edge.from.id);
  	  		} else if (edge.to.id != node.id) {
  	  			edgesTo.push(edge.to.id);
  	  		} else {
  	  			edgesFrom.push(edge.from.id);
  	  			edgesTo.push(edge.to.id);
  	  		}
  	  	});
  	  	*/
  	  
  	  	//taskCtrl[edgeSelectedName] = null;
  	  	taskCtrl[nodeSelectedName] = {
  	  		'id': node.id,
  	  		'name': node.options.label,
  	  		'value': node.options.value,
			//'edgesFrom': edgesFrom.join(", "),
			//'edgesTo': edgesTo.join(", "),
			'sessions': node.options.sessions,
			'patternsCount': node.options.countPatterns,
			'action': node.options.action,
			'positionStep': null
  	  	}
    }
    
    $scope.resetGraph = function(graphName, nodeSelectedName, edgeSelectedName) {
    	console.log($scope[graphName]);
    	$scope[graphName].on("selectNode", function (params) {
      	  	var node = $scope[graphName].body.nodes[params.nodes[0]];
      	  	selectNode(node, nodeSelectedName, edgeSelectedName);
	  		$scope.$apply();
        });
    	$scope[graphName].on("selectEdge", function (params) {
    		var edge = $scope[graphName].body.edges[params.edges[0]];
    		//taskCtrl[nodeSelectedName] = null;
	  		taskCtrl[edgeSelectedName] = {
	  			'fromId' : edge.fromId,
	  			'toId' : edge.toId,
	  			'value' : edge.options.value,
	  			'sessions': edge.options.sessions,
	  			'patternsCount' : edge.options.countPatterns
	      	}
	  		console.log(edge, taskCtrl[edgeSelectedName]);
    		$scope.$apply();
        });
    	$scope[graphName].on("deselectNode", function () {
    		taskCtrl[nodeSelectedName] = null;
    		$scope.$apply();
    	});
    	$scope[graphName].on("deselectEdge", function () {
    		taskCtrl[edgeSelectedName] = null;
    		$scope.$apply();
    	});
    	
    }
    
    var factorScale = 0.1;
    $scope.zoomOutGraph = function(graphName){
    	$scope[graphName].moveTo({
            scale: $scope[graphName].getScale() + factorScale
        });
    }
    $scope.zoomInGraph = function(graphName){
    	var newScale = $scope[graphName].getScale() - factorScale;
    	$scope[graphName].moveTo({
            scale: newScale >= 0 ? newScale : 0
        });
    }
    $scope.resetZoomGraph = function(graphName){
    	$scope[graphName].moveTo({
    		position: $scope[graphName].initialPosition,
    		scale: $scope[graphName].initialZoom
    	});
    }
    function verifyInitialZoom(graph){
    	graph.initialZoom = graph.initialZoom != null ? graph.initialZoom : graph.getScale();
    	graph.initialPosition = graph.initialPosition != null ? graph.initialPosition : graph.getViewPosition();
    }
    
    
    $scope.redrawAll = function() {
    	$scope.redraw($scope.graphIdsEnum.GRAPH_DEFAULT);
    	$scope.redraw($scope.graphIdsEnum.GRAPH_SESSION);
    }
    $scope.redraw = function(graphName) {
    	refreshGraph($scope[graphName], taskCtrl.actionsSituation, $scope.situationsEnum);
    }
    $scope.showActionsSessionFromGraph = function(session){
    	$scope.showContentAdvanced();
    	var sessions = taskCtrl.result.sessions, realSession;
    	for (var s in sessions) {
    		if (sessions[s].id == session.id) {
    			realSession = sessions[s];
    		}
    	}
		taskCtrl.userSession = realSession;
		changeMode('actions');
	}
    
    /** STEPS ON GRAPH **/
    $scope.positionStepView = {};
    $scope.positionStepView[$scope.graphIdsEnum.GRAPH_SESSION] = 0;
    
    $scope.forwardStepGraph = function(graphName, session) {
    	if (session.actions.length > $scope[graphName].positionStep) {
    		$scope[graphName].positionStep++;
    	}
    	setStepGraph(session, graphName);
    }
    
    $scope.backwardStepGraph = function(graphName, session) {
    	if ($scope[graphName].positionStep > 1) {
    		$scope[graphName].positionStep--;
    	}
    	setStepGraph(session, graphName);
    }
    
    $scope.repeatStepsGraph = function(graphName, session) {
    	$scope[graphName].positionStep = 1;
    	setStepGraph(session, graphName);
    }
    
    function setStepGraph(session, graphName) {
    	var graph = $scope[graphName];
    	var action = session.actions[graph.positionStep - 1];
    	var node = nodeFromGraph(action.identifier, graph);
    	
    	$scope.positionStepView[$scope.graphIdsEnum.GRAPH_SESSION] = graph.positionStep;
    	
    	updateNodeGraph(node, graph);
		selectNode(node, "actionViewSelected", "edgeSelectedSession");
		taskCtrl.actionViewSelected.positionStep = graph.positionStep;
    }
    
    function updateNodeGraph(node, graph){
    	graph.unselectAll();
		graph.selectNodes([node.id]);
    }
    
    function nodeFromGraph(actionId, graph) {
    	var nodes = graph.body.nodes;
    	for (var n in nodes) {
    		if (actionId == nodes[n].id) {
    			return nodes[n];
    		}
    	}
    	return null;
    }
    
    /*************   GUIDE   *************/
    
    $scope.goToGuideStep = function(step, data){
    	$scope.step = step;
    	if (step == "1") {
    		//ordenar prioridade: classificacao > exatidão > completude > tempo > qtd.acoes
    		var sess = taskCtrl.result.sessions;
    		sess.sort(function(a, b) {
    		    return a.actions.length - b.actions.length; //menor
    		});
    		sess.sort(function(a, b) {
    		    return a.time - b.time; //menor
    		});
    		sess.sort(function(a, b) {
    		    return b.userRateSuccess - a.userRateSuccess; //maior
    		});
    		sess.sort(function(a, b) {
    		    return b.userRateRequired - a.userRateRequired; //maior
    		});
    		sess.sort(function(a, b) {
    		    return classificationPoints(a.classification) - classificationPoints(b.classification); //menor
    		});
    		for (var s in sess) {
    			sess[s].order = s;
    		}
    		$scope.sessionsGuide = sess;
    	} else if (step == "1.1") {
    		$scope.sessionSelected = data;
    		
    		//filter for required actions dont done
    		$scope.sessionActionsReqStr = taskCtrl.task.actionsRequiredOrder;
    		if (taskCtrl.task.actionsRequiredOrder) {
    			var groupActionsReq = taskCtrl.task.actionsRequiredOrder.split(","),
	    			actionsRequiredSession = [],
	    			actionsFound = [];
	    		
	    		//identify actions req has been done
	    		for (var gar in groupActionsReq) {
	    			var strActionsReqId = groupActionsReq[gar],
	    				actionsReqId = strActionsReqId.replace(/\[|\]/g,"").split(";"),
	    				containsAnyActionGroup = false;
	    			
	    			for (var ari in actionsReqId) {
	    				var actionReqId = actionsReqId[ari];
	    				for (var t in taskCtrl.task.actionsRequired) {
	    					var action = taskCtrl.task.actionsRequired[t];
	    					if (action.id == actionReqId) {
	    						var actionRes = findActionResultFromActionSingleDataMining(action);
	    						actionRes.isFound = false;
	            				for (var a in data.actions) {
	            					if (data.actions[a].identifier == actionRes.identifier) {
	            						containsAnyActionGroup = true;
	            						actionRes.isFound = true;
	            						break;
	            					}
	            				}
	            				actionsFound.push(actionRes);
	    					}
	    				}
	    			}
	    			
	    			//mark only when hasnt any actionreq of group
	    			if (!containsAnyActionGroup) {
	    				$scope.sessionActionsReqStr = $scope.sessionActionsReqStr.replace(strActionsReqId, '<b style="color: #C80707">'+strActionsReqId+'</b>');
	    			}
	    		}
    		}
    		
    		$scope.sessionActionsRequired = actionsFound;
    		
    		$scope.renderGraphSession(data);
    		$scope.repeatStepsGraph($scope.graphIdsEnum.GRAPH_SESSION, data);
    	}
    }
    
    function findActionResultFromActionSingleDataMining (action) {
    	var actionResult = {}, 
    		sXPath = '', sJhm = '', sStepJhm = '', actResume = '';
	
		//TODO: tornar essa parte genérica (montar de acordo com regra do servidor)
		for (var u in action.urlFieldSearch) {
			var ufs = action.urlFieldSearch[u];
			if (ufs.field == 'sJhm') {
				sJhm = ufs.value;
			} else if (ufs.field == 'sStepJhm') {
				sStepJhm = ufs.value;
			}
		}
		for (var e in action.elementFiedlSearch) {
			var el = action.elementFiedlSearch[e];
			if (el.field == 'sXPath') {
				sXPath = el.value;
			}
		}
		actResume = sJhm+'-'+sStepJhm+' | '+sXPath+' | '+action.actionType;
		actionResult.resume = actResume;
		actionResult.iddb = action.id;
		
		for (var tar in taskCtrl.actionsRequiredArr) {
			var act = taskCtrl.actionsRequiredArr[tar];
			if (act.$key == actResume) {
				actionResult.identifier = act.id || '-';
				actionResult.action = act;
			}
		}
		
		return actionResult;
    };
    
    function classificationPoints(classification) {
    	return $scope.getClassificationFromEnum(classification).val;
    }
    
    function addMoreInfoSession(session) {
    	var countUserSessions = 0;
    	for (var s in taskCtrl.result.sessions) {
    		if (taskCtrl.result.sessions[s].username == session.username) {
    			countUserSessions++;
    		}
    	}
    	return countUserSessions;
    }
    
    $scope.showActionsSessionGuide = function(session){
    	if (session) {
    		$scope.goToGuideStep("1.1", session);
    	} else {
    		alert("Selecione uma sessão e tente novamente...");
    		$scope.goToGuideStep("1");
    	}
    }
    
    $scope.saveChangeSituations = function(){
    	//taskCtrl.actionsSituationChanged
    	//taskCtrl.actionsSpecialSituationChanged
    	var actionInfoMap = {};
    	for (var a in taskCtrl.actionsSpecialSituationChanged) {
    		var action = getActionFromActionsArr(a);
    		var actionInfo = splitActionValue(action.value);
    		
    		actionInfo.id = action.$key;
    		actionInfo.type = taskCtrl.actionsSpecialSituationChanged[a].id;
    		
    		actionInfoMap[actionInfo.id] = actionInfo;
    	}
    	
    	for (var a in taskCtrl.actionsSituationChanged) {
    		var action = getActionFromActionsArr(a);
    		var actionInfo = actionInfoMap[action.$key];
    		
    		if (!actionInfo) {
    			actionInfo = splitActionValue(action.value);
        		actionInfo.id = action.$key;
    		}
    		
    		actionInfo.situation = taskCtrl.actionsSituationChanged[a].id;
    		actionInfoMap[actionInfo.id] = actionInfo;
    	}
    	
    	console.log(actionInfoMap);
    	var resultActionMap = [];
    	for (var a in actionInfoMap) {
    		resultActionMap.push(actionInfoMap[a]);
    	}
    	
    	console.log(angular.toJson(resultActionMap));
    	
    	ServerAPI.saveActionSituation(
    			Number.parseInt(taskCtrl.task.testDataMining.id), 
    			Number.parseInt(taskCtrl.evalTestId), 
    			Number.parseInt(taskCtrl.task.id),
    			angular.toJson(resultActionMap)
		).then(function(data) {
			result = JSON.parse(data.data.string);
			if (result.status == "SUCESSO") {
				taskCtrl.actionsSpecialSituationChanged = {};
				$scope.sendDataChange = false;
				$scope.alertChangeTask = true;
				$scope.success = $filter('translate')(result.message);
			}
		}, function(data) {
			console.log(data);
		});
    	
    }
    
    function getActionFromActionsArr (id) {
    	for (var a in taskCtrl.actionsArr) {
    		if (taskCtrl.actionsArr[a].$key == id) {
    			return taskCtrl.actionsArr[a];
    		}
    	}
    }
    
    function splitActionValue (str) {
    	var data = str.split(' | ');
    	return {
    		location: data[0],
			element: data[1],
			action: data[2]
    	};
    }
    
    //initialize
    $timeout(function(){ 
    	$scope.changeGraphType(false);
    }, 500);
    
})

//Actions Controllers
//ActionsNewController
.controller('ActionViewController', function(action) {
	//todo
})
.controller('ActionNewController', function(task, $filter, ServerAPI, ActionTypeEnum, MomentTypeEnum, ValidateService, ShowMessage) {
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
		
		if (ValidateService.action(actionCtrl.action)) {
			var action = angular.toJson({
				'actionJHeatVO' : actionCtrl.action
			});
			ServerAPI.saveAction(action);
		} else {
			ShowMessage.error('datamining.validate.default');
		}
	};
})
.controller('ActionEditController', function(action, $filter, ServerAPI) {
	//todo
})

/* DIRECTIVES */
/* <a href="#" ng-click="confirmClick() && deleteItem(item)" confirm-click>Delete</a> */
.directive('usdmActionInfo', function(config, env) {
  return {
    restrict: 'E',
    scope: {
      actionInfo: '=action',
      momento: '=momento',
      
      situationsEnum: '=situationsEnum',
      specialActionsEnum: '=specialActionsEnum',
      situationAux: '=situationAux',
      situationSpecialAux: '=situationSpecialAux',
      
      selectSituation: '&onSelectSituation',
      selectSpecialSituation: '&onSelectSpecialSituation'
    },
    templateUrl: config[env].apiUrl+'/templates/directives/usdm-action-info.html'
  };
})
.directive('usdmSessionInfo', function(config, env) {
  return {
    restrict: 'E',
    scope: {
    	sessionInfo: '=session',
    	graphsession: '=graphsession',
    	getClassificationFromEnum: '&getClassificationFromEnum'
    },
    templateUrl: config[env].apiUrl+'/templates/directives/usdm-session-info.html'
  };
})
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
.directive('colorAction', function() {
	  return {
		    restrict: 'A',
		    scope: {
		    	colorActionElem: '=',
		    	colorCtrl: '='
		    },
		    link: function(scope, element) {
		    	
		    	var id = (scope.colorActionElem.$key || scope.colorActionElem.identifier);
		    	var count = scope.colorCtrl.result.pageViewActionCount[id];
		    	var a = count > 0 ? (count / scope.colorCtrl.actionsMaxCount) : 0;
		    	
		    	
		    	jQuery(element).style('background', 'rgba(66,99,146, '+a+')', 'important')
		    	if(a > .5){
		    		element.css('color', 'white');
		    	}
		    	
		    	/*
		    	var a = (scope.colorActionElem.count/taskCtrl.actionsMaxCount || 0);
		    	element.css('background', 'rgba(66,99,146, '+a+')');
		    	if(a >= .6){
		    		element.css('color', 'white');
		    	}
		    	*/
		    }
	  };
})
.directive('longLoader', function($rootScope) {
	  return {
		    restrict: 'A',
		    scope: { },
		    link: function(scope, element) {
		    	
		    	var DEFAULT_PADDING_TIME = 20, 
		    		DEFAULT_VELOCITY = 100,
		    		DEFAULT_TIME_MSGS = 2;
		    	
		    	var v = 0, endFunc = false;
		    	function fakeLoading($obj, seconds) {
		    		if (typeof seconds == "undefined") seconds = DEFAULT_VELOCITY;
		    		seconds += DEFAULT_PADDING_TIME;
		    		endFunc = false;
		    		var messages = {
		    			'0.25': {
		    				message: 'Carregando...' 
		    			},
		    			'0.5': {
		    				message: 'Opa, chegou na metade! :)' 
		    			},
		    			'0.7': {
		    				message: 'Z z Z z ...' 
		    			},
		    			'0.85': {
		    				message: 'Tá pertinho... :D' 
		    			}
		    		};
		    		v = 0;
		    		var l = function() {
		    				var newadd = 0.2 / seconds;
		    				newadd = v >= 0.6 ? 0.1 / seconds : newadd;
		    				newadd = v >= 0.82 ? 0.05 / seconds : newadd;
		    				
		    				for (var m in messages) {
		    					if (v >= parseFloat(m) && messages[m].done != true) {
		    						var timeMsg = messages[m].time || DEFAULT_TIME_MSGS;
		    						messages[m].done = true;
		    						$obj.ElasticProgress("showMessage", messages[m].message, timeMsg);
		    					}
		    				}
		    				
		    				if (v == 2) {
		    					v = 1;
		    					endFunc = true;
		    				}else if (v >= 1) {
		    					v = 0.999;
		    					endFunc = true;
		    				} else {
		    					v += newadd;
		    				}
		    				
		    				if (!loading) {
		    					return;
		    				}
		    				
		    				if (typeof $obj.jquery != "undefined") {
		    						$obj.ElasticProgress("setValue", v);
		    				} else {
		    						$obj.setValue(v);
		    				}
		    				
		    				if (!endFunc) {
		    						TweenMax.delayedCall(0.05 + (Math.random() * 0.14), l);
		    				}
		    		};
		    		l();
			    }
		    	
		    	var velocity = DEFAULT_VELOCITY;
		    	var progress = jQuery(element).show().ElasticProgress({
	    			align: "center",
	    			bleedTop: 110,
	    			bleedBottom: 40,
	    			buttonSize: 50,
	    			labelTilt: 70,
	    			arrowDirection: "down",
	    			fontFamily: "Montserrat",
	    			colorBg: "#80ACFD",
	    			colorFg: "#425b92",
	    			textComplete: 'Últimos detalhes...',
	    			onClick: function(event) {
	    					console.log("onClick");
	    					$(this).ElasticProgress("open");
	    			},
	    			onOpen: function(event) {
	    					console.log("onOpen", new Date());
	    					fakeLoading($(this), velocity);
	    			},
	    			onComplete: function(event) {
	    					console.log("onComplete", new Date());
	    			},
	    			onClose: function(event) {
	    					console.log("onClose");
	    			},
	    			onFail: function(event) {
	    					console.log("onFail");
	    					$(this).ElasticProgress("open");
	    			},
	    			onCancel: function(event) {
	    					console.log("onCancel");
	    					$(this).ElasticProgress("open");
	    			}
	    		}).hide();
		    	
		    	var loading = false;
		    	
		    	function initLongLoading() {
		    		console.log('initLongLoading');
		    		loading = true;
		    		v = 0;
		    		endFunc = false;
		    		progress.ElasticProgress("setValue", 0);
		    		velocity = $rootScope.longLoadingTime || DEFAULT_VELOCITY;
		    		progress.show().ElasticProgress("open");
		    	}
		    	
		    	function endLongLoading() {
		    		console.log('endLongLoading');
		    		loading = false;
		    		v = 0;
		    		endFunc = true;
		    		progress.ElasticProgress("setValue", 0);
		    		progress.hide().ElasticProgress("close");
		    	}		    	
		    	
		    	$rootScope.$on('$routeChangeStart', function(event, next, current) {
		    		if (next.data && next.data.longLoading) {
		    			initLongLoading();
		    		} else {
		    			endLongLoading();
		    		}
		    	});
		    	
		    	$rootScope.$on('cfpLoadingBar:loading', function(){
		    		var x = jQuery('.loader__img');
		    		console.log(loading, x.length);
		    		if (loading && x.length) {
		    			x.hide();
		    		} else {
		    			x.show();
		    		}
		    	});
		    	
		    	$rootScope.$on('cfpLoadingBar:completed', function(){
		    		endLongLoading();
		    	});
		    }
	  };
})

/* FILTER */

.filter('msConverter', function() {
  return function(n) {
	  var numberTwoDigits = function(n) {
		  return (n < 10 ? '0' : '') + n;
	  }
	  n = Math.round(n);
	  var minutes = Math.floor(n / 60000);
	  var seconds = ((n % 60000) / 1000).toFixed(0);
	  if (minutes < 60 ){
		  return numberTwoDigits(minutes) + "m:" + numberTwoDigits(seconds) + "s";
	  } else {
		  var hours = Math.floor(minutes / 60);
		  minutes = (minutes % 60).toFixed(0);
		  return hours + "h:" + numberTwoDigits(minutes) + "m:" + numberTwoDigits(seconds) + "s";
	  }
  }
})
.filter('booleanText', function($filter) {
  return function(n) {
	  if (n == true) {
		  return $filter('translate')('true');
	  } else {
		  return $filter('translate')('false');
	  }
  }
})
.filter('numberSize', function() {
  return function(n, size) {
	  while((new String(n)).length < size){
		  n = "0"+n;
	  }
	  return n; 
  }
})
.filter('toArray', function () {
  return function (obj, addKey) {
    if (!(obj instanceof Object)) {
      return obj;
    }

    if ( addKey === false ) {
      return Object.values(obj);
    } else {
      return Object.keys(obj).map(function (key) {
    	  if((obj[key] instanceof Object)){
    		  return Object.defineProperty(obj[key], '$key', { enumerable: false, value: key});
    	  }else{
    		  return {'value': obj[key], '$key': key};
    	  }
      });
    }
  };
})
.filter("sanitize", ['$sce', function($sce) {
  return function(htmlCode){
    return $sce.trustAsHtml(htmlCode);
  }
}])
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

(function($) {    
  if ($.fn.style) {
    return;
  }

  // Escape regex chars with \
  var escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
  };

  // For those who need them (< IE 9), add support for CSS functions
  var isStyleFuncSupported = !!CSSStyleDeclaration.prototype.getPropertyValue;
  if (!isStyleFuncSupported) {
    CSSStyleDeclaration.prototype.getPropertyValue = function(a) {
      return this.getAttribute(a);
    };
    CSSStyleDeclaration.prototype.setProperty = function(styleName, value, priority) {
      this.setAttribute(styleName, value);
      var priority = typeof priority != 'undefined' ? priority : '';
      if (priority != '') {
        // Add priority manually
        var rule = new RegExp(escape(styleName) + '\\s*:\\s*' + escape(value) +
            '(\\s*;)?', 'gmi');
        this.cssText =
            this.cssText.replace(rule, styleName + ': ' + value + ' !' + priority + ';');
      }
    };
    CSSStyleDeclaration.prototype.removeProperty = function(a) {
      return this.removeAttribute(a);
    };
    CSSStyleDeclaration.prototype.getPropertyPriority = function(styleName) {
      var rule = new RegExp(escape(styleName) + '\\s*:\\s*[^\\s]*\\s*!important(\\s*;)?',
          'gmi');
      return rule.test(this.cssText) ? 'important' : '';
    }
  }

  // The style function
  $.fn.style = function(styleName, value, priority) {
    // DOM node
    var node = this.get(0);
    // Ensure we have a DOM node
    if (typeof node == 'undefined') {
      return this;
    }
    // CSSStyleDeclaration
    var style = this.get(0).style;
    // Getter/Setter
    if (typeof styleName != 'undefined') {
      if (typeof value != 'undefined') {
        // Set style property
        priority = typeof priority != 'undefined' ? priority : '';
        style.setProperty(styleName, value, priority);
        return this;
      } else {
        // Get style property
        return style.getPropertyValue(styleName);
      }
    } else {
      // Get CSSStyleDeclaration
      return style;
    }
  };
})(jQuery);