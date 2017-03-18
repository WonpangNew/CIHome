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
        
        function config($stateProvider, $urlRouterProvider, $urlMatcherFactoryProvider) {

            $urlRouterProvider.otherwise('/');

            $urlMatcherFactoryProvider.type('path', {
                encode: function (path) {
                    return path;
                },
                decode: function (path) {
                    return path;
                },
                is: function (path) {
                    return true;
                },
                pattern: /[^@]*/
            });

            $stateProvider
                .state('default', {
                    url: '/',
                    controller: 'DefaultController'
                })
                .state('builds', {
                    url: '/builds/{module:path}',
                    templateUrl: constants.resource('pipeline/pipeline.html'),
                    controller: 'BuildsController',
                    controllerAs: 'buildsCtrl'
                })
                .state('builds.trunk', {
                    url: '@trunk',
                    templateUrl: constants.resource('pipeline/builds.trunk.html'),
                    controller: 'TrunkController',
                    controllerAs: 'trunkCtrl'
                })
                .state('builds.branches', {
                    url: '@branches',
                    templateUrl: constants.resource('pipeline/builds.branches.html'),
                    controller: 'BranchesController',
                    controllerAs: 'branchesCtrl'
                })
                .state('builds.branch', {
                    url: '@branch/{branch:path}',
                    templateUrl: constants.resource('pipeline/builds.branch.html'),
                    controller: 'BranchController',
                    controllerAs: 'branchCtrl'
                });

        }

        app.config(['$stateProvider', '$urlRouterProvider', '$urlMatcherFactoryProvider', config]);
    }
)
