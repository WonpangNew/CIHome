/**
 * Created by Wonpang New on 2016/9/10.
 */

define(['app'], function (app) {
    'use strict';
    
    app.controller('IndexController', [
        'bookService',
        '$scope',
        '$location',
        IndexController
    ]);
    
    function IndexController(bookService, $scope, $location) {
        var self = this;
        
        $scope.username = "wangwu";
        $scope.absUrl = $location.absUrl();
        $scope.protocol = $location.protocol();
        $scope.host = $location.host();
        $scope.port = $location.port();
        
        self.saveUser = function () {
            console.log(11111);
            bookService.getName($scope.username);
        };
    }
});