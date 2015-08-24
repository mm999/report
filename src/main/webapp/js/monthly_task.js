function getMonthStartTimestamp(date) {
    var d = new Date(date.getFullYear(), date.getMonth(), 1);
    return getTimeStamp(d);
}

function getMonthEndTimestamp(date) {
    var d = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    d.setHours(23);
    d.setMinutes(59);
    d.setSeconds(59)

    return getTimeStamp(d);
}

/**
 * 时间戳转换，日期转换成时间戳
 * @param date_str 格式化日期“YYYY-MM-DD”
 * @returns {number} 返回Unix时间戳
 */
function convert_date(date_str) {
    var startDate = new Date();
    startDate.setFullYear(date_str.substring(0, 4));
    startDate.setMonth(date_str.substring(5, 7) -1);
    startDate.setDate(date_str.substring(8, 10));
    startDate.setHours("00");
    startDate.setMinutes("00");
    startDate.setSeconds("00");
    return Date.parse(startDate)/1000;
}

$(function() {
    require.config({
        paths: {
            echarts : 'js/echarts'
        }
    });

    var date = new Date();
    var label = (date.getMonth() + 1) + "月份";
    var code = getParameterByName("code");
    var postData = {
        startTimestamp : getMonthStartTimestamp(date),
        endTimestamp : getMonthEndTimestamp(date),
        userId : sessionStorage.getItem("userId")
    };

    post(postData, label);

    $('#beginTime').date();
    $('#endTime').date();

    $("#erp_btn_custom").on("click", show_date_picker);
});

//获取企业code
function getParameterByName(name){
    var values = decodeURIComponent((location.search.match(RegExp("[?|&]" + name + '=([^\&]+)'))||[,null])[1]);
    return  values == "" || values == "null" ? "" : values;
};

function show_date_picker() {
    $("#date_picker").removeClass("hide");
    $("#ec_custom").on("click", listCustom);
}

function listCustom() {
    var start = $("#beginTime").val();
    var end = $("#endTime").val();

    if (!start || !end) {
        return;
    }

    var startDate = convert_date(start);
    var endDate = convert_date(end);

    if (endDate < startDate) {
        return;
    }

    var postData = {
        startTimestamp : startDate,
        endTimestamp : endDate,
        userId : sessionStorage.getItem("userId")
    };

    var date = new Date(startDate*1000);
    var dateEnd = new Date(endDate*1000);
    var label = (date.getMonth() + 1) + "-" + (dateEnd.getMonth() + 1) + "月份";
    post(postData, label);
}

function post(postData, label) {
    require(['echarts/echarts'],function(ec) {
        var myChartAttention = ec.init(document.getElementById('echarts_attention'), 'macarons');
        var myChartTradeUser = ec.init(document.getElementById('echarts_trade_user'), 'macarons');
        var myChartTradeTotal = ec.init(document.getElementById('echarts_trade'), 'macarons');
        myChartAttention.showLoading({
            text: '正在努力的加载数据中...'
        });
        myChartTradeUser.showLoading({
            text: '正在努力的加载数据中...'
        });
        myChartTradeTotal.showLoading({
            text: '正在努力的加载数据中...'
        });

        $.ajax({
            url : ERP_CONFIG.api_base_url + '/monthly_task', // TODO:确定访问地址
            type : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify(postData),
            datatype : 'json',
            success : function(data) {
                if (data && data.status == 0) {
                    $("#monthlyBottomDiv").show();
                    renderEcharts(data, label);
                }else{
                    alert("您没有访问权限！");
                    $("#monthlyDateDiv").hide();
                }
                myChartAttention.hideLoading();
                myChartTradeUser.hideLoading();
                myChartTradeTotal.hideLoading();
            },
            error : function() {
                console.error("初始化获取数据失败");
                myChartAttention.hideLoading();
                myChartTradeUser.hideLoading();
                myChartTradeTotal.hideLoading();
            }
        });
    });
}

/**
 * echarts渲染
 * @param data 图表数据
 */
function renderEcharts(data, label) {

    require(
        [
            'echarts',
            'echarts/chart/k',
            'echarts/chart/gauge'
        ],
        function(ec) {
            var myChartAttention = ec.init(document.getElementById('echarts_attention'), 'macarons');
            var myChartTradeUser = ec.init(document.getElementById('echarts_trade_user'), 'macarons');
            var myChartTradeTotal = ec.init(document.getElementById('echarts_trade'), 'macarons');

            var winWidth = document.documentElement.clientWidth;
            var option_attention = {
                tooltip: {
                    formatter: "{a} <br/>{b}: {c}"
                },
                toolbox: {
                    show: false,
                    feature: {
                        mark: {show: true},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: label,
                        type: 'gauge',          // 仪表盘
                        radius : '95%',
                        min: 0,                 // 指定最小值
                        max: 20000,               // 指定最大值
                        splitNumber: 10,        // 分割段数
                        axisLabel : {
                            formatter : function(value) {
                                return value/1000;
                            }
                        },
                        axisLine: {             // 坐标轴线
                            lineStyle: {        // 属性lineStyle控制线条样式
                                width: 5
                            }
                        },
                        axisTick: {             // 坐标轴小标记
                            length: 15,         // 属性length控制线长
                            lineStyle: {        // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        splitLine: {            // 分隔线
                            length: 20,         // 属性length控制线长
                            lineStyle: {        // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        title: {
                            textStyle: {        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder',
                                fontSize: 15,
                                fontStyle: 'italic'
                            }
                        },
                        detail: {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            }
                        },
                        data: [
                            {
                                value: data.content.attentions,
                                name: '关注量(千)'
                            }
                        ]
                    }
                ]
            };

            var option_trade_user = {
                tooltip: {
                    formatter: "{a} <br/>{b}: {c}"
                },
                toolbox: {
                    show: false,
                    feature: {
                        mark: {show: true},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: label,
                        type: 'gauge',          // 仪表盘
                        radius : '95%',
                        min: 0,                 // 指定最小值
                        max: 2000,               // 指定最大值
                        splitNumber: 10,        // 分割段数
                        axisLine: {             // 坐标轴线
                            lineStyle: {        // 属性lineStyle控制线条样式
                                width: 5
                            }
                        },
                        axisTick: {             // 坐标轴小标记
                            length: 15,         // 属性length控制线长
                            lineStyle: {        // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        splitLine: {            // 分隔线
                            length: 20,         // 属性length控制线长
                            lineStyle: {        // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        title: {
                            textStyle: {        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder',
                                fontSize: 15,
                                fontStyle: 'italic'
                            }
                        },
                        detail: {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            }
                        },
                        data: [
                            {
                                value: data.content.tradeUsers,
                                name: '交易用户数量'
                            }
                        ]
                    }
                ]
            };

            var option_trade = {
                tooltip: {
                    formatter: "{a} <br/>{b}: {c}"
                },
                toolbox: {
                    show: false,
                    feature: {
                        mark: {show: true},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: label,
                        type: 'gauge',          // 仪表盘
                        radius : '95%',
                        min: 0,                 // 指定最小值
                        max: 100000000,               // 指定最大值
                        splitNumber: 10,        // 分割段数
                        axisLabel : {
                            formatter : function(value) {
                                return value/10000;
                            }
                        },
                        axisLine: {             // 坐标轴线
                            lineStyle: {        // 属性lineStyle控制线条样式
                                width: 5
                            }
                        },
                        axisTick: {             // 坐标轴小标记
                            length: 15,         // 属性length控制线长
                            lineStyle: {        // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        splitLine: {            // 分隔线
                            length: 20,         // 属性length控制线长
                            lineStyle: {        // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        title: {
                            textStyle: {        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder',
                                fontSize: 15,
                                fontStyle: 'italic'
                            }
                        },
                        detail: {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            },
                            formatter : function(value) {
                                return parseInt(value/10000);
                            }
                        },
                        data: [
                            {
                                value: data.content.totalTrade,
                                name: '总成交量（万）'
                            }
                        ]
                    }
                ]
            };

            myChartAttention.setOption(option_attention);
            myChartTradeTotal.setOption(option_trade);
            myChartTradeUser.setOption(option_trade_user);

            $("#label_attention").text("预期:" + data.content.estimateAttentions);
            $("#label_trade_user").text("预期:" + data.content.estimateTradeUsers);
            $("#label_trade").text("预期:" + data.content.estimateTotalTrade);

            $(window).resize(function () {
                myChartAttention.resize();
                myChartTradeTotal.resize();
                myChartTradeUser.resize();
            });

            $(window).beforeunload = function () {
                myChartAttention.clear();
                myChartAttention.dispose();
                myChartTradeTotal.clear();
                myChartTradeTotal.dispose();
                myChartTradeUser.clear();
                myChartTradeUser.dispose();
            }
        }
    );
}