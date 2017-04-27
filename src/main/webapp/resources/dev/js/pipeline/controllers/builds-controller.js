/**
 * Created by Wonpang New on 2016/9/10.
 *
 */

define(['app'], function (app) {
    'use strict';

    app.controller('BuildsController', [
        '$scope',
        '$location',
        '$state',
        'pipelineContextService',
        BuildsController
    ]);

    function BuildsController($scope, $location, $state, pipelineContextService) {
        var self = this;

        self.context = pipelineContextService.context;

    }
});