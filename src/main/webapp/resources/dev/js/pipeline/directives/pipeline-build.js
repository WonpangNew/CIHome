/**
 * Created by niuwanpeng on 17/4/27.
 *
 * pipeline构建指令
 */
define(['app', 'constants', 'angular'], function (app, constants, angular) {
    app.directive(
        'pipelineBuild',
        [
            'pipelineDataService',
            '$templateCache',
            '$compile',
            '$q',
            function (pipelineDataService, $templateCache, $compile, $q) {
                return{
                    restrict: 'E',
                    scope: {
                        currentBuild: '=',
                        context: '=appContext',
                        index: '=index'
                    },
                    templateUrl: constants.resource('directive/pipeline-build.html'),
                    replace: true,
                    link: function (scope, el) {

                        scope.compileBriefInfo = scope.currentBuild.compileBuildBean;
                        scope.releaseBriefInfo = scope.currentBuild.releaseBean;

                        scope.tool = {
                            isSuccBuildStatus: function (status) {
                                return 'SUCCESS' === status;
                            },
                            isCanRelease: function () {
                                var compileBuildStatus = scope.currentBuild.compileBuildBean.buildStatus;
                                var releaseInfo = scope.currentBuild.releaseBean || {};
                                return scope.tool.isSuccBuildStatus(compileBuildStatus)
                                    && 'SUCCESS' !== releaseInfo.releaseStatus
                                    && 'RUNNING' !== releaseInfo.releaseStatus;
                            },
                            updateJobBuild: function () {
                                var promises = [];
                                return $q.all(promises);
                            }
                        };

                        changePipelineStatus(scope.currentBuild.pipelineStatus);
                        scope.expandClass = 'normal';

                        /* 详情展开页 start*/
                        var toggleExpand = false;
                        var previousStageIndex = -1;
                        scope.currentStageExpandPointers = new Array(3);
                        for (var i = 0; i < scope.currentStageExpandPointers.length; i++) {
                            scope.currentStageExpandPointers[i] = false;
                        }

                        scope.stageTabToggle = function (stageIndex, templateUrl) {
                            if (previousStageIndex !== stageIndex) {
                                scope.currentStageExpandPointers[previousStageIndex] = false;
                                scope.currentStageExpandPointers[stageIndex] = true;
                                previousStageIndex = stageIndex;
                                toggleExpand = false;

                            } else if (previousStageIndex === stageIndex) {
                                scope.currentStageExpandPointers[previousStageIndex]
                                    = !scope.currentStageExpandPointers[previousStageIndex];
                            }
                            scope.toggleInfo(stageIndex, templateUrl);
                        };

                        scope.toggleInfo = function (stageIndex, templateUrl) {
                            if (!toggleExpand) {
                                var expandArea = el.children()[1];
                                if (angular.isDefined(expandArea)) {
                                    if (stageIndex === -3) {
                                        templateUrl = 'pipeline-release-info-tpl';
                                    } else {
                                        if (stageIndex === -1) {
                                            templateUrl = 'pipeline-commit-info-tpl';
                                        }
                                        else {
                                            templateUrl = 'pipeline-compile-info-tpl';
                                        }
                                    }
                                    var template = $templateCache.get(templateUrl);

                                    if (template == null) {
                                        throw new URIError('无法找到指定模版:' + templateUrl);
                                    }
                                    var angularExpandArea = angular.element(expandArea);

                                    scope.tool.updateJobBuild().then(function () {
                                        var compiledTemplate = $compile(template)(scope);
                                        // console.log(compiledTemplate);
                                        angularExpandArea.html(compiledTemplate);
                                        // var insertedTemplate = angular.element(compiledTemplate);

                                        // see http://stackoverflow.com/questions/9911554/jquery-get-div
                                        // -width-after-document-is-ready-and-rendered
                                        /* setTimeout(function () {
                                         angularExpandArea[0].style.height = insertedTemplate[0].clientHeight + 'px';
                                         }, 0);*/
                                    });
                                } else {
                                    throw new SyntaxError('无法找到expand-area');
                                }
                                toggleExpand = true;
                                scope.expandClass = 'expand';
                            } else {
                                var expandArea = el.children()[1];
                                angular.element(expandArea).empty();
                                scope.expandClass = 'normal';
                                toggleExpand = false;
                            }
                        };
                        /* 详情展开页 end*/

                        function changePipelineStatus(newStatus) {
                            if (newStatus === 'SUCCESS') {
                                scope.pipelineStatusClass = 'success';
                            } else if (newStatus === 'FAIL') {
                                scope.pipelineStatusClass = 'fail';
                            }
                            else if (newStatus === 'BUILDING') {
                                scope.pipelineStatusClass = 'running';
                            }
                            else {
                                scope.pipelineStatusClass = 'waiting';
                            }
                        };

                        scope.$watch(function () {
                            return scope.currentBuild.pipelineStatus;
                        }, function (newStatus) {
                            changePipelineStatus(newStatus);
                        });
                    }
                };
            }
        ]
    );
});
