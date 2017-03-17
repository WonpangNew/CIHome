/**
 * Created by Wonpang New on 2016/9/10.
 */

define(['app'], function (app) {
    'use strict';

    app.controller('PermissionController', [
        'permissionService',
        '$scope',
        '$location',
        PermissionController
    ]);

    function PermissionController(permissionService, $scope, $location) {
        var self = this;

        $scope.username = '';
        $scope.password = '';

        self.loginSystem = function () {
            permissionService.loginSystem($scope.username, $scope.password);
        };
    }
});