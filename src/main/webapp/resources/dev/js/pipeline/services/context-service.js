/**
 * Created by niuwanpeng on 17/3/17.
 */
/**
 * @file ContextService
 */

define(['app', 'angular', 'constants'], function appPlContextService(app, angular, constants) {
    app.service('pipelineContextService',
        [
            '$q', '$http', '$state', '$window', '$timeout', ContextService
        ]
    );

    function ContextService($q, $http, $state, $window, $timeout) {
        var self = this;
        self.context = {
            module: '',
            svnType: '',
            branch: '',
            isGitModule: false,
            pipelineType: 'MODULE',
            workFlow: ''
        };
    }


});

