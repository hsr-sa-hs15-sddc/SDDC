'use strict';

sddcDashboard.controller('adminServicesController', function($scope, $http){
        $scope.services = [];

        $scope.findServices = function () {
            $http.get("/api/services").success(function (data) {
                $scope.services = data;
            })
        };


    $scope.deleteService = function(service) {
        $http.delete("/api/services/{id}".replace('{id}',service.id)).success(function (data)
            {
                if ( ! $scope.$$phase) {
                    $scope.$apply();
                }
            }
        ).error();
    };

        $scope.findServices();

    });
