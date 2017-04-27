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
        'localStorageService',
        'pipelineContextService',
        PermissionController
    ]);

    function PermissionController(permissionService, $scope, $state, $window, $location,
                                  localStorageService, pipelineContextService) {
        var self = this;
        self.loginStatus = 'INIT';
        self.loginMessage = '';

        self.username = '';
        self.password = '';
        self.allowClickLogin = false;

        self.loginSystem = function () {
            permissionService.loginSystem(self.username, self.password)
                .then(function (result) {
                    var result = angular.fromJson(result);
                    self.loginStatus = result.LOGIN_STATUS;
                    self.loginMessage = result.MESSAGE;
                    if ('SUCC' === self.loginStatus) {
                        pipelineContextService.setUsername(self.username);
                        localStorageService.addLoginUser(self.username);
                        $window.location.reload();
                    }
            });
        };

        $scope.$watch(function () {
            return self.username !== '' && self.password !== '';
        }, function (result) {
            self.allowClickLogin = result;
        })
    }
});