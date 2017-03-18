/**
 * Created by Wonpang New on 2016/9/10.
 */

define(['app'], function (app) {
    'use strict';

    app.controller('PermissionController', [
        'permissionService',
        '$scope',
        '$state',
        '$window',
        '$location',
        PermissionController
    ]);

    function PermissionController(permissionService, $scope, $state, $window, $location) {
        var self = this;
        self.loginStatus = 'INIT';
        self.loginMessage = '';

        $scope.username = '';
        $scope.password = '';

        self.loginSystem = function () {
            permissionService.loginSystem($scope.username, $scope.password)
                .then(function (result) {
                    var result = angular.fromJson(result);
                    self.loginStatus = result.LOGIN_STATUS;
                    self.loginMessage = result.MESSAGE;
                    if ('SUCC' === self.loginStatus) {
                        $window.location.reload();
                    }
            });
        };
    }
});