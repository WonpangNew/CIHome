/**
 * Created by Wonpang New on 2016/9/10.
 *
 *  全部分支流水线
 */

define(['app', 'angular'], function (app, angular) {
    'use strict';

    app.controller('BranchesController', [
        'pipelineDataService',
        '$scope',
        '$location',
        '$state',
        'pipelineContextService',
        BranchesController
    ]);

    function BranchesController(pipelineDataService, $scope, $location, $state, pipelineContextService) {
        var self = this;
        pipelineContextService.setBranchType('BRANCH');
        self.context = pipelineContextService.context;
        self.module = $state.params.module || self.context.module;
        pipelineContextService.setModule(self.module);
        self.showLoadMoreBuildsLoader = false;
        self.noMoreBuildsToLoad = false;
        self.initBuildsDone = false;

        self.initBuilds = pipelineDataService.getBranchesPipelines(self.context.username, self.module, 0)
            .then(function (data) {
                self.branchPipelines = data;
                self.initBuildsDone = true;
            })
            .then(function () {
                pipelineDataService.getBranches(self.context.username, self.module)
                    .then(function (data) {
                        self.branches = data instanceof Array ? data : [];
                    });
            });

        self.loadSingleBranchPipelines = function (fold, branch, builds) {
            if (!fold) {
                pipelineDataService.getBranchPipelines(self.context.username, self.module, branch, 0)
                    .then(function (data) {
                        self.branchPipelines[branch] = data;
                    });
            }
        };

        self.loadMoreBuilds = function () {
            var lastBranchId = self.branchPipelines[self.branchPipelines.length - 1].branchId;
            if (angular.isDefined(lastBranchId)) {
                self.showLoadMoreBuildsLoader = true;
                pipelineDataService.getTrunkPipelines(self.context.username, self.module, lastBranchId)
                    .then(function (data) {
                        if (data instanceof  Array && data.length > 0) {
                            angular.forEach(data, function (data) {
                                self.pipelineBuilds.push(data);
                            });
                        }
                        else {
                            self.noMoreBuildsToLoad = true;
                        }
                        self.showLoadMoreBuildsLoader = false;
                    });
            }
        };
    }
});