/**
 * Created by niuwanpeng on 17/5/4.
 *
 * 重新编译指令
 */

define(['app', 'constants'], function (app, constants) {
    app.directive(
        'appReCompile',
        [
            'pipelineDataService',
            '$uibModal',
            'modalService',
            '$timeout',
            function (pipelineDataService, $uibModal, modalService, $timeout) {
                return {
                    restrict: 'A',
                    scope: {
                        compileBuildBrief: '=compileBuildBrief',
                        context: '=appContext'
                    },
                    link: function (scope, el) {

                        scope.tool = {
                            reCompile: function () {
                                var compileBuildId = scope.compileBuildBrief.id;
                                pipelineDataService.doRebuild(scope.context.username, scope.context.module, compileBuildId)
                                    .then(function (data) {
                                        if ('OK' === data.RESULT) {
                                            el.after('<button type="button" class="btn btn-default btn-xs button-bubble" ' +
                                                'style=" background-color: white !important;">已重新发起编译!</button>');
                                        } else {
                                            el.after('<button type="button" class="btn btn-default btn-xs button-bubble" ' +
                                                'style=" background-color: white !important;">重新发起编译失败，请重试!</button>');
                                        }
                                        $timeout(function () {
                                            el.next().remove();
                                        }, 2000);
                                        el.removeAttr('disabled');
                                        el.find('.fa').removeClass('fa-spin');
                                    });
                            }
                        };

                        el.click(function () {
                            el.attr('disabled', 'disabled');
                            el.find('.fa').addClass('fa-spin');
                            scope.tool.reCompile();
                        });

                    }
                };
            }
        ]
    );
});

