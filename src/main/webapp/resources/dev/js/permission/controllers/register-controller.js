/**
 * Created by Wonpang New on 2016/9/10.
 */

define(['app'], function (app) {
    'use strict';

    app.controller('RegisterController', [
        'registerService',
        '$scope',
        '$state',
        '$window',
        '$location',
        RegisterController
    ]);

    function RegisterController(registerService, $scope, $state, $window, $location) {
        var self = this;
        self.username = '';
        self.password = '';
        self.checkPassword = '';
        self.email = '';
        self.password = '';

        $scope.$watch(function () {
            if (self.checkPassword !== '') {
                return self.password === self.checkPassword;
            } else {
                return true;
            }
        }, function (result) {
            self.checkPasswordPass = result;
        })
    }
});