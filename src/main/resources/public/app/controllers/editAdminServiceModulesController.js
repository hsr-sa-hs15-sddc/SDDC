'use strict';

sddcDashboard.controller('editAdminServiceModulesController', function($scope,$http,$routeParams, ServiceModule){

    $scope.servicemodule = ServiceModule.get({id: $routeParams.servicemoduleId});

    $scope.updateServiceModule = function(module) {
        ServiceModule.update({ id:$routeParams.servicemoduleId }, module);
    };

    });
