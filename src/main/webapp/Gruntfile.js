/**
 * Created by Wonpang New on 2016/9/10.
 */

'use strict';

module.exports = function (grunt) {

    // 配置app路径
    var config = {
        rootPath: require('./bower.json').rootPath || './',
        devPath: './resources',
    };

    grunt.initConfig({
        ciHome: config,
        pkg: grunt.file.readJSON('package.json'),

        // bower任务配置
        bower: {
            install: {
                options: {
                    targetDir: '<%= ciHome.devPath %>/common/lib',
                    layout: 'byComponent',
                    install: true,
                    verbose: false,
                    cleanTargetDir: false,
                    copy: true,
                    cleanBowerDir: true
                }
            }
        }
    });

    grunt.loadNpmTasks("grunt-bower-task");

    grunt.registerTask('default', [
        "bower:install"
    ]);
}
