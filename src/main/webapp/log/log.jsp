<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/24
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>
    $(function () {
        $('#showlog').jqGrid({
            styleUI:'Bootstrap',
            url:'${path}/log/findAll',
            datatype:'JSON',
            colNames:['编号','操作人','操作时间','操作详情','操作状态'],
            colModel:[
                {name:'id',align:'center'},
                {name:'logName',align:'center'},
                {name:'logTimes',align:'center'},
                {name:'logOption',align:'center'},
                {name:'status',align:'center'},
            ],
            pager:'#logPager',
            page:1,
            rowNum:5,
            rowList:[5,10,15,20],
            viewrecords:true,//显示数据库中总记录数
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            autowidth:true,
            editurl:'${path}/user/exit',
            height:"auto",
        }).navGrid('#logPager',
            {edit:false,add:true,del:false,search:false,addtext:'添加'},
            {},
            //添加后的额外操作
            {
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    let id = response.responseJSON.rows.id;
                    $.ajaxFileUpload({
                        url: '${path}/user/insertAliyun',
                        type: 'post',
                        data: {'id':id},
                        fileElementId: 'picImg',
                        success:function () {
                            $('#showuser').trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {}
        )
    })
</script>
<style>
    #yzm{
        margin-left: 490px;
    }
</style>
<%--设置面板--%>
<div id="content" >
    <div class="col-sm-10">
    <div class="panel panel-danger">
        <%--面板头--%>
        <div class="panel panel-heading">
            <h2>日志信息</h2>
        </div>
        <%--标签页--%>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">日志管理</a></li>
        </ul>

            <%--表单--%>
            <table id="showlog" />
        </div>
    </div>
</div>

    <%--分页工具栏--%>
    <div id="logPager" />

