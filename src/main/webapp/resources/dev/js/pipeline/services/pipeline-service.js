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
        self.getSearchModules = function (username, password) {
            return $http.get(constants.api('cihome/login/loginSystem?username='
                + username + '&password=' + password))
                .then(function (data) {
                    return data.data;
                });
        };
    }
})
