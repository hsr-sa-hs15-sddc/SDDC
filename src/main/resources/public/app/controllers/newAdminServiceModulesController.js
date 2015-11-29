'use strict';

sddcDashboard.controller('newAdminServiceModulesController', function($scope,$http,$location,$routeParams, newServiceModule){

    $scope.module = new newServiceModule;

    $scope.save = function(module){
            $scope.module.$save()
                .then(function(module){
                    $location.path('/admin/servicemodules/');
                });
    }

    });
