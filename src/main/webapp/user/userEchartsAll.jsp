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
    <script src="${path}/bootstrap/js/echarts.js" />
    <script src="${path}/bootstrap/js/goeasy-1.0.5.js" />
    <script src="${path}/bootstrap/js/china.js" />
    <title>Document</title>
    <script>
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            $.get("${path}/user/findAllByMonth", function (res) {
                console.log(res);
                var option = {
                    title: {
                        text: '实时更新'
                    },
                    tooltip: {},
                    legend: {
                        data: ['小男孩','小姑娘']
                    },
                    xAxis: {
                        data: res.months,
                        name:'月'
                    },
                    yAxis: {},
                    series: [{
                        name: '小男孩',
                        type: 'bar',
                        data: res.man
                    }, {
                        name: '小姑娘',
                        type: 'bar',
                        data: res.woman
                    }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }, "json");
        });

    </script>
    <script>
        /*初始化GoEasy对象*/
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-df0a45499f274b2bae29ae50a6a12dc9", //替换为您的应用appkey
        });
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            /*接收消息*/
            goEasy.subscribe({
                channel: "yingx-ljn", //替换为您自己的channel
                onMessage: function (res) {
                    let parse = JSON.parse(res.content);
                    console.log(parse);
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '实时更新'
                        },
                        tooltip: {},
                        legend: {
                            data: ['小男孩', '小姑娘']
                        },
                        xAxis: {
                            data: parse.months,
                            name: '月'
                        },
                        yAxis: {},
                        series: [{
                            name: '小男孩',
                            type: 'bar',
                            data: parse.man
                        }, {
                            name: '小姑娘',
                            type: 'bar',
                            data: parse.woman
                        }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        });
    </script>
<body>
<%--设置面板--%>
<div id="content" >
    <div class="col-sm-10">
        <div class="panel panel-info">
            <%--面板头--%>
            <div class="panel panel-heading">
                <h2>用户统计</h2>
            </div>
            <div id="main" style="width: 1000px;height:500px;"></div>
        </div>
    </div>
</div>
</body>
</html>