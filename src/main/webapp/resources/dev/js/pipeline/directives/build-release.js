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
            'modalService',
            '$q',
            '$http',
            '$state',
            '$stateParams',
            function (pipelineDataService, $uibModal, modalService, $q, $http, $state, $stateParams) {
                return {
                    restrict: 'A',
                    scope: {
                        pipelineBuild: '=appPipelineBuild',
                        isCanRelease: '@appCanRelease',
                        context: '=appReleaseContext'
                    },
                    link: function (scope, el, attr) {

                        scope.tool = {
                            threeVersion: '',
                            fourthVersion: 1,
                            version: '',
                            remarks: '',
                            getThreeVersion: function () {
                                pipelineDataService.getThreeVersion(scope.pipelineBuild.moduleId, scope.pipelineBuild.branchName)
                                    .then(function (data) {
                                        scope.tool.threeVersion = data.THREE_VERSION;
                                        scope.tool.fourthVersion = parseInt(data.FOURTH_VERSION, 10);
                                        scope.tool.version = scope.tool.threeVersion + '.' + scope.tool.fourthVersion;
                                    });
                            },
                            assembleParams: function () {
                               return {
                                    pipelineBuildId: scope.pipelineBuild.pipelineBuildId,
                                    compileBuildId: scope.pipelineBuild.compileBuildBean.id,
                                    moduleId: scope.pipelineBuild.moduleId,
                                    branchType: scope.pipelineBuild.branchType,
                                    branchName: scope.pipelineBuild.branchName,
                                    module: scope.pipelineBuild.module,
                                    version: scope.tool.version,
                                    releaseUser: scope.context.username,
                                    remarks: scope.tool.remarks
                                };
                            }
                        };

                        scope.op = {
                            open: function () {
                                return scope.uibModalInstance = $uibModal.open({
                                    templateUrl: constants.resource('directive/build-release.html'),
                                    scope: scope,
                                    size: 'md',
                                    windowClass: 'zoom'
                                });
                            },
                            ok: function () {
                                scope.uibModalInstance && scope.uibModalInstance.close();
                                return pipelineDataService.doRelease(scope.tool.assembleParams())
                                    .then(function (data) {
                                        scope.result = data;
                                        return scope.uibModalInstance = $uibModal.open({
                                            templateUrl: constants.resource('directive/release-result.html'),
                                            scope: scope,
                                            size: 'sm',
                                            windowClass: 'zoom'
                                        });
                                    });
                            },
                            cancel: function () {
                                return scope.uibModalInstance && scope.uibModalInstance.dismiss();
                            }
                        };

                        el.click(function (e) {
                            if ('false' !== scope.isCanRelease) {
                                scope.tool.getThreeVersion();
                                scope.op.open();
                            }
                            e.stopPropagation();
                        });
                    }
                }
            }
        ]
    )
});
