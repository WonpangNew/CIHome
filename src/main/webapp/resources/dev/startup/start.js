/**
 * Created by Wonpang New on 2016/9/10.
 * @file 启动angular
 */
define([
        'require',
        'jquery',
        'angular',
        'angular-ui-router',
        'angular-cookies',
        'angular-sanitize',
        'angular-bindonce',
        'angular-resource',
        'angular-animate',
        'angular-bootstrap',
        'ng-tags-input',
        'app',
        'router',
        'ctrls',
        'servs',
        'dirs',
    ],
    function (angular) {
        'use strict';
        require(['angular', 'router'], function(angular){
            angular.bootstrap(document, ['app']);
        });
    });
