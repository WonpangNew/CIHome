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

<body ng-controller="RegisterController as registerCtrl">
<div>
    <div class="background-index">
        <div class="register-panel">
            <div class="panel panel-default" style="margin-top: 100px;position: fixed;width: 50%;">
                <div class="panel-heading element-center">
                    <h3 class="font-light">创建您的 CiHome ID</h3>
                </div>
                <div class="panel-body">
                    <ul>
                        <li class="text-center" style="margin-bottom: 35px;">只需一个 CiHome ID，您即可访问 CiHome 所有内容。</li>
                        <li>
                            <div class="input-group">
                                <input type="text" class="form-control" ng-model="registerCtrl.username" placeholder="用户名">
                                <span class="input-group-btn">
                                    <button class="btn btn-default tooltip-top tooltip-rounded" type="button"
                                            tooltip-data="该用户名应与GitHub用户名保持一致">
                                        <i class="fa fa-question-circle" style="color: #177ff7;" aria-hidden="true"></i>
                                    </button>
                                </span>
                            </div>
                        </li>
                        <li>
                            <div class="form-group">
                                <input type="password" class="form-control" ng-model="registerCtrl.password" placeholder="密码">
                            </div>
                        </li>
                        <li>
                            <div class="input-group">
                                <input type="password" class="form-control" ng-model="registerCtrl.checkPassword" placeholder="确认密码">
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button">
                                         <span ng-hide="registerCtrl.checkPassword === ''">
                                            <i ng-show="registerCtrl.checkPasswordPass" class="fa fa-check check-right" aria-hidden="true"></i>
                                            <i ng-show="!registerCtrl.checkPasswordPass" class="fa fa-times check-fault" aria-hidden="true"></i>
                                        </span>
                                        <span ng-hide="registerCtrl.checkPassword !== ''">
                                            <i class="fa fa-spinner" aria-hidden="true"></i>
                                        </span>
                                    </button>
                                </span>
                            </div>
                        </li>
                        <li>
                            <div class="input-group">
                                <input type="email"
                                       class="form-control"
                                       ng-model="registerCtrl.email"
                                       placeholder="邮箱">
                                <span class="input-group-btn">
                                    <button ng-show="registerCtrl.email === ''" class="btn btn-default tooltip-top tooltip-rounded" type="button"
                                            tooltip-data="用于找回密码">
                                       <i class="fa fa-question-circle" style="color: #177ff7;" aria-hidden="true"></i>
                                    </button>
                                     <button ng-show="registerCtrl.email !== ''" class="btn btn-default" type="button">
                                         <i ng-show="registerCtrl.checkEmailPass" class="fa fa-check check-right" aria-hidden="true"></i>
                                         <i ng-show="!registerCtrl.checkEmailPass" class="fa fa-times check-fault" aria-hidden="true"></i>
                                    </button>
                                </span>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="panel-footer" style="margin-top: 35px;">
                    <div class="text-right">
                        <a href="loginTo">取消</a>
                        <a class="separator">|</a>
                        <a href="#">继续</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="index-footer">
            <div class="float-right">Copyright © 2017 CIHome Inc. 保留所有权利</div>
        </div>
    </div>
</div>

<script src="<%=basePath%>common/lib/requirejs/js/require.js" data-main="<%=basePath%>dev/main.js"></script>
</body>
</html>
