'use strict';

sddcDashboard.controller('editAdminServiceModulesController', function($scope,$http,$routeParams,$location, ServiceModule){

    $scope.module = ServiceModule.get({id: $routeParams.servicemoduleId});

    $scope.updateServiceModule = function(module) {
        ServiceModule.update({ id:$routeParams.servicemoduleId }, module);
        $location.path('/admin/servicemodules/');
    };

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
