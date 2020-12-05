<%@page pageEncoding="UTF-8" isELIgnored="false" isErrorPage="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script>
        $(function () {
            $('#exit').click(function () {
                $.ajax({
                    url: '${path}/admin/exit',
                    type: 'post',
                    dataType: 'JSON',
                    success:function (data) {
                        location.href='${path}/login/login.jsp';
                        alert(data.status);
                    }
                });
            });
        });
    </script>
</head>
<body>
<!--顶部导航-->
<div class="navbar navbar-default" id="navbar-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="" class="navbar-left">
                <img src="${path}/bootstrap/img/yx-icon.png" alt="" width="48px" height="48px">
            </a>
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-1">
                <span class="caret"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse" id="menu-1">
            <ul class="nav navbar-nav">
                <li><a href="javascript:void(0)">应学视频APP后台管理系统</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">
                        欢迎：<span class="text text-primary">${sessionScope.admin.username}</span>
                        &nbsp;<img src="${path}/bootstrap/img/pkq06.jpg" class="img-circle" alt="" width="24px" height="24px">
                    </a>
                </li>
                <li class="dropdown">
                    <a href="" id="exit">退出 <span class="glyphicon glyphicon-log-out"></span></a>
                </li>
            </ul>
        </div>
    </div>
</div>
<br>
<div class="container-fluid yx-nav-menu">
    <div class="row ">
        <!--导航菜单-->
        <div class="col-sm-2">
            <div class="panel panel-default panel-info ">
                <div class="panel-heading">
                    <a href="${path}/main/main.jsp"> <span class="glyphicon glyphicon-menu-up"></span> 首页</a>
                </div>
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default panel-info">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    <span class="glyphicon glyphicon-user"></span> 用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body text-center" style="padding-bottom: 0px">
                                <ul class="list-group">
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a class="list-group-item" href="javascript:;" onclick="javascript:$('#all').load('${path}/user/user.jsp');">
                                            <button class="btn btn-info btn-group-justified">
                                                用户展示
                                            </button>
                                        </a>
                                    </li>
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a href="javascript:;" onclick="javascript:$('#all').load('${path}/user/chinaAll.jsp');" class="list-group-item">
                                            <button class="btn btn-info btn-group-justified">
                                                用户分布
                                            </button>
                                        </a>
                                    </li>
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a href="javascript:;" onclick="javascript:$('#all').load('${path}/user/userEchartsAll.jsp');" class="list-group-item">
                                            <button class="btn btn-info btn-group-justified">
                                                用户统计
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="panel panel-default panel-success">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title text-center">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    <span class="glyphicon glyphicon-th-list"></span> 分类展示
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body text-center" style="padding-bottom: 0px">
                                <ul class="list-group">
                                    <li class="list-group-item"  style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a class="list-group-item" href="javascript:;" onclick="javascript:$('#all').load('${path}/category/category.jsp');">
                                            <button class="btn btn-success btn-group-justified">
                                                查看分类列表
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="panel panel-default panel-warning">
                        <div class="panel-heading text-center" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    <span class="glyphicon glyphicon-film"></span> 视频管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body text-center" style="padding-bottom: 0px">
                                <ul class="list-group">
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a href="javascript:;" onclick="javascript:$('#all').load('${path}/video/video.jsp');" class="list-group-item" id="a2">
                                            <button class="btn btn-warning btn-group-justified">
                                                查看视频
                                            </button>
                                        </a>
                                    </li>
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a href="javascript:;" onclick="javascript:$('#all').load('${path}/video/searchVideo.jsp');" class="list-group-item" id="a2">
                                            <button class="btn btn-warning btn-group-justified">
                                                检索视频
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="panel panel-default panel-primary">
                        <div class="panel-heading text-center" role="tab" id="headingFour">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                    <span class="glyphicon glyphicon-tag"></span> 反馈管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                            <div class="panel-body text-center" style="padding-bottom: 0px">
                                <ul class="list-group">
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a href="#" class="list-group-item" id="a3">
                                            <button class="btn btn-primary btn-group-justified">
                                                查看反馈列表
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="panel panel-default panel-danger">
                        <div class="panel-heading" role="tab" id="headingFire">
                            <h4 class="panel-title text-center">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFire" aria-expanded="false" aria-controls="collapseFire">
                                    <span class="glyphicon glyphicon-calendar"></span> 日志管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFire" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFire">
                            <div class="panel-body text-center" style="padding-bottom: 0px">
                                <ul class="list-group">
                                    <li class="list-group-item" style="margin-bottom: 5px;padding: 0px;border: 2px solid white">
                                        <a href="javascript:;" onclick="javascript:$('#all').load('${path}/log/log.jsp');" class="list-group-item" id="a4">
                                            <button class="btn btn-danger btn-group-justified">
                                                查看日志列表
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--右边内容-->
        <div id="all">
            <div id="content" >
            <div class="col-sm-10">
                <%--巨幕--%>
                <div class="col-xs-12 col-md-12" id="centerLayout">
                    <div class="jumbotron" style="padding: 10px">
                        <h2 class="text-center">欢迎来到应学APP后台管理系统</h2>
                    </div>
                </div>
                <!--轮播图-->
                <div class="col-sm-12">
                    <div id="carousel-example-generic" data-interval="3000" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        </ol>
                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active text-center">
                                <img src="${path}/bootstrap/img/1.jpg" style="width: 100%; height: 350px;">
                                <div class="carousel-caption">
                                </div>
                            </div>
                            <div class="item">
                                <img src="${path}/bootstrap/img/2.jpg" style="width: 100%; height: 350px;">
                                <div class="carousel-caption">
                                </div>
                            </div>
                            <div class="item">
                                <img src="${path}/bootstrap/img/3.jpg" style="width: 100%; height: 350px;">
                                <div class="carousel-caption">
                                </div>
                            </div>
                        </div>
                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </div>
</div>
<!--页脚-->
<nav class="navbar navbar-default navbar-fixed-bottom" style="bottom: -24px">
    <div class="container" >
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse"  id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav" style="margin-left: 40%">
                <li style="margin-top: 3px"><p class="text-center" style="margin-right: 0px">@楠少:www.2500494776@qq.com</p></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


    <!--栅格系统-->
    <!--左边手风琴部分-->
    <!--巨幕开始-->
    <!--右边轮播图部分-->
    <!--页脚-->
    <!--栅格系统-->

</body>
</html>
