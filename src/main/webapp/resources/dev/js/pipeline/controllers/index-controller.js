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

    }
});