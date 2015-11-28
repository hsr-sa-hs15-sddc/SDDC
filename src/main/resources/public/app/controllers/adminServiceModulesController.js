'use strict';

sddcDashboard.controller('adminServiceModulesController', function($scope, $http, $location,$route){
        $scope.servicemodules = [];

        $scope.findServiceModules = function () {
            $http.get("/api/servicemodules").success(function (data) {
                $scope.servicemodules = data;
            })
        };


        $scope.deleteServiceModule = function(module) {
            $http.delete("/api/servicemodules/{id}".replace('{id}',module.id)).success(function (data)
            {
                if ( ! $scope.$$phase) {
                    $scope.$apply();
                }
            }
            ).error();
        };



        $scope.findServiceModules();

    });
