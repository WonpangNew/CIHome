/**
 * Created by niuwanpeng on 17/5/5.
 *
 * 发布记录
 */

define(['app', 'angular'], function (app, angular) {
    'use strict';

    app.controller('ReleaseHistoryController', [
        'pipelineDataService',
        'pipelineContextService',
        '$scope',
        '$state',
        ReleaseHistoryController
    ]);

    function ReleaseHistoryController(pipelineDataService, pipelineContextService, $scope, $state) {

        var self = this;
        self.context = pipelineContextService.context;
        self.currentModule = self.context.module;
        self.username = self.context.username;

        self.currentRevision = '123456';
        self.initReleasesDone = false;
        self.showLoadMoreBuildsLoader = false;
        self.selectRelease = $state.params.selectRelease;
        self.noMoreBuildsToLoad = false;

        pipelineDataService.getReleaseHistory(self.username, self.currentModule, 0)
            .then(function (data) {
                self.releaseHistorys = data instanceof Array ? data : [];
                self.initReleasesDone = true;
            });

        self.loadMoreReleaseHistory = function () {
            var lastReleaseId = self.releaseHistorys[self.releaseHistorys.length - 1].id;
            if (angular.isDefined(lastReleaseId)) {
                self.showLoadMoreBuildsLoader = true;
                pipelineDataService.getReleaseHistory(self.username, self.currentModule, lastReleaseId)
                    .then(function (data) {
                        if (data instanceof  Array && data.length > 0) {
                            angular.forEach(data, function (data) {
                                self.releaseHistorys.push(data);
                                self.showLoadMoreBuildsLoader = false;
                            });
                        } else {
                            self.noMoreBuildsToLoad = true;
                        }
                    });
            }
        };

        $scope.$watch(function () {
            return self.context.module;
        }, function (module) {
            module && $state.go(
                'release',
                {
                    module: module
                }
            );
        });
    }

});

