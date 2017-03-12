/**
 * Created by Wonpang New on 2016/9/10.
 * 
 * router angular入口
 */

define(
    [
        'app',
        'angular',
        'constants',
        'angular-ui-router',
        'ctrls'
    ], function (app, constants) {
        'use strict';
        
        function config($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
            
            $stateProvider
                .state ('default', {
                    url: '/',
                    controller: 'IndexController'
                })
        }

        app.config(['$stateProvider', '$urlRouterProvider', config]);
    }
)
