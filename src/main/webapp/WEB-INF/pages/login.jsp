<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
    <head>
        <%@include file="common/inc.jsp"%>
        <meta charset="utf-8">
        <title>CIHome</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="CIHome">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta http-equiv="X-UA-COMPATIBLE" content="IE=Edge, chrome=1">

        <link rel="stylesheet" href="<%=basePath%>common/css/common.css">
        <link rel="stylesheet" href="<%=basePath%>common/css/lib.css">
        <link rel="stylesheet" href="<%=basePath%>dev/css/overview.css">
    </head>

    <body class="landing-body" ng-controller="PermissionController as permissionCtrl">
        <div class="background-index">
            <canvas id="particle-canvas" class="particle-canvas"></canvas>
            <div class="background-index__overlay"></div>
            <div class="background-index__content">
                <div class="cihome-logo-index" role="img" aria-label="CIHome Logo"></div>
                <p class="hero-tagline">一站式持续集成与交付控制台</p>

                <!-- 登录 start-->
                <div class="login glass-panel" role="form" aria-label="登录 CIHome">
                    <div class="form-group">
                        <input type="text" class="form-control" ng-model="permissionCtrl.username" placeholder="username">
                    </div>
                    <div class="input-group">
                        <input type="password" class="form-control" ng-model="permissionCtrl.password" placeholder="password">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" ng-disabled="!permissionCtrl.allowClickLogin"
                             ng-click="permissionCtrl.loginSystem()" aria-label="登录 CIHome">
                                <i class="fa fa-sign-in" aria-hidden="true"></i>
                            </button>
                        </span>
                    </div>
                </div>
                <div class="element-center notice-error" ng-if="permissionCtrl.loginStatus === 'FAIL'">
                    {{permissionCtrl.loginMessage}}
                </div>
                <!-- 登录 end-->

                <div class="cut-line" aria-hidden="true">
                    <div class="cut-line-right"></div>
                    <div class="cut-line-left"></div>
                </div>
                <div class="element-center support-links">
                    <a href="#">忘记了密码？</a><br><br>
                    <a href="cihome/login/register">没有账户？立即注册</a>
                </div>

                <footer class="index-footer">
                    Copyright © 2017 - <script>document.write(new Date().getFullYear());</script>
                    CIHome Inc. 保留所有权利
                </footer>
            </div>
        </div>

        <script src="<%=basePath%>dev/js/effects/particle-background.js"></script>
        <script src="<%=basePath%>common/lib/requirejs/js/require.js" data-main="<%=basePath%>dev/main.js"></script>
    </body>
</html>
