/**
 * Created by niuwanpeng on 17/3/17.
 */
/**
 * @file ContextService
 */

define(['app', 'angular', 'constants'], function appPlContextService(app, angular, constants) {
    app.service('pipelineContextService',
        [
            '$q', '$http', '$state', '$window', '$timeout', 'localStorageService', ContextService
        ]
    );

    function ContextService($q, $http, $state, $window, $timeout, localStorageService) {
        var self = this;

        self.context = {
            module: '',
            username: '',
            branchType: 'TRUNK',
        };

        self.setModule = function (module) {
            self.context.module = module;
        };

        self.setUsername = function (username) {
            self.context.username = username;
        };

        self.setBranchType = function (branchType) {
            self.context.branchType = branchType;
        };

        self.selectModule = function (module) {
            self.context.module = module;
            localStorageService.addRecentModule(module);
            self.initContext();
            $state.go(
                'builds.trunk',
                {
                    module: self.context.module
                }
            );
        };

        self.initContext = function () {
            var lastVisitModule = localStorageService.getRecentModule();
            self.context.module = lastVisitModule;
            self.context.username = localStorageService.getLoginUser();
        };
    }
});

