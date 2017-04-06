/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

/**
 * @file 加载器, 用于在加载构建或者日志的时候显示加载效果
 * @author
 */

define(['angular', 'angular-animate'], function (angular, angularAnimate) {

    'use strict';

    angular.module('agile.loading', [])
        .run(['$templateCache', function ($templateCache) {
            'use strict';
            $templateCache.put('agile-loading.html',
                '<div class=\"agile-loading-default-wrapper\">\n'
                + '    <div class=\"agile-loading-default-sign\">\n'
                + '        <div class=\"agile-loading-default-spinner\">\n'
                + '            <div class=\"bar1\"></div>\n'
                + '            <div class=\"bar2\"></div>\n'
                + '            <div class=\"bar3\"></div>\n'
                + '            <div class=\"bar4\"></div>\n'
                + '            <div class=\"bar5\"></div>\n'
                + '            <div class=\"bar6\"></div>\n'
                + '        </div>\n'
                + '        <div ng-show="$showMessage" class="agile-loading-default-text">{{$message}}</div>'
                + '    </div>\n'
                + '</div>'
            );
        }])
        .factory('loadingTrackerFactory', ['$timeout', '$q', loadingTrackerFactory])
        .value('loadingDefaults', {})
        .directive('agileLoading',
        [
            '$compile',
            '$templateCache',
            'loadingDefaults',
            '$http',
            'loadingTrackerFactory',
            agileLoading
        ]);

    function loadingTrackerFactory($timeout, $q) {
        return function () {
            var tracker = {};
            tracker.promises = [];
            tracker.delayPromise = null;
            tracker.durationPromise = null;
            tracker.delayJustFinished = false;


            tracker.isPromise = function (promiseThing) {
                var then = promiseThing && (promiseThing.then || promiseThing.$then
                    || (promiseThing.$promise && promiseThing.$promise.then));

                return typeof then !== 'undefined';
            };

            tracker.callThen = function (promiseThing, success, error) {
                var promise;
                if (promiseThing.then || promiseThing.$then) {
                    promise = promiseThing;
                } else if (promiseThing.$promise) {
                    promise = promiseThing.$promise;
                } else if (promiseThing.denodeify) {
                    promise = $q.when(promiseThing);
                }

                var then = (promise.then || promise.$then);

                then.call(promise, success, error);
            };


            tracker.active = function () {
                if (tracker.delayPromise) {
                    return false;
                }

                if (!tracker.delayJustFinished) {
                    if (tracker.durationPromise) {
                        return true;
                    }
                    return tracker.promises.length > 0;
                } else {
                    // if both delay and min duration are set,
                    // we don't want to initiate the min duration if the
                    // promise finished before the delay was complete
                    tracker.delayJustFinished = false;
                    if (tracker.promises.length === 0) {
                        tracker.durationPromise = null;
                    }
                    return tracker.promises.length > 0;
                }
            };

            var addPromiseLikeThing = function (promise) {

                if (!tracker.isPromise(promise)) {
                    throw new Error('agileLoading expects a promise (or something that has a .promise or .$promise');
                }

                if (tracker.promises.indexOf(promise) !== -1) {
                    return;
                }
                tracker.promises.push(promise);

                tracker.callThen(promise, function () {
                    promise.$cgBusyFulfilled = true;
                    if (tracker.promises.indexOf(promise) === -1) {
                        return;
                    }
                    tracker.promises.splice(tracker.promises.indexOf(promise), 1);
                }, function () {
                    promise.$cgBusyFulfilled = true;
                    if (tracker.promises.indexOf(promise) === -1) {
                        return;
                    }
                    tracker.promises.splice(tracker.promises.indexOf(promise), 1);
                });
            };


            tracker.reset = function (options) {
                tracker.minDuration = options.minDuration;

                tracker.promises = [];
                angular.forEach(options.promises, function (p) {
                    if (!p || p.$cgBusyFulfilled) {
                        return;
                    }
                    addPromiseLikeThing(p);
                });

                if (tracker.promises.length === 0) {
                    // if we have no promises then dont do the delay or duration stuff
                    return;
                }
                tracker.delayJustFinished = false;
                if (options.delay) {
                    tracker.delayPromise = $timeout(function () {
                        tracker.delayPromise = null;
                        tracker.delayJustFinished = true;
                    }, parseInt(options.delay, 10));
                }
                if (options.minDuration) {
                    tracker.durationPromise = $timeout(function () {
                        tracker.durationPromise = null;
                    }, parseInt(options.minDuration, 10) + (options.delay ? parseInt(options.delay, 10) : 0));
                }
            };

            return tracker;
        };
    }


    function agileLoading($compile, $templateCache, loadingDefaults, $http, loadingTrackerFactory) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs, fn) {

                // Apply position:relative to parent element if necessary
                var position = element.css('position');
                if (position === 'static' || position === '' || typeof position === 'undefined') {
                    element.css('position', 'relative');
                }

                var templateElement;
                var backdropElement;
                var currentTemplate;
                var templateScope;
                var backdrop;
                var tracker = loadingTrackerFactory();

                var defaults = {
                    templateUrl: 'agile-loading.html',
                    delay: 0,
                    minDuration: 0,
                    backdrop: true,
                    message: '',
                    showMessage: false,
                    wrapperClass: 'agile-loading agile-loading-animation'
                };

                angular.extend(defaults, loadingDefaults);

                scope.$watchCollection(attrs.agileLoading, function (options) {

                    if (!options) {
                        options = {promise: null};
                    }

                    if (angular.isString(options)) {
                        throw new Error('Invalid value for agile-loading. agileLoading no longer accepts string ids'
                            + ' to represent promises/trackers.');
                    }

                    // is it an array (of promises) or one promise
                    if (angular.isArray(options) || tracker.isPromise(options)) {
                        options = {promise: options};
                    }

                    options = angular.extend(angular.copy(defaults), options);

                    if (!options.templateUrl) {
                        options.templateUrl = defaults.templateUrl;
                    }

                    if (!angular.isArray(options.promise)) {
                        options.promise = [options.promise];
                    }

                    if (!templateScope) {
                        templateScope = scope.$new();
                    }

                    templateScope.$message = options.message;
                    templateScope.$showMessage = options.showMessage;

                    if (!angular.equals(tracker.promises, options.promise)) {
                        tracker.reset({
                            promises: options.promise,
                            delay: options.delay,
                            minDuration: options.minDuration
                        });
                    }

                    templateScope.$agileLoadingIsActive = function () {
                        return tracker.active();
                    };


                    if (!templateElement || currentTemplate !== options.templateUrl || backdrop !== options.backdrop) {

                        if (templateElement) {
                            templateElement.remove();
                        }
                        if (backdropElement) {
                            backdropElement.remove();
                        }

                        currentTemplate = options.templateUrl;
                        backdrop = options.backdrop;

                        $http.get(currentTemplate, {cache: $templateCache}).success(function (indicatorTemplate) {

                            options.backdrop = typeof options.backdrop === 'undefined' ? true : options.backdrop;

                            if (options.backdrop) {
                                var backdrop = '<div class="agile-loading agile-loading-backdrop'
                                    + 'agile-loading-backdrop-animation ng-hide" ng-show="$agileLoadingIsActive()">'
                                    + '</div>';
                                backdropElement = $compile(backdrop)(templateScope);
                                element.append(backdropElement);
                            }

                            var template = '<div class="' + options.wrapperClass + ' ng-hide"'
                                + ' ng-show="$agileLoadingIsActive()">' + indicatorTemplate + '</div>';
                            templateElement = $compile(template)(templateScope);

                            angular.element(templateElement.children()[0])
                                .css('position', 'absolute')
                                .css('top', 0)
                                .css('left', 0)
                                .css('right', 0)
                                .css('bottom', 0);
                            element.append(templateElement);

                        }).error(function (data) {
                            throw new Error('Template specified for agileLoading (' + options.templateUrl + ') could'
                                + ' not be loaded. ' + data);
                        });
                    }
                }, true);
            }
        };
    }
});


