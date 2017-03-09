/**
 * Created by Wonpang New on 2016/9/10.
 * 用户浏览商品指令
 */

define(['app', 'constants', 'angular'], function (app, constants, angular) {
    app.directive (
        'browesStore',
        [
            'bookService',
            '$uibModal',
            function (bookService, $uibModal) {
                return {
                    restrict: 'E',
                    scope: {
                        bookName: '='
                    },
                    templateUrl: constants.resource('directive-tpl/test.html'),
                    replace: true,
                    link: function (scope, el) {

                        scope.getBookName = function (name) {
                            return bookService.getName(name);
                        };

                        scope.open = function () {
                            return scope.getBookName(scope.bookName);
                        }

                        el.click(function () {
                            console.log(1111);
                            scope.open();
                        });
                    }
                }
            }
        ]
    );
})
