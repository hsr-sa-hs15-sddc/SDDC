'use strict';

sddcDashboard.controller('detailAdminServiceModulesController',
    function detailServiceModule($scope, $routeParams, ServiceModule) {
  $scope.module = ServiceModule.get({id: $routeParams.servicemoduleId});

});
