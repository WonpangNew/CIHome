/**
 * Created by Wonpang New on 2016/9/10.
 *
 * 注册service
 */

define(['app', 'constants'], function (app, constants) {
    app.service (
        'registerService',
        [
            '$http',
            '$q',
            RegisterService
        ]
    );

    function RegisterService($http, $q) {
        var self = this;

        self.register = function (userbean) {
            return $http.post(constants.api('/github/initUser'), userbean)
                .then(function (data) {
                    return data.data;
                });
        };
    }
})
