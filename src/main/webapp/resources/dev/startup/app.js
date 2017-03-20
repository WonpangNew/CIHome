/**
 * Created by Wonpang New on 2016/9/10.
 * @file angular 入口
 */

define([
        'angular',
        'angular-animate',
        'angular-ui-router',
        'angular-bindonce',
        'angular-bootstrap',
        'ng-tags-input'
    ], function (angular) {
        'use strict';
        angular.module('app',
            [
                'ui.router',
                'pasvaz.bindonce',
                'ui.bootstrap',
                'ngTagsInput'
            ]).factory('cihome', ['$q', function ($q) {
            return {
                responseError: function (rejection) {
                    if (rejection.status === 403) {
                        document.write(rejection.data);
                        document.close();
                        return;
                    }
                    return $q.reject(rejection);
                }
            };
        }]).config(['$httpProvider', function ($httpProvider) {
            $httpProvider.interceptors.push('cihome');
        }]);

        return angular.module('app');
    });
