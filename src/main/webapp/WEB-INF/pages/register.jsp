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
            <div class="panel panel-default" style="margin-top: 50px;position: fixed;width: 50%;">
                <div class="panel-heading element-center">
                    <h3 class="font-light">创建您的 CiHome ID</h3>
                </div>
                <div class="panel-body" ng-show="!registerCtrl.showLoading">
                    <ul>
                        <li class="text-center" style="margin-bottom: 35px;">只需一个 CiHome ID，您即可访问 CiHome 所有内容。</li>
                        <li>
                            <div class="input-group">
                                <input type="text" class="form-control" ng-model="registerCtrl.username" ng-change="registerCtrl.judgeUsernameRepeat()" placeholder="用户名">
                                <span class="input-group-btn">
                                    <button ng-show="registerCtrl.username === ''" class="btn btn-default tooltip-top tooltip-rounded" type="button"
                                            tooltip-data="该用户名应与GitHub用户名保持一致">
                                        <i class="fa fa-question-circle" style="color: #177ff7;" aria-hidden="true"></i>
                                    </button>
                                     <button ng-show="registerCtrl.username !== ''" class="btn btn-default" type="button">
                                         <i ng-show="!registerCtrl.usernameRepeat" class="fa fa-check check-right" aria-hidden="true"></i>
                                         <i ng-show="registerCtrl.usernameRepeat" class="fa fa-times check-fault" aria-hidden="true"></i>
                                    </button>
                                </span>
                            </div>
                            <div ng-show="registerCtrl.username !== '' && registerCtrl.usernameRepeat">
                                <span class="error-font-size check-fault">
                                    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                                    改用户名已存在，请更改
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
                        <li>
                            <div class="switch switch-mini">
                                <input type="checkbox" ng-true-value="true" ng-false-value="false" ng-model="registerCtrl.isSyncGithub" checked/>
                                您希望同步GitHub吗？(推荐同步,否则需要单独配置各个仓库,这需要
                                <a href="http://www.cnblogs.com/peteremperor/p/6135984.html" target="_blank">获得token</a>)
                            </div>
                        </li>
                        <li>
                            <div ng-show="registerCtrl.isSyncGithub" >
                                <div class="input-group">
                                    <input type="password" class="form-control" ng-model="registerCtrl.githubPassword" placeholder="Github token">
                                    <span class="input-group-btn">
                                    <button class="btn btn-default tooltip-top tooltip-rounded" type="button"
                                            tooltip-data="该token作为您信任本站的凭据，以期从GitHub获得相关数据">
                                        <i class="fa fa-question-circle" style="color: #177ff7;" aria-hidden="true"></i>
                                    </button>
                                </span>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="panel-body" ng-show="registerCtrl.showLoading"><br>
                    <div style="text-align: center;">
                        <span>正在为您创建账户，</span>
                        <span ng-show="registerCtrl.isSyncGithub">并同步GitHub数据，</span>
                        <span>这需要一点时间，请稍等</span>
                    </div><br><br><br>
                    <div class="agile-loading-circle">
                        <div class="ag-ld-circle1 ag-ld-circle"></div>
                        <div class="ag-ld-circle2 ag-ld-circle"></div>
                        <div class="ag-ld-circle3 ag-ld-circle"></div>
                        <div class="ag-ld-circle4 ag-ld-circle"></div>
                        <div class="ag-ld-circle5 ag-ld-circle"></div>
                        <div class="ag-ld-circle6 ag-ld-circle"></div>
                        <div class="ag-ld-circle7 ag-ld-circle"></div>
                        <div class="ag-ld-circle8 ag-ld-circle"></div>
                        <div class="ag-ld-circle9 ag-ld-circle"></div>
                        <div class="ag-ld-circle10 ag-ld-circle"></div>
                        <div class="ag-ld-circle11 ag-ld-circle"></div>
                        <div class="ag-ld-circle12 ag-ld-circle"></div>
                    </div><br><br><br>
                </div>
                <div class="panel-footer" ng-show="!registerCtrl.showLoading" style="margin-top: 35px;">
                    <div class="text-right">
                        <div class="btn-group btn-group-sm" role="group" aria-label="...">
                            <button type="button" class="btn btn-default" ng-click="registerCtrl.cancel()">取消</button>
                            <button type="button" class="btn btn-default" ng-disabled="!registerCtrl.canGoOn" ng-click="registerCtrl.register()">继续</button>
                        </div>
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
