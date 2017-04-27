/**
 * Created by Wonpang New on 2016/9/10.
 *
 * 主干流水线
 */

define(['app'], function (app) {
    'use strict';

    app.controller('TrunkController', [
        'pipelineDataService',
        '$scope',
        '$location',
        '$state',
        'pipelineContextService',
        TrunkController
    ]);

    function TrunkController(pipelineDataService, $scope, $location, $state, pipelineContextService) {

        var self = this;
        pipelineContextService.setBranchType('TRUNK');
        self.context = pipelineContextService.context;
    }
});