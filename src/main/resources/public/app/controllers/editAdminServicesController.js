'use strict';

sddcDashboard.controller('editAdminServicesController', function($scope,$http,$routeParams,$location, Service){

    $scope.service = Service.get({id: $routeParams.serviceId});

    $scope.updateService = function(service) {
        Service.update({ id:$routeParams.serviceId }, service);
        $location.path('/admin/services/');
    };

    $scope.servicemodules = [];

    $scope.findServiceModules = function () {
        $http.get("/api/servicemodules").success(function (data) {
            $scope.servicemodules = data;
        })
    };

    $scope.findServiceModules();

    });
