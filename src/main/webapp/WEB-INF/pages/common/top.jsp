<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">CIHOME</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <form class="navbar-form navbar-left">
                    <div class="form-group">
                        <input class="form-control" style="width: 350px;" type="text"
                               ng-model="ctrl.searchModule"
                               placeholder="请输入模块名"
                               uib-typeahead="module.module for module in ctrl.getSearchModules($viewValue, '<%=user.getUsername()%>')"
                               typeahead-loading="loadingLocations"
                               typeahead-no-results="noResults"
                               typeahead-on-select="ctrl.moduleSelected($model)"
                               autocomplete="off"
                               role="search"
                               uib-dropdown-toggle>
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><%=user.getUsername()%></a></li>
                    <li class="btn-group" uib-dropdown="">
                        <a href="#" role="button" class="dropdown-toggle" uib-dropdown-toggle=""
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            设置
                            <span class="caret"></span>
                        </a>
                        <ul class="uib-dropdown-menu" role="menu">
                            <li><a href="" ng-click="ctrl.exitLogin('<%=user.getUsername()%>')">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>
