/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

/**
 * @file 发布指令
 */
define(['app', 'constants'], function (app, constants) {
    app.directive(
        'appBuildRelease',
        [
            'pipelineDataService',
            '$uibModal',
            '$q',
            '$http',
            '$state',
            '$stateParams',
            function (pipelineDataService, $uibModal, $q, $http, $state, $stateParams) {
                return {
                    restrict: 'A',
                    scope: {
                        pipelineBuild: '=appPipelineBuild',
                        isCanRelease: '@appCanRelease',
                        isAnyPipelineRelease: '@appAnyRelease'
                    },
                    link: function (scope, el, attr) {
                    }

                }
            }
            ]
    )
});
