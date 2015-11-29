'use strict';

sddcDashboard.controller('newAdminServicesController', function($scope,$http,$location,$routeParams, newService){

    $scope.service = new newService;

    $scope.save = function(service){
            $scope.service.$save()
                .then(function(service){
                    $location.path('/admin/services/' + service.id);
                });
    }

    $scope.servicemodules = [];

    $scope.findServiceModules = function () {
        $http.get("/api/servicemodules").success(function (data) {
            $scope.servicemodules = data;
        })
    };


    $scope.findServiceModules();

    });
