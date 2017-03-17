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

    <body ng-controller="IndexController as ctrl">
        <%@include file="common/top.jsp" %>
        <div ui-view></div>
        <%@include file="common/footer.jsp" %>
        <script src="<%=basePath%>common/lib/requirejs/js/require.js" data-main="<%=basePath%>dev/main.js"></script>
    </body>
</html>
