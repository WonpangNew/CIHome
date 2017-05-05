/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

/**
 * @file 发布expend指令
 */
define(['app', 'constants'], function (app, constants) {
    app.directive(
        'appOverviewRelease',
        [
            'pipelineDataService',
            function (pipelineDataService) {
                return {
                    restrict: 'E',
                    scope: {
                        currentBuild: '=appPipelineBuild',
                        isCanRelease: '@appCanRelease'
                    },
                    templateUrl: constants.resource('directive/overview-release.html'),
                    replace: true,
                    link: function (scope, el, attr) {
                        pipelineDataService.getReleaseDetail(scope.currentBuild.pipelineBuildId)
                            .then(function (data) {
                                scope.releaseDetail = data;
                            });
                    }
                }
            }
        ]
    );
});
