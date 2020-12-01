<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/23
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>
    $(function () {
        $('#showvideo').jqGrid({
            styleUI:'Bootstrap',
            url:'${path}/video/selectAll',
            datatype:'JSON',
            colNames:['编号','标题','简介','封面','视频','上传时间','类别编号','用户编号','分组编号'],
            colModel:[
                {name:'id',align:'center'},
                {name:'title',align:'center',editable:true},
                {name:'brief',align:'center',editable:true},
                {name:'coverPath',align:'center',
                    formatter:function (value,options,row) {
                        return '<img src="'+value+'" style="width: 100px;height: 100px" class="img-circle" />'
                    }
                },
                {name:'videoPath',align:'center',editable:true,edittype:'file',
                    formatter:function (value,options,row) {
                        return '<video src="'+value+'" controls="controls" style="width: 100%; height: 90px; poster:"'+row.coverPath+'" >'
                    }
                },
                {name:'uploadTime',align:'center'},
                {name:'categoryId',align:'center',editable:true,edittype:'select',editoptions:{dataUrl:'${path}/category/findAll'}},
                {name:'userId',align:'center',editable:true},
                {name:'groupId',align:'center',editable:true}
            ],
            pager:'#videoPager',
            page:1,
            rowNum:5,
            rowList:[5,10,15,20],
            viewrecords:true,//显示数据库中总记录数
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            autowidth:true,
            editurl:'${path}/video/edit',
            height:"auto",
        }).navGrid('#videoPager',
            {edit:true,add:true,del:true,search:false,addtext:'添加',edittext:'修改',deltext:'删除'},
            {
                closeAfterEdit:true,
                reloadAfterSubmit: true,
                afterSubmit:function (response) {
                    console.log(response);
                    let id = response.responseJSON.rows.id;
                    $.ajaxFileUpload({
                        url: '${path}/video/modfiyAliyun',
                        type: 'post',
                        data: {'id':id},
                        fileElementId: 'videoPath',
                        success:function () {
                            $('#showvideo').trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            //添加后的额外操作
            {
                closeAfterAdd:true,
                reloadAfterSubmit: true,
                afterSubmit:function (response) {
                    console.log(response);
                    let id = response.responseJSON.rows.id;
                    $.ajaxFileUpload({
                        url: '${path}/video/insertAliyun',
                        type: 'post',
                        data: {'id':id},
                        fileElementId: 'videoPath',
                        success:function () {
                            $('#showvideo').trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {
                closeAfterDelete: true,//关闭面板
                reloadAfterSubmit: true,
            }
        )
    });
</script>
<style>
</style>
<%--设置面板--%>
<div id="content" >
    <div class="col-sm-10">
    <div class="panel panel-warning">
        <%--面板头--%>
        <div class="panel panel-heading">
            <h2>视频信息</h2>
        </div>
        <%--标签页--%>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">视频管理</a></li>
        </ul>

            <%--表单--%>
            <table id="showvideo" />
        </div>
    </div>
</div>

    <%--分页工具栏--%>
    <div id="videoPager" />

