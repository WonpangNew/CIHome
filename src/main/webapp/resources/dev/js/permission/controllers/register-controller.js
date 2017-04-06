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
        self.githubPassword = '';
        self.isSyncGithub = false;
        self.showLoading = false;
        self.usernameRepeat = true;
        self.checkEmailPass = false;

        self.getInitParams = function () {
            return {
                username: self.username,
                password: self.password,
                gitHubToken: self.githubPassword,
                email: self.email,
                syncGithub: self.isSyncGithub
            }
        };

        self.register = function () {
            self.showLoading = true;
            var params = self.getInitParams();
            self.initResult = registerService.register(params)
                .then(function (result) {
                    var result = angular.fromJson(result);
                    if (result.REGISTER_STATUS === true) {
                        $window.location.href = "http://localhost:8080/";
                    } else {
                        $window.location.reload();
                    }
                });
        };

        self.judgeUsernameRepeat = function () {
            registerService.judgeUsernameRepeat(self.username)
                .then(function (result) {
                    self.usernameRepeat = result;
                });
        }

        self.cancel = function () {
            $window.location.href = "http://localhost:8080/";
        };

        $scope.$watch(function () {
            if (self.checkPassword !== '') {
                return self.password === self.checkPassword;
            } else {
                return true;
            }
        }, function (result) {
            self.checkPasswordPass = result;
        });

        $scope.$watch(function () {
            self.checkEmailPass = self.email ? true : false;
        });

        $scope.$watch(function () {
            self.canGoOn = self.checkPassword && self.checkEmailPass && !self.usernameRepeat;
        });
    }
});