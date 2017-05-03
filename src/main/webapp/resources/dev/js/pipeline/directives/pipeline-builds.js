/**
 * @file pipelines构建指令
 */
define(['app', 'constants'], function (app, constants) {
    app.directive(
        'pipelineBuilds',
        [
            function () {
                return {
                    restrict: 'E',
                    scope: {
                        builds: '=appPipelineBuilds',
                        context: '=context'
                    },
                    templateUrl: constants.resource('directive/pipeline-builds.html'),
                    replace: true
                };
            }
        ]
    );
});
