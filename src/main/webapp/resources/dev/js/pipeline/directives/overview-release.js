/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

/**
 * @file 发布指令
 */
define(['app', 'constants'], function (app, constants) {
    app.directive(
        'appOverriewRelease',
        [
            'pipelineDataService',
            function (pipelineDataService) {
                return {
                    restrict: 'E',
                    scope: {
                        pipelineBuild: '=appPipelineBuild',
                        isCanRelease: '@appCanRelease',
                        isAggregationRelease: '@'
                    },
                    templateUrl: constants.resource('directive/overview-release.html'),
                    replace: true,
                    link: function (scope, el, attr) {

                        scope.isDirectoryModule = false;

                        scope.releaseInfo = {};

                        scope.releaseDetailInfo = {};

                    }
                }
            }
        ]
    );
});
