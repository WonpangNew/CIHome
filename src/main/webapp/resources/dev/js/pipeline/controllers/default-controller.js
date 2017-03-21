/**
 * @file 默认自动加载流水线 Controller
 *
 */
define(['app', 'constants'], function (app, constants) {
    'use strict';

    app.controller('DefaultController', [
        'localStorageService',
        '$state',
        function (localStorageService, $state) {
            var lastVisitModule = localStorageService.getRecentModule();
            var lastState = 'builds.trunk';
            if (typeof lastVisitModule !== 'string' && lastVisitModule === '') {
                lastVisitModule = 'notStorage';
            } else {
                localStorageService.addRecentModule(lastVisitModule);
            }
            var lastParams = {
                module: lastVisitModule
            };

            $state.go(lastState, lastParams);
        }
    ]);
});
