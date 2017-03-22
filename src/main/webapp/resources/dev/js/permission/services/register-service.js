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

        self.register = function (username, password) {
            return $http.get(constants.api(''))
                .then(function (data) {
                    return data.data;
                });
        };
    }
})
