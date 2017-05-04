/**
 * Created by niuwanpeng on 17/5/4.
 * 模态框
 */

define(['app', 'constants', 'angular'], function (app, constants, angular) {
    app.service(
        'modalService',
        [
            '$uibModal',
            '$rootScope',
            ModalService
        ]
    );

    function ModalService($uibModal, $rootScope) {

        var self = this;

        /**
         * open alert modal
         * @param {string} title The title
         * @param {string} content The content that could be html or url which ends with the suffix '.html'
         * @param {Object} model The model members for scope
         * @param {string} size The default modal size is 'sm'
         */
        self.alert = function (title, content, model, size) {
            var scope = angular.extend($rootScope.$new(), {title: title || ''});
            if (content && content.endsWith('.html')) {
                scope.contentUrl = content;
            } else {
                scope.content = content;
            }
            model instanceof Object && angular.extend(scope, model);
            $uibModal.open({
                scope: scope,
                templateUrl: constants.resource('pipeline/modal-alert.html'),
                size: size || 'sm',
                windowClass: 'zoom'
            });
        };
    }
});

