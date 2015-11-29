'use strict';

var sddcDashboard = angular.module('sddcDashboard', ['ngRoute','ngResource','checklist-model']);

sddcDashboard.config(function($routeProvider) {

  $routeProvider
  .when('/services', {
        controller: 'ServicesController',
        templateUrl: 'views/service/list.html'
  })
  .when('/orderedservices',{
        controller: 'OrderedServicesController',
        templateUrl: 'views/orderedservice/list.html'
  })
  .when('/services/:serviceId', {
			controller: 'detailService',
			templateUrl: '/views/service/detail.html'
	})
  .when('/orderedservices/:orderedserviceId', {
      controller: 'orderedDetailService',
      templateUrl: '/views/orderedservice/detail.html'
  })
  .when('/admin/services', {
        controller: 'adminServicesController',
        templateUrl: 'views/admin/listservice.html'
  })
  .when('/admin/servicemodules', {
        controller: 'adminServiceModulesController',
        templateUrl: 'views/admin/listservicemodules.html'
  })
  .when('/admin/services/new', {
        controller: 'newAdminServicesController',
        templateUrl: '/views/admin/newservice.html'
  })
  .when('/admin/services/:serviceId', {
  controller: 'detailAdminServicesController',
  templateUrl: '/views/admin/detailservice.html'
  })
  .when('/admin/services/:serviceId/edit', {
   controller: 'editAdminServicesController',
   templateUrl: '/views/admin/editservice.html'
   })
   .when('/admin/servicemodules/new', {
      controller: 'newAdminServiceModulesController',
      templateUrl: '/views/admin/newservicemodule.html'
   })
   .when('/admin/servicemodules/:servicemoduleId', {
    controller: 'detailAdminServiceModulesController',
    templateUrl: '/views/admin/detailservicemodule.html'
  })
  .when('/admin/servicemodules/:servicemoduleId/edit', {
    controller: 'editAdminServiceModulesController',
    templateUrl: '/views/admin/editservicemodule.html'
   })
  .otherwise({
			redirectTo: '/services'
	});
;
});


sddcDashboard.factory("OrderedService", function($resource) {
  return $resource("/api/orderedservices/:id",{id: '@id'});
});

sddcDashboard.factory("newService", function($resource) {
    return $resource("/api/services/new");
});

sddcDashboard.factory("newServiceModule", function($resource) {
    return $resource("/api/servicemodules/new");
});

sddcDashboard.factory('ServiceModule', ['$resource', function($resource) {
    return $resource('/api/servicemodules/:id', null,
        {
            'update': { method:'PUT' }
        });
}]);

sddcDashboard.factory('Service', ['$resource', function($resource) {
    return $resource('/api/services/:id', {id: '@id'},
        {
            'update': { method:'PUT' }
        });
}]);