<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/19
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>
    function modfiyStatus(id,status){
        $.ajax({
          url: '${path}/user/status',
          data: {'id':id,'status':status},
          type: 'post',
          datatype: 'json',
            success:function (data) {
                if (data.status=='200'){
                    $('#showuser').jqGrid('clearGridData');
                    $('#showuser').trigger('reloadGrid');
                }else alert(data.yes);
            }
        })
    }
    $('#easyPOI').click(function () {
        $.ajax({
            url: '${path}/user/easyPOI',
            type: 'get',
            success:function (data) {
                alert(data.message);
                $('#showuser').trigger("reloadGrid");
            }
        })
    });

    $(function () {
        $('#showuser').jqGrid({
            styleUI:'Bootstrap',
            url:'${path}/user/findAll',
            datatype:'JSON',
            colNames:['编号','姓名','性别','地址','电话','头像','简介','学分','状态','创建时间'],
            colModel:[
                {name:'id',align:'center'},
                {name:'nickname',align:'center',editable:true},
                {name:'sex',align:'center',editable:true},
                {name:'address',align:'center',editable:true},
                {name:'phone',align:'center',editable:true},
                {name:'picImg',align:'center',editable:true,edittype:'file',
                    formatter:function (value,options,row) {
                        return '<img src="'+value+'" style="width: 60px;height: 60px" class="img-circle" >'
                    }
                },
                {name:'brief',align:'center',editable:true},
                {name:'score',align:'center',editable:true},
                {name:'status',align:'center',editable:true,
                    formatter:function (value,options,row) {
                        if(value=='正常') return "<button class='btn btn-success' onclick='modfiyStatus(\""+row.id+"\",\""+value+"\")'>"+value+"</button>"
                        else return "<button class='btn btn-danger' onclick='modfiyStatus(\""+row.id+"\",\""+value+"\")'>"+value+"</button>"
                    }
                },
                {name:'userDate',align:'center'}
            ],
            pager:'#userPager',
            page:1,
            rowNum:5,
            rowList:[5,10,15,20],
            viewrecords:true,//显示数据库中总记录数
            multiselect:true,//开启多行选择
            rownumbers:true,//开启表格行号
            autowidth:true,
            editurl:'${path}/user/exit',
            height:"auto",
        }).navGrid('#userPager',
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
    });


    $('#code').click(function () {
        let phonn=$('#yzm').val();
        $.ajax({
           url: '${path}/user/code',
           data: {'phonn':phonn},
           datatype: 'json',
           type:'post',
           success:function () {
                alert("发送成功");
               $('#showuser').trigger("reloadGrid");
           }
        });
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
    <div class="panel panel-info">
        <%--面板头--%>
        <div class="panel panel-heading">
            <h2>用户信息</h2>
        </div>
        <%--标签页--%>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户管理</a></li>
        </ul>

        <div>
            <form class="form-inline">
                <button class="btn btn-info" id="easyPOI">导出用户信息</button>
                <button class="btn btn-success">导入用户信息</button>
                <div class="form-group">
                    <input type="text" name="phonn" class="form-control" id="yzm" placeholder="请输入手机号">
                </div>
                <button type="button" class="btn btn-success" id="code">发送验证码</button>
            </form>
        </div>
            <%--表单--%>
            <table id="showuser" />
        </div>
    </div>
</div>

    <%--分页工具栏--%>
    <div id="userPager" />

