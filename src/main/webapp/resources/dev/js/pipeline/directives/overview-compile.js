/**
 * Created by niuwanpeng on 17/5/4.
 */
/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

/**
 * @file 编译指令
 */
define(['app', 'constants'], function (app, constants) {
    app.directive(
        'appOverviewCompile',
        [
            'pipelineDataService',
            function (pipelineDataService) {
                return {
                    restrict: 'E',
                    scope: {
                        currentBuild: '=appPipelineBuild',
                    },
                    templateUrl: constants.resource('directive/overview-compile.html'),
                    replace: true,
                    link: function (scope, el, attr) {
                        pipelineDataService.getCompileBuildDetail(scope.currentBuild.pipelineBuildId)
                            .then(function (data) {
                                scope.compileBuildDetail = data;
                            });
                    }
                };
            }
        ]
    );
});

