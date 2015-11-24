'use strict';

sddcDashboard.controller('detailAdminServicesController',
    function detailService($scope, $routeParams, Service) {
  $scope.service = Service.get({id: $routeParams.serviceId});

});
