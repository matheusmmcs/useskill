angular.module('useskill', 
		['ngRoute', 'pascalprecht.translate'])
		
.config(['$translateProvider', '$routeProvider', 
         function($translateProvider, $routeProvider) {
	
	$translateProvider.useUrlLoader('/Usabilidade/jscripts/app/messages.json');
	$translateProvider.preferredLanguage('pt');
	
	var routeInit = {
		controller:'TestController as testCtrl',
		templateUrl:'/Usabilidade/templates/list.html',
		resolve: {
			tests: function (TestServer) {
	        	return TestServer.getTests();
	        }
	    }
	};
	
	$routeProvider
		.when('/', routeInit)
		.when('/testes/', routeInit)
		.when('/testes/criar', {
	    	controller:'TestNewController as testCtrl',
	    	templateUrl:'/Usabilidade/templates/create.html'
	    })
		.when('/testes/:testId', {
			controller:'TestViewController as testCtrl',
			templateUrl:'/Usabilidade/templates/detail.html',
			resolve: {
				test: function (TestServer, $route) {
		        	return TestServer.getTest($route.current.params.testId);
		        }
		    }
	    })
	    .otherwise({
	    	redirectTo:'/'
	    });
	
}])
.factory("TestServer", function($q, $http){
    return {
        getTests: function(){
            var promise = $http({ method: 'GET', url: '/Usabilidade/datamining/testes/'});
            return promise;
        },
        getTest: function(testId){
            var promise = $http({ method: 'GET', url: '/Usabilidade/datamining/testes/'+testId});
            return promise;
        },
        saveTest: function(test){
        	
        	var promise = $http.post('/Usabilidade/datamining/testes/salvar/', test);
        	return promise;
        }
    };
})
.controller('TestController', function(tests) {
	var testCtrl = this;
	testCtrl.tests = JSON.parse(tests.data.string);
})
.controller('TestViewController', function(test) {
	var testCtrl = this;
	testCtrl.test = JSON.parse(test.data.string);
})
.controller('TestNewController', function($filter, TestServer) {
	var testCtrl = this;
	testCtrl.actionTitle = $filter('translate')('datamining.testes.new');
	testCtrl.save = function() {
		console.log('save', testCtrl);
		TestServer.saveTest(testCtrl.test);
		//${pageContext.request.contextPath}/datamining/testes/salvar POST
//      Projects.$add(editProject.project).then(function(data) {
//          $location.path('/');
//      });
	};
})
;


/*
.service('fbRef', function(fbURL) {
  return new Firebase(fbURL)
})
.service('fbAuth', function($q, $firebase, $firebaseAuth, fbRef) {
  var auth;
  return function () {
      if (auth) return $q.when(auth);
      var authObj = $firebaseAuth(fbRef);
      if (authObj.$getAuth()) {
        return $q.when(auth = authObj.$getAuth());
      }
      var deferred = $q.defer();
      authObj.$authAnonymously().then(function(authData) {
          auth = authData;
          deferred.resolve(authData);
      });
      return deferred.promise;
  }
})
 
.service('Projects', function($q, $firebase, fbRef, fbAuth) {
  var self = this;
  this.fetch = function () {
    if (this.projects) return $q.when(this.projects);
    return fbAuth().then(function(auth) {
      var deferred = $q.defer();
      var ref = fbRef.child('projects/' + auth.auth.uid);
      var $projects = $firebase(ref);
      ref.on('value', function(snapshot) {
        if (snapshot.val() === null) {
          $projects.$set(window.projectsArray);
        }
        self.projects = $projects.$asArray();
        deferred.resolve(self.projects);
      });
      return deferred.promise;
    });
  };
})
 
.config(function($routeProvider) {
  $routeProvider
    .when('/', {
      controller:'ProjectListController as projectList',
      templateUrl:'../../templates/list.html',
      resolve: {
        projects: function (Projects) {
          return Projects.fetch();
        }
      }
    })
    .when('/edit/:projectId', {
      controller:'EditProjectController as editProject',
      templateUrl:'../../templates/detail.html'
    })
    .when('/new', {
      controller:'NewProjectController as editProject',
      templateUrl:'../../templates/detail.html'
    })
    .otherwise({
      redirectTo:'/'
    });
})
 
.controller('ProjectListController', function(projects) {
  var projectList = this;
  projectList.projects = projects;
})
 
.controller('NewProjectController', function($location, Projects) {
  var editProject = this;
  editProject.save = function() {
      Projects.$add(editProject.project).then(function(data) {
          $location.path('/');
      });
  };
})
 
.controller('EditProjectController',
  function($location, $routeParams, Projects) {
    var editProject = this;
    var projectId = $routeParams.projectId,
        projectIndex;
 
    editProject.projects = Projects.projects;
    projectIndex = editProject.projects.$indexFor(projectId);
    editProject.project = editProject.projects[projectIndex];
 
    editProject.destroy = function() {
        editProject.projects.$remove(editProject.project).then(function(data) {
            $location.path('/');
        });
    };
 
    editProject.save = function() {
        editProject.projects.$save(editProject.project).then(function(data) {
           $location.path('/');
        });
    };
});
*/