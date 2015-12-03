'use strict';

sddcDashboard.controller('newAdminServiceModulesController', function($scope,$http,$location,$routeParams,newServiceModule){

    $scope.module = new newServiceModule;

    $scope.save = function(module){
        $scope.module.$save()
            .then(function(module){
            $location.path('/admin/servicemodules/');
        });
    }

    $scope.providers = [];

    $scope.findProviders = function () {
        $http.get("/api/servicemodules/providers").success(function (data) {
            $scope.providers = data;
        })
    };

    $scope.findProviders();

    $scope.categories = [];

    $scope.findCategories = function () {
        $http.get("/api/servicemodules/categories").success(function (data) {
            $scope.categories = data;
        })
    };

    $scope.findCategories();

    $scope.sizes = [];

    $scope.findSizes = function () {
        $http.get("/api/servicemodules/sizes").success(function (data) {
            $scope.sizes = data;
        })
    };

	$scope.findSizes();
    



    });
