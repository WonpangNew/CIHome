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
    }
})
