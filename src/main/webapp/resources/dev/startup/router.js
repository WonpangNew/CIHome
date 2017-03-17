/**
 * Created by Wonpang New on 2016/9/10.
 * 
 * router angular入口
 */

define(
    [
        'app',
        'constants',
        'angular',
        'angular-ui-router',
        'ctrls'
    ], function (app, constants) {
        'use strict';
        
        function config($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.when('', '/');
            
            $stateProvider
                .state('default', {
                    url: '/',
                    controller: 'DefaultController'
                })

        }

        app.config(['$stateProvider', '$urlRouterProvider', config]);
    }
)
