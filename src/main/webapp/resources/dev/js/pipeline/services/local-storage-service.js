/**
 * Created by niuwanpeng on 17/3/17.
 *
 * cihome本地存储服务
 */

define(['app', 'angular'], function (app, angular) {


    app.factory('localStorageService', ['$window', '$log', LocalStorageService]);


    var MAX_RECENT_MODULE = 5;

    function LocalStorageService($window) {


        var storageKeyPrefix = 'ciHomeLocalStorage-';
        var serializer = angular.toJson;
        var deserializer = angular.fromJson;


        this.setKeyPrefix = function (prefix) {
            if (typeof prefix !== 'string') {
                throw new TypeError('[app.localStorage] - ' + 'setKeyPrefix() expects a' + ' String.');
            }
            storageKeyPrefix = prefix;
        };

        this.addLoginUser = function (username) {
            this.setKeyPrefix('login_user');
            this.put('', username);
        };

        this.removeLoginUser = function () {
            this.remove('login_user');
        };

        this.getLoginUser = function () {
            this.setKeyPrefix('login_user');
            var username = this.get('');
            return username ? username : ''
        };

        this.addRecentModule = function (module) {

            this.setKeyPrefix('recentModules');
            if (typeof module !== 'string') {
                throw new TypeError('app.localStorage addRecentModule error, module is not string!');
            }
            var recentModuleQueue = this.get('') || [];

            var containsThisModule = false;
            angular.forEach(recentModuleQueue, function (value) {

                if (value !== null && value.name === module) {
                    value.visitTime = Date.now();
                    containsThisModule = true;
                }
            });
            if (!containsThisModule) {

                if (recentModuleQueue.length === MAX_RECENT_MODULE) {
                    this.sort(recentModuleQueue);
                    recentModuleQueue.pop();
                }

                recentModuleQueue.unshift({
                    name: module,
                    visitTime: Date.now()
                });
            }
            this.sort(recentModuleQueue);
            this.put('', recentModuleQueue);
        };

        this.sort = function (aArray) {
            aArray.sort(function (m1, m2) {
                if (m1 === null && m2 !== null) {
                    return 1;
                } else if (m1 !== null && m2 === null) {
                    return -1;
                } else if (m1 === null && m2 === null) {
                    return 0;
                } else {
                    if (m1.visitTime > m2.visitTime) {
                        return -1;
                    } else if (m1.visitTime === m2.visitTime) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        };

        this.cleanNull = function (aArray) {
            for (var i = 0; i < aArray.length; i++) {
                if (aArray[i] === null) {
                    aArray.splice(i, 1);
                    i--;
                }
            }
            return aArray;
        };

        this.getRecentModules = function () {
            this.setKeyPrefix('recentModules');
            var originRecentModules = this.cleanNull(this.get('') || []);
            var shouldReturnModules = [];
            angular.forEach(originRecentModules, function (value) {
                shouldReturnModules.push(value.name);
            });
            return shouldReturnModules;
        };

        this.getRecentModule = function () {
            var modules = this.getRecentModules();
            return modules && modules.length ? modules[0] : '';
        };


        this.get = function (key) {
            return deserializer($window.localStorage.getItem(storageKeyPrefix + key));
        };


        this.put = function (key, value) {
            $window.localStorage.setItem(storageKeyPrefix + key, serializer(value));
        };

        this.setUnSerializer = function (key, value) {
            $window.localStorage.setItem(storageKeyPrefix + key, value);
        };
        this.getUnSerializer = function (key) {
            return $window.localStorage.getItem(storageKeyPrefix + key);
        };


        this.remove = function (key) {
            $window.localStorage.removeItem(key);
        };


        this.clearAll = function () {
            $window.localStorage.clear();
        };

        return this;

    }


});