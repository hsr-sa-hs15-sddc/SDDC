'use strict';

sddcDashboard.controller('orderedDetailService',
    function orderedDetailService($scope, $routeParams, OrderedService) {
  $scope.service = OrderedService.get({id: $routeParams.orderedserviceId});

});
