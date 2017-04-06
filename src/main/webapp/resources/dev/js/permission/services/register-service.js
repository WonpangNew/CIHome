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

        /**
         * 判断用户名是否重复
         * @param username
         * @returns {*}
         */
        self.judgeUsernameRepeat = function (username) {
            return $http.get(constants.api('/user/judgeUsernameRepeat?username=' + username))
                .then(function (data) {
                    return data.data;
                });
        };
    }
})
