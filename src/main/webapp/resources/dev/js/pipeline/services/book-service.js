/**
 * Created by Wonpang New on 2016/9/10.
 * 
 * app service
 */

define(['app', 'constants'], function (app, constants) {
    app.service (
        'bookService',
        [
            '$http',
            '$q',
            BookService
        ]
    );
    
    function BookService($http, $q) {
        var self = this;

        self.getName = function (name) {
            console.log(2222);
            return $http
                .get(constants.api('/test?username=' + name))
                .then(function (data) {
                    return data.data;
                });
        };
    }
})
