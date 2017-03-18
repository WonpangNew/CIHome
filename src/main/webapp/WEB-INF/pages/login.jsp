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

<body ng-controller="PermissionController as permissionCtrl">
<div>
    <div class="background-index">
        <div style="height: 20%"></div>
        <div class="cihome-logo-index"></div>
        <!-- 登录 start-->
        <div class="login">
            <div class="form-group">
                <input type="text" class="form-control" ng-model="username" placeholder="Email or username">
            </div>
            <div class="input-group">
                <input type="password" class="form-control" ng-model="password" placeholder="password">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" ng-click="permissionCtrl.loginSystem()">
                        <i class="fa fa-sign-in" aria-hidden="true"></i>
                    </button>
                </span>
            </div>
        </div>
        <div ng-if="permissionCtrl.loginStatus === 'FAIL'" >
            {{permissionCtrl.loginMessage}}
        </div>
        <!-- 登录 end-->
        <div class="cut-line">
            <div class="cut-line-right"></div>
            <div class="cut-line-left"></div>
        </div>
        <div class="element-center">
            <a href="#">忘记了密码？</a><br><br>
            <a href="#">有账户吗？创建一个。</a>
        </div>

        <div class="index-footer">
            <div class="float-right">Copyright © 2017 CIHome Inc. 保留所有权利</div>
        </div>
    </div>
</div>

<script src="<%=basePath%>common/lib/requirejs/js/require.js" data-main="<%=basePath%>dev/main.js"></script>
</body>
</html>
