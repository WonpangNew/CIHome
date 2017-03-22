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
        IndexController
    ]);
    
    function IndexController($scope, $location, $state, $window, pipelineDataService,
                             pipelineContextService, permissionService) {
        var self = this;

        self.states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];

        self.context = pipelineContextService.context;
        self.searchModule = undefined;

        self.getSearchModules = function (searchVal, username) {
            return pipelineDataService.getSearchModules(searchVal, username);
        };

        self.moduleSelected = function (module) {
            module && pipelineContextService.initContext(module);
        };

        self.exitLogin = function () {
            permissionService.exitLogin(self.context.username)
                .then(function (result) {
                    if (result) {
                        $window.location.reload();
                    }
                });
        };
    }
});