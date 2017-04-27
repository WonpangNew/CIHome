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
        'pipelineContextService',
        'permissionService',
        'localStorageService',
        IndexController
    ]);
    
    function IndexController($scope, $location, $state, $window, pipelineDataService,
                             pipelineContextService, permissionService, localStorageService) {
        var self = this;
        pipelineContextService.initContext();
        self.context = pipelineContextService.context;
        self.searchModule = undefined;

        self.getSearchModules = function (searchVal, username) {
            return pipelineDataService.getSearchModules(searchVal, username);
        };

        self.moduleSelected = function (module) {
            module && pipelineContextService.selectModule(module);
        };

        self.exitLogin = function () {
            localStorageService.remove('recentModules');
            localStorageService.removeLoginUser();
            permissionService.exitLogin(self.context.username)
                .then(function (result) {
                    if (result) {
                        $window.location.href = "http://localhost:8080/";
                    }
                });
        };
    }
});