/**
 * Created by Wonpang New on 2016/9/10.
 */

define(['app'], function (app) {
    'use strict';
    
    app.controller('IndexController', [
        '$scope',
        '$location',
        '$state',
        '$window',
        'pipelineDataService',
        'permissionService',
        IndexController
    ]);
    
    function IndexController($scope, $location, $state, $window, pipelineDataService, permissionService) {
        var self = this;

        self.getSearchModules = function (searchVal) {
            return pipelineDataService.getSearchModules(searchVal);
        };

        self.exitLogin = function (username) {
            permissionService.exitLogin(username)
                .then(function (result) {
                    if (result) {
                        $window.location.reload();
                    }
                });
        };
    }
});