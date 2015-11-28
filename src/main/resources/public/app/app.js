'use strict';

var sddcDashboard = angular.module('sddcDashboard', ['ngRoute','ngResource']);

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
  .when('/admin/services/:serviceId', {
  controller: 'detailAdminServicesController',
  templateUrl: '/views/admin/detailservice.html'
  })
   .when('/admin/servicemodules/:servicemoduleId', {
    controller: 'detailAdminServiceModulesController',
    templateUrl: '/views/admin/detailservicemodule.html'
  })
  .otherwise({
			redirectTo: '/services'
	});
;
});


sddcDashboard.factory("Service", function($resource) {
  return $resource("/api/services/:id",{id: '@id'});
});

sddcDashboard.factory("OrderedService", function($resource) {
  return $resource("/api/orderedservices/:id",{id: '@id'});
});

sddcDashboard.factory("ServiceModule", function($resource) {
    return $resource("/api/servicemodules/:id",{id: '@id'});
});
