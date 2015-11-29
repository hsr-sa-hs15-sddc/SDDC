'use strict';

sddcDashboard.controller('detailService',
    function detailService($scope,$location, $routeParams, Service) {

  	$scope.service = Service.get({id: $routeParams.serviceId});
	$scope.order = function(service){
		$scope.service.$save()
			.then(function(service){
					$location.path('/orderedservices/');
			});
		}
});
