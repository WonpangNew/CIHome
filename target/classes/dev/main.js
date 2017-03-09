/**
 * Created by Wonpang New on 2016/9/10.
 */

require.config ({
    paths: {
        'app': 'startup/app',
        'router': 'startup/router',
        'start': 'startup/start',
        'ctrls': 'startup/controllers',
        'servs': 'startup/services',
        'dirs': 'startup/directives',
        'require-css': '../common/lib/require-css/css',
        'jquery': '../common/lib/jquery/js/jquery.min',
        'angular': '../common/lib/angular/js/angular.min',
        'angular-ui-router': '../common/lib/angular-ui-router/js/angular-ui-router',
        'angular-bindonce': '../common/lib/angular-bindonce/js/bindonce',
        'angular-animate': '../common/lib/angular-animate/js/angular-animate.min',
        'angular-cookies': '../common/lib/angular-cookies/js/angular-cookies.min',
        'angular-resource': '../common/lib/angular-resource/js/angular-resource.min',
        'angular-sanitize': '../common/lib/angular-sanitize/js/angular-sanitize.min',
        'angular-bootstrap': '../common/lib/angular-bootstrap/js/ui-bootstrap-tpls',
        'ng-tags-input': '../common/lib/ng-tags-input/js/ng-tags-input',
        'constants': '../common/widget/constants/constants'
    },
    map: {
        '*': {
            css: 'require-css'
        }
    },
    shim: {
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular'],
            exports: 'angularRoute'
        },
        'angular-animate': {
            deps: ['angular'],
            exports: 'angularAnimate'
        },
        'angular-resource': {
            deps: ['angular'],
            exports: 'angularResource'
        },
        'angular-cookies': {
            deps: ['angular'],
            exports: 'angularCookies'
        },
        'angular-sanitize': {
            deps: ['angular'],
            exports: 'angularSanitize'
        },
        'angular-ui-router': {
            deps: ['angular'],
            exports: 'angularUIRouter'
        },
        'angular-bindonce': {
            deps: ['angular'],
            exports: 'angularBindonce'
        },
        'angular-bootstrap': {
            deps: ['angular'],
            exports: 'angularBootstrap'
        },
        'ng-tags-input': {
            deps: ['angular'],
            exports: 'ngTagsInput'
        }
    },
    deps: ['start', 'require-css']
});
