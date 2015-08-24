$(function() {
    require.config({
        paths: {
            echarts : 'js/echarts'
        }
    });

    var date = new Date();
    var endStamp = getTimeStamp(date);
    var startStamp = getLastSevenDay(date);

    var postData = {
        type : 0,
        startTimestamp : startStamp,
        endTimestamp : endStamp,
        userId : sessionStorage.getItem("userId")
    };

    post(postData, 0);
});

//获取企业code
function getParameterByName(name){
    var values = decodeURIComponent((location.search.match(RegExp("[?|&]" + name + '=([^\&]+)'))||[,null])[1]);
    return  values == "" || values == "null" ? "" : values;
};

function post(srcData, type) {
    require(['echarts/echarts'],function(ec) {
        var myChart_statistic = ec.init(document.getElementById('echarts_statistic'), 'macarons');
        var myChart_total = ec.init(document.getElementById('echarts_total'), 'macarons');
        myChart_statistic.showLoading({
            text: '正在努力的加载数据中...'
        });
        myChart_total.showLoading({
            text: '正在努力的加载数据中...'
        });

        $.ajax({
            url : ERP_CONFIG.api_base_url + '/user_statistic',
            type : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify(srcData),
            datatype : 'json',
            success : function(data) {
                if (data && data.status == 0) {
                    $("#userBottomDiv").show();
                    renderEcharts(data, type);
                }else{
                    alert("您没有访问权限！");
                }
                myChart_statistic.hideLoading();
                myChart_total.hideLoading();
            },
            error : function() {
                console.error("初始化获取数据失败");
                myChart_statistic.hideLoading();
                myChart_total.hideLoading();
            }
        });
    });
}

function getMonthDayStrByTimestamp(timeStamp) {
    var date = new Date(timeStamp*1000);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    return month + "/" + day;
}

function getTradeX(data) {
    var list = data.content.infoList;
    var tradex = [];

    for (var i = 0; i < list.length; i++) {
        var timeStamp = list[i].date;
        var dateStr = getMonthDayStrByTimestamp(timeStamp);
        tradex.push(dateStr);
    }
    return tradex;
}

function getLegendList(data) {
    var list = [
        "用户总数",
        "激活用户数",
        "绑定手机号用户数",
        "实名认证用户数",
        "绑定银行卡用户数",
        "理财用户数",
        "取消关注用户数",
        "理财中用户数"
    ];
    return list;
}

function getTotalData(data) {
    var list = data.content.infoList;
    var total = [];
    var not_activited = [];
    var activited = [];
    var bind_mobile = [];
    var certified = [];
    var bind_card = [];
    var deposited = [];
    var not_follow_total = [];
    var totalFinancePeoples = [];

    for (var i = 0; i < list.length; i++) {
        total.push(list[i].total);
        not_activited.push(list[i].notActivited);
        activited.push(list[i].activited);
        bind_mobile.push(list[i].bindMobile);
        certified.push(list[i].certified);
        bind_card.push(list[i].bindCard);
        deposited.push(list[i].deposited);
        not_follow_total.push(list[i].notFollowTotal);
        totalFinancePeoples.push(list[i].totalFinancePeoples);
    }

    var series = [
        {
            name : "用户总数",
            type : "line",
            data : total
        },
        {
            name : "激活用户数",
            type : "line",
            data : activited
        },
        {
            name : "绑定手机号用户数",
            type : "line",
            data : bind_mobile
        },
        {
            name : "实名认证用户数",
            type : "line",
            data : certified
        },
        {
            name : "绑定银行卡用户数",
            type : "line",
            data : bind_card
        },
        {
            name : "理财用户数",
            type : "line",
            data : deposited
        },
        {
            name : "取消关注用户数",
            type : "line",
            data : not_follow_total
        },
        {
            name : "理财中用户数",
            type : "line",
            data : totalFinancePeoples

        },
    ];
    return series;
}

function getDayStartTimestamp(timeStamp) {
    var date = new Date(timeStamp*1000);
    date.setHours(0,0,0,0);
    return parseInt(date.getTime()/1000);
}

function getSeries(data) {
    var list = data.content.infoList;
    var total = [];
    var not_activited = [];
    var activited = [];
    var bind_mobile = [];
    var certified = [];
    var bind_card = [];
    var deposited = [];
    var not_follow_total = [];
    var history_total = data.content.historyTotal;

    for (var i = 0; i < list.length; i++) {
        if (i == 0) { // first item, substract the history
            if (getDayStartTimestamp(1437494400) == getDayStartTimestamp(list[i].date)) {
                total.push(0);
                not_activited.push(0);
                activited.push(0);
                bind_mobile.push(0);
                certified.push(0);
                bind_card.push(0);
                deposited.push(0);
                not_follow_total.push(0);
            } else {
                total.push(list[i].total - history_total.total);
                not_activited.push(list[i].notActivited - history_total.notActivited);
                activited.push(list[i].activited - history_total.activited);
                bind_mobile.push(list[i].bindMobile - history_total.bindMobile);
                certified.push(list[i].certified - history_total.certified);
                bind_card.push(list[i].bindCard - history_total.bindCard);
                deposited.push(list[i].deposited - history_total.deposited);
                not_follow_total.push(-(list[i].notFollowTotal - history_total.notFollowTotal));
            }
        } else {
            total.push(list[i].total - list[i-1].total);
            not_activited.push(list[i].notActivited - list[i-1].notActivited);
            activited.push(list[i].activited - list[i-1].activited);
            bind_mobile.push(list[i].bindMobile - list[i-1].bindMobile);
            certified.push(list[i].certified - list[i-1].certified);
            bind_card.push(list[i].bindCard - list[i-1].bindCard);
            deposited.push(list[i].deposited - list[i-1].deposited);
            not_follow_total.push(-(list[i].notFollowTotal - list[i-1].notFollowTotal));
        }
    }

    var series = [
        {
            name : "用户总数",
            type : "bar",
            barWidth : 10,
            data : total
        },
        {
            name : "激活用户数",
            type : "bar",
            barWidth : 10,
            data : activited
        },
        {
            name : "绑定手机号用户数",
            type : "bar",
            barWidth : 10,
            data : bind_mobile
        },
        {
            name : "实名认证用户数",
            type : "bar",
            barWidth : 10,
            data : certified
        },
        {
            name : "绑定银行卡用户数",
            type : "bar",
            barWidth : 10,
            data : bind_card
        },
        {
            name : "理财用户数",
            type : "bar",
            barWidth : 10,
            data : deposited
        },
        {
            name : "取消关注用户数",
            type : "bar",
            barWidth : 10,
            data : not_follow_total
        }
    ];
    return series;
}

function getTradeNameList(data) {
    var list = [
        "用户总数",
        "激活用户数",
        "绑定手机号用户数",
        "实名认证用户数",
        "绑定银行卡用户数",
        "理财用户数",
        "取消关注用户数"
    ];
    return list;
}

/**
 * 渲染echarts
 * @param data 报表数据
 * @param label 显示类型
 */
function renderEcharts(data, type) {
    var tradeNameList = getTradeNameList(data);
    var tradeSeries = getSeries(data);
    var tradeX = getTradeX(data, type);

    var legendList = getLegendList(data);
    var ecData = getTotalData(data);

    require(
        [
            'echarts',
            'echarts/chart/line',
            'echarts/chart/bar'
        ],
        function (ec) {
            var myChart_statistic = ec.init(document.getElementById('echarts_statistic'), 'macarons');
            var myChart_total = ec.init(document.getElementById('echarts_total'), 'macarons');
            var winWidth = document.documentElement.clientWidth;

            var option_statistic = {
                grid : {
                    x : '13%',
                    x2 : '2%',
                    y2 : '150'
                },
                title : {
                    text : '每日新增用户量'
                },
                tooltip : {
                    trigger : 'axis',
                    axisPointer : { type : 'shadow' }
                },
                legend: {
                    x : 'center',
                    y : '280',
                    data : tradeNameList
                },
                toolbox : {
                    show : false,
                    orient : 'vertical',
                    feature : {
                        dataView : { show : true, readOnly : true },
                        restore : { show : true}
                    }
                },
                xAxis : [
                    {
                        type : 'category',
                        data : tradeX
                    }
                ],
                yAxis : [
                    {
                        name : '人数（千）',
                        type : 'value',
                        axisLabel : {
                            formatter: function(value) {
                                return value/1000;
                            }
                        }
                    }
                ],
                series : tradeSeries
            };
            var option_total = {
                title : {
                    text : "用户总量统计"
                },
                grid : {
                    x : '13%',
                    x2 : '10%',
                    y2 : '150'
                },
                tooltip : {
                    trigger : 'axis',
                    axisPointer : { type : 'shadow' }
                },
                legend : {
                    x : 'center',
                    y : '280',
                    data : legendList
                },
                xAxis : [
                    {
                        type : 'category',
                        data : tradeX
                    }
                ],
                yAxis : [
                    {
                        name : '人数(千)',
                        type : 'value',
                        axisLabel : {
                            formatter: function(value) {
                                return value/1000;
                            }
                        }
                    }
                ],
                series : ecData
            };

            myChart_statistic.setOption(option_statistic);
            myChart_total.setOption(option_total);

            myChart_statistic.connect(myChart_total);
            myChart_total.connect(myChart_statistic);

            $(window).resize(function() {
                myChart_statistic.resize();
                myChart_total.resize();
            });
            $(window).beforeunload = function() {
                myChart_statistic.clear();
                myChart_total.clear();
                myChart_statistic.dispose();
                myChart_total.dispose();
            }
        }
    );
}