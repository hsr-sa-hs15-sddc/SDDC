'use strict';

sddcDashboard.controller('adminServicesController', function($scope, $http){
        $scope.services = [];

        $scope.findServices = function () {
            $http.get("/api/services").success(function (data) {
                $scope.services = data;
            })
        };

        $scope.findServices();

    });
