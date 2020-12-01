<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/19
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#showcategory").jqGrid({
            styleUI: 'Bootstrap',
            url : "${path}/category/selectAll1",
            datatype : "json",
            colNames : [ '编号', '一级类别Name', '所属级别'],
            colModel : [
                {name : 'id',align:'center'},
                {name : 'cateName',align:'center',editable:true},
                {name : 'levels',align:'center'},
            ],
            rowNum : 4,
            rowList : [ 4, 8, 12,16 ],
            pager : '#categoryPager',
            sortname : 'id',
            viewrecords : true,
            autowidth:true,
            height:"auto",
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            subGrid : true,  //开启子表格
            // subgrid_id:是在创建表数据时创建的div标签的ID
            //row_id是该行的ID
            subGridRowExpanded : function(subgrid_id, row_id) {
                addSubGrid(subgrid_id, row_id);
            },
            editurl:'${path}/category/exit',
        }).navGrid('#categoryPager', {add : true,addtext:'添加',edit : true,edittext:'修改',del : true,deltext:'删除'},
            {
                closeAfterEdit: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseJSON.message);
                    return "true";
                }
            },  //修改
            {
                closeAfterAdd: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseJSON.message);
                    return "true";
                }
            }, //添加
            {
                closeAfterDelete: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseJSON.message);
                    return "true";
                }
            }, //删除
        );
    }

    //开启子表格的样式
    function addSubGrid(subgridId, rowId){

        var subgridTableTd= subgridId + "Table";
        var pagerId= subgridId+"Page";


        $("#"+subgridId).html("" +
            "<table id='"+subgridTableTd+"' />" +
            "<div id='"+pagerId+"' />"
        );
        $("#" + subgridTableTd).jqGrid({
            styleUI:"Bootstrap",
            url : "${path}/category/selectAll2?id=" + rowId,
            datatype : "json",
            colNames : [ '编号', '二级类别Name', '所属级别','所属类别'],
            colModel : [
                {name : "id",align:'center'},
                {name : "cateName",align:'center',editable:true},
                {name : "levels",align:'center'},
                {name : "parentId",align:'center'},
            ],
            rowNum : 20,
            pager : "#"+pagerId,
            sortname : 'num',
            sortorder : "asc",
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            autowidth:true,
            height:"auto",
            editurl:'${path}/category/exit?parentId='+rowId,
        });
        $("#" + subgridTableTd).jqGrid('navGrid',"#" + pagerId,
            {add : true,addtext:'添加',edit : true,edittext:'修改',del : true,deltext:'删除'},
            {
                closeAfterEdit: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseJSON.message);
                    return "true";
                }
            },  //修改
            {
                closeAfterAdd: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseJSON.message);
                    return "true";
                }
            }, //添加
            {
                closeAfterDelete: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseJSON.message);
                    return "true";
                }
            }, //删除
        );
    }
</script>
<%--设置面板--%>
<div id="content" >
    <div class="col-sm-10">
        <div class="panel panel-success">
            <%--面板头--%>
            <div class="panel panel-heading">
                <h2>类别信息</h2>
            </div>
            <%--标签页--%>
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别管理</a></li>
            </ul>
            <%--表单--%>
            <table id="showcategory" />
        </div>
    </div>
</div>

<%--分页工具栏--%>
<div id="categoryPager" />
