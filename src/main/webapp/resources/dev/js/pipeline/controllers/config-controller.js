/**
 * Created by niuwanpeng on 17/5/5.
 *
 * 配置 Controller
 */

define(['app', 'constants'], function (app, constants) {
    'use strict';
    app.controller('ConfigController', [
        'pipelineDataService',
        'pipelineContextService',
        '$scope',
        '$state',
        '$timeout',
        '$uibModal',
        ConfigController
    ]);

    function ConfigController(pipelineDataService, pipelineContextService, $scope, $state, $timeout, $uibModal) {
        var self = this;
        self.currentModule = pipelineContextService.context.module;
        self.isSyncGithub = false;
        self.configModule = '';
        self.username = pipelineContextService.context.username;
        self.context = pipelineContextService;
        self.showLoading = false;

        self.addModule = function () {
            self.showLoading = true;
            pipelineDataService.addModule(self.username, self.configModule)
                .then(function (data) {
                    $scope.result = data;
                    return $scope.uibModalInstance = $uibModal.open({
                        templateUrl: constants.resource('config/config.result.html'),
                        scope: $scope,
                        size: 'sm',
                        windowClass: 'zoom'
                    });
                });
        };

        self.cancel = function () {
            $state.go(
                'builds.trunk',
                {
                    module: self.currentModule
                }
            );
        };

        $scope.cancelWindow = function () {
            self.showLoading = false;
            return $scope.uibModalInstance && $scope.uibModalInstance.dismiss();
        };

        $scope.goNewModule = function (newModule) {
            $state.go(
                'builds.trunk',
                {
                    module:newModule
                }
            );
        }

        $scope.$watch(function () {
            self.canGoOn = self.isSyncGithub && self.configModule !== '';
        });

    }
});

