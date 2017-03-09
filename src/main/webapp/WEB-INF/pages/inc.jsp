<%--
  Created by IntelliJ IDEA.
  User: Wonpang New
  Date: 2016/9/10
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    if (!path.endsWith("/")) {
        path += "/";
    }
    String basePath = path + "resources/";
%>
