<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <%@include file="inc.jsp"%>
    <meta charset="utf-8">
    <title>TopFresh</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="TopFresh">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=Edge, chrome=1">

    <link rel="stylesheet" href="<%=basePath%>common/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>common/css/lib.css">
    <link rel="stylesheet" href="<%=basePath%>dev/css/overview.css">
</head>

<body ng-view>
    <div>
        <input type="text" ng-model="name">
        <span>{{name}}</span>
    </div>
    <div ng-controller="IndexController as ctrl">
        <button type="button" ng-click="ctrl.saveUser()">save</button><br>
        <span>绝对地址：{{absUrl}}</span><br>
        <span>协议名称：{{protocol}}</span><br>
        <span>请求主机：{{host}}</span><br>
        <span>请求端口：{{port}}</span>
    </div>
    <script src="<%=basePath%>common/lib/requirejs/js/require.js" data-main="<%=basePath%>dev/main.js"></script>
</body>
</html>
