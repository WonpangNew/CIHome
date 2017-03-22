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
            username: ''
        };

        self.setModule = function (module) {
            self.context.module = module;
        }

        self.setUsername = function (username) {
            self.context.username = username;
        }

        self.initContext = function (module) {

            self.context.module = module;
            localStorageService.addRecentModule(module);
            $state.go(
                'builds.trunk',
                {
                    module: self.context.module
                }
            );
        };
    }
});

