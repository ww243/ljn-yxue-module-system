<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="${path}/bootstrap/js/echarts.js"></script>
    <script src="${path}/bootstrap/js/china.js"></script>
    <title>Document</title>
    <script>
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            $.get("${path}/user/findAllBySes", function (datas) {
                console.log(datas);
                var series = [];

                for (var i = 0; i < datas.length; i++) {

                    var data = datas[i];

                    series.push(
                        {
                            name: data.sex,
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.cityPos
                        }
                    )
                }

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '注册人数全国统计表',
                        subtext: '实时更新',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['小男孩', '小女孩']
                    },
                    visualMap: {
                        min: 0,
                        max: 200,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: series
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }, "json");

        });
    </script>
</head>
<body>
<%--设置面板--%>
<div id="content" >
    <div class="col-sm-10">
        <div class="panel panel-info">
            <%--面板头--%>
            <div class="panel panel-heading">
                <h2>用户分布</h2>
            </div>
        <div id="main" style="width: 1000px;height:500px;"></div>
        </div>
    </div>
</div>
</body>
</html>