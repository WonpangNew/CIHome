/**
 * Created by Wonpang New on 2016/9/10.
 *
 * app service
 */

define(['app', 'constants'], function (app, constants) {
    app.service (
        'pipelineDataService',
        [
            '$http',
            '$q',
            PipelineService
        ]
    );

    function PipelineService($http, $q) {
        var self = this;

        // 搜索模块名
        self.getSearchModules = function (searchVal, username) {
            return $http.get(constants.api('ajax/module/query?limit=70&q=' + searchVal + '&username=' + username))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得主干流水线
         * @param username
         * @param module
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getTrunkPipelines = function (username, module, pipelineBuildId) {
            return $http.get(constants.api('pipeline/v1/pipelineBuilds?username=' + username + '&module=' + module
                + '&pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得编译详细信息
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getCompileBuildDetail = function (pipelineBuildId) {
            return $http.get(constants.api('compileBuild/v1/detail?pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得发布详细信息
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getReleaseDetail = function (pipelineBuildId) {
            return $http.get(constants.api('release/v1/detail?pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        self.doRebuild = function (username, module, compileBuildId) {
            return $http.get(constants.api('compileBuild/v1/rebuild?compileBuildId=' + compileBuildId
                + '&username=' + username + '&module=' + module))
                .then(function (data) {
                    return data.data;
                });
        };
    }
});
