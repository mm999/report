/**
 * Created by tobepro on 2015/6/26 0026.
 */

var custom = "";
$(function() {
    require.config({
        paths: {
            echarts : 'js/echarts'
        }
    });

    var date = new Date();
    var postData = {
        "type" : 0,
        "startTimestamp" : getLastSevenDay(date),
        "endTimestamp" : getTimeStamp(date),
        "userId" : sessionStorage.getItem("userId")
    };

    post(postData, 0);

    $('#beginTime').date();
    $('#endTime').date();

    $("#ec_seven").on("click", ec_seven);
    $("#ec_all").on("click", ec_all);
    $("#ec_custom").on("click", ec_custom);
    $("#btn_show_date").on("click", show_date);

    $("#ec_seven").addClass("active");
});

//获取企业code
function getParameterByName(name){
    var values = decodeURIComponent((location.search.match(RegExp("[?|&]" + name + '=([^\&]+)'))||[,null])[1]);
    return  values == "" || values == "null" ? "" : values;
};

function clearButtonActive() {
    $("#ec_seven").removeClass("active");
    $("#ec_all").removeClass("active");
    $("#btn_show_date").removeClass("active");
}

/**
 * 显示datepicker
 */
function show_date() {
    clearButtonActive();
    $("#btn_show_date").addClass("active");
    $("#date_picker").removeClass("hide");
}

/**
 * 获取7天数据
 */
function ec_seven() {
    clearButtonActive();
    $("#ec_seven").addClass("active");
    $("#date_picker").addClass("hide");
    var date = new Date();
    var postData = {
        "type" : 2,
        "startTimestamp" : getLastSevenDay(date),
        "endTimestamp" : getTimeStamp(date),
        "userId" : sessionStorage.getItem("userId")
    };

    post(postData, 0);
}

/**
 * 获取全年
 */
function ec_all() {
    clearButtonActive();
    $("#ec_all").addClass("active");
    $("#date_picker").addClass("hide");
    var date = new Date();

    var postData = {
        "type" : 1,
        "startTimestamp" : getLastSevenDay(date),
        "endTimestamp" : getTimeStamp(date),
        "userId" : sessionStorage.getItem("userId")
    };
    post(postData, 1);
}

function ec_custom() {
    var start = $("#beginTime").val();
    var end = $("#endTime").val();

    var postData = {
        "type" : 2,
        "startTimestamp" : getDateStampFromStr(start),
        "endTimestamp" : getDateStampFromStr(end),
        "userId" : sessionStorage.getItem("userId")
    };
    post(postData, 2);
}

/**
 * echarts渲染
 * @param data 图表数据
 */
function renderEcharts(data, type) {
    var legendList = ["实时交易额","历史平均交易额"];
    var echartKData = getEchartData(data); // data
    var kXAxis = getXAxis(data);

    var label = '';
    switch (type) {
        case 0:
            label = "7天";
            break;
        case 1:
            label = "历史";
            break;
        case 2:
            label = "";

    }

    var today = [];
    var total = [];
    var tmpToday = 0;
    var tmpTotal = 0;
    for (var i = 0; i < echartKData.length; i++) {
        tmpTotal += echartKData[i][0];
        total.push(parseInt(tmpTotal));
    }
    for (var j = 0; j < getDateHour(getTimeStamp(new Date())); j++) {
        tmpToday += echartKData[j][1];
        today.push(tmpToday);
    }

    var ecData = [
        {
            name : "实时交易额",
            type : "line",
            data : today
        },
        {
            name : "历史平均交易额",
            type : "line",
            data : total
        }
    ]

    require(
        [
            'echarts',
            'echarts/chart/k',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function(ec) {
            var myChart = ec.init(document.getElementById('echarts'), 'macarons');
            var myChartK = ec.init(document.getElementById('echarts_k'), 'macarons');
            var winWidth = document.documentElement.clientWidth;

            var option_k = {
                grid : {
                    x : '10%',
                    x2 : '2%'
                },
                title : {
                    text : label + '理财实时趋势（万元）'
                },
                tooltip : {
                    trigger : 'axis',
                    formatter : function (params) {
                        var res = params[0].seriesName + ' ' + params[0].name;
                        res +='<br/> 最高:' + params[0].value[3] + ' 最低:' + params[0].value[2];
                        res +='<br/> 平均:' + parseInt(params[0].value[0]) + ' 当前:' + params[0].value[1];
                        return res;
                    }
                },
                legend : {
                    x : 'center',
                    y : '30',
                    data : ['交易额']
                },
                toolbox : {
                    show : false,
                    orient : 'vertical',
                    feature : {
                        saveAsImage : { show : true }
                    }
                },
                xAxis : [
                    {
                        type : 'category',
                        data : kXAxis
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter : function(value) {
                                return value/10000;
                            }
                        }
                    }
                ],
                series : [
                    {
                        name : '交易额',
                        type : 'k',
                        data : echartKData
                    }
                ]
            };
            var option = {
                title : {
                    text : label + "实时交易曲线(万元)"
                },
                grid : {
                    x : '10%',
                    x2 : '2%',
                    y2 : '30%'
                },
                tooltip : {
                    trigger : 'axis',
                    axisPointer : { type : 'shadow' }
                },
                legend : {
                    x : 'center',
                    y : '80%',
                    data : legendList
                },
                xAxis : [
                    {
                        type : 'category',
                        data : kXAxis
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter : function(value) {
                                return value/10000;
                            }
                        }
                    }
                ],
                series : ecData
            };

            myChart.setOption(option);
            myChartK.setOption(option_k);

            myChart.connect(myChartK);
            myChartK.connect(myChart);

            $(window).resize(function() {
                myChart.resize();
                myChartK.resize();
            });

            $(window).beforeunload = function() {
                myChart.clear();
                myChart.dispose();
                myChartK.clear();
                myChartK.dispose();
            }
        }
    );
}

function post(data, type) {
    require(['echarts/echarts'],function(ec) {
        var myChart = ec.init(document.getElementById('echarts'), 'macarons');
        var myChartK = ec.init(document.getElementById('echarts_k'), 'macarons');
        myChart.showLoading({
            text: '正在努力的加载数据中...'
        });
        myChartK.showLoading({
            text: '正在努力的加载数据中...'
        });

        $.ajax({
            url : ERP_CONFIG.api_base_url + '/time_chart', // TODO:确定访问地址
            type : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify(data),
            datatype : 'json',
            success : function(data) {
                if (data && data.status == 0) {
                    $("#tradeRealBottomDiv").show();
                    renderEcharts(data, type);
                }else{
                    alert("您没有访问权限！");
                    $("#tradeRealContainerDiv").hide();
                }
                myChart.hideLoading();
                myChartK.hideLoading();
            },
            error : function() {
                console.error("初始化获取数据失败");
                myChart.hideLoading();
                myChartK.hideLoading();
            }
        });
    });
}

function getEchartData(data) {
    var series = new Array();
    for (var i = 0; i < data.content.timeChartInfoCellList.length; i++) {
        var average = data.content.timeChartInfoCellList[i].averageDepositAmount;
        var max = data.content.timeChartInfoCellList[i].maxDepositAmount;
        var min = data.content.timeChartInfoCellList[i].minDepositAmount;
        var now = data.content.timeChartInfoCellList[i].nowDepositAmount;
        var item = [average, now, min, max];
        series[i] = item;
    }
    return series;
}

function getXAxis(data) {
    var hours = [];
    for (var i = 0; i < data.content.timeChartInfoCellList.length; i++) {
        var hour = getDateHour(data.content.timeChartInfoCellList[i].startTime);
        hours.push(hour);
    }
    return hours;
}

function getDateHour(timeStamp) {
    var date = new Date(timeStamp * 1000);
    return date.getHours();
}