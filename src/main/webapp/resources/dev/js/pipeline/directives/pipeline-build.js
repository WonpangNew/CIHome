/**
 * Created by niuwanpeng on 17/4/27.
 *
 * pipeline构建指令
 */
define(['app', 'constants'], function (app, constants) {
    app.directive(
        'pipelineBuild',
        [
            'pipelineDataService',
            function (pipelineDataService) {
                return{
                    restrict: 'E',
                    scope: {
                        currentBuild: '=',
                        context: '=appContext',
                        index: '=index'
                    },
                    templateUrl: constants.resource('directive/pipeline-build.html'),
                    replace: true,
                    link: function (scope) {
                        scope.getCompileBuildDetail = function () {
                            pipelineDataService.getCompileBuildDetail(scope.currentBuild.pipelineBuildId)
                                .then(function (data) {
                                    scope.compileBuildDetail = data;
                                });
                        };

                        scope.getReleaseDetail = function () {
                            pipelineDataService.getReleaseDetail(scope.currentBuild.pipelineBuildId)
                                .then(function (data) {
                                    scope.releaseDetail = data;
                                });
                        };

                        scope.doRebuild = function () {
                            var compileBuildId = scope.compileBuildDetail.id;
                            pipelineDataService.doRebuild(scope.context.username, scope.context.module, compileBuildId)
                                .then(function (data) {
                                    var returnResult = data;
                                    if (data === 'OK') {
                                        var tips = "开始编译";
                                    } else {
                                        var tips = "请求失败"
                                    }
                                });
                        };
                    }
                };
            }
        ]
    );
});
