/**
 * Created by Wonpang New on 2016/9/10.
 * 
 * router angular入口
 */

define(
    [
        'app',
        'angular',
        'angular-ui-router',
        'ctrls'
    ], function (app) {
        'use strict';
        
        function config($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
            
            $stateProvider
                .state ('default', {
                    url: '/'
                })
        }

        app.config(['$stateProvider', '$urlRouterProvider', config]);
    }
)
