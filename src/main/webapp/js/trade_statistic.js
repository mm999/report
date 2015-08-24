/**
 * Created by tobepro on 2015/6/26 0026.
 */


var IN_FIX_TERM_FROM_FUND = "活期买定期+";
var IN_FIX_TERM_FROM_BANK = "银行卡买定期";
var IN_FIX_TERM_FROM_BIG = "大客户买定期";
var IN_FUND_FROM_FIX_TERM = "定期到期+";
var IN_FUND_FROM_BANK = "银行卡买活期";
var IN_FUND_FROM_BIG = "大客户买活期";
var IN_FUND_FROM_MIGRATE = "老用户迁移";
var OUT_FIX_TERM_TO_FUND = "定期到期-";
var OUT_FUND_TO_FIX_TERM = "活期买定期-";
var OUT_FUND_TO_BANK = "活期宝提现";
var TOTAL_TRANS_MONEY = "交易量";

$(function() {
    require.config({
        paths: {
            echarts : 'js/echarts'
        }
    });

    //var code = getParameterByName("code");
    //if(code != null && code != ""){
    //    getUserId(code);
    //}
    //if(sessionStorage.getItem("userId") == null){
    //    return ;
    //}

    //测试代码
    sessionStorage.seti


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
    enableDatePicker();
    $("#ec_day").on("click", listDay);
    $("#ec_week").on("click", listWeek);
    $("#ec_month").on("click", listMonth);
    $("#ec_custom").on("click", listCustom);

    $("#ec_day").addClass("active");
});

//获取企业code
function getParameterByName(name){
    var values = decodeURIComponent((location.search.match(RegExp("[?|&]" + name + '=([^\&]+)'))||[,null])[1]);
    return  values == "" || values == "null" ? "" : values;
};

//获取userid
function getUserId(code) {
    var srcData = {"code":code};
    $.ajax({
        url : ERP_CONFIG.api_base_url + '/getUserInfo', // TODO:确定访问地址
        async: false ,
        type : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data : JSON.stringify(srcData),
        datatype : 'json',
        success : function(data) {
            if (data && data.content) {
                sessionStorage.setItem("userId",data.content);
            }else{
                //alert("获取用户信息失败！");
                return;
            }
        },
        error : function() {
            console.error("初始化获取数据失败");
        }
    });
}

function enableDatePicker() {
    var date_now = new Date();

    //$('#beginTime').date();
    //$('#endTime').date();
    $("#show_date_picker").on("click", show_date_picker);
}

function clearButtonActive() {
    $("#ec_day").removeClass("active");
    $("#ec_week").removeClass("active");
    $("#ec_month").removeClass("active");
    $("#show_date_picker").removeClass("active");
}

/**
 * 显示datepicker
 */
function show_date_picker() {
    clearButtonActive();
    $("#show_date_picker").addClass("active");
    $("#date_picker").removeClass("hide");

    $("#beginTime").val(getDate(6));
    $("#endTime").val(getDate(0));
}

function getDate(day){
    var zdate=new Date();
    var sdate=zdate.getTime();
    var edate=new Date(sdate-(day*24*60*60*1000)).format("yyyy-MM-dd");
    return edate;
}
//日期格式化
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 获取横坐标
 * @param data  原始数据
 * @param type  日期类型
 * @returns {Array} 坐标数组
 */
function getTradeX(data, type) {
    var xAxis = [];
    for (var i = 0; i < data.content.transInfoCellList.length; i++) {
        var time = data.content.transInfoCellList[i].startTime;
        var date = new Date(time*1000);
        switch (type) {
            case 0 :        // day
                var strDate = date.getMonth() + 1 + "/" + date.getDate();
                xAxis.push(strDate);
                break;
            case 1 :        // week
                var strDate = date.getMonth() + 1 + "/" + date.getDate();
                xAxis.push(strDate);
                break;
            case 2 :        // month
                var strDate = date.getMonth() + 1;
                xAxis.push(strDate);
                break;
            default :
                break;
        }
    }
    return xAxis;
}

/**
 * 获取图例
 * @param data 数据源
 * @returns {Array} 图例列表
 */
function getDetailLegendList(data) {
    var productInfoList = data.content.transInfoCellList;
    var legendObject = {};
    var legendList = [];
    var countInFundFromBank = 0;
    var countInFundFromBig = 0;
    var countInFundFromMigrate = 0;
    var countOutFundToBank = 0;

    for (var i = 0; i < productInfoList.length; i++) {
        var productInfo = productInfoList[i].productInfo;
        for (var j = 0; j < productInfo.outFundToFixTermList.length; j++) {
            var product = productInfo.outFundToFixTermList[j];
            legendObject[OUT_FUND_TO_FIX_TERM + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.outFixTermToFundList.length; j++) {
            var product = productInfo.outFixTermToFundList[j];
            legendObject[OUT_FIX_TERM_TO_FUND + ":" + product.name ] = "1";
        }
        for (var j = 0; j < productInfo.inFixTermFromFundList.length; j++) {
            var product = productInfo.inFixTermFromFundList[j];
            legendObject[IN_FIX_TERM_FROM_FUND + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.inFundFromFixTermList.length; j++) {
            var product = productInfo.inFundFromFixTermList[j];
            legendObject[IN_FUND_FROM_FIX_TERM + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.inFixTermFromBankList.length; j++) {
            var product = productInfo.inFixTermFromBankList[j];
            legendObject[IN_FIX_TERM_FROM_BANK + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.inFixTermFromBigUserList.length; j++) {
            var product = productInfo.inFixTermFromBigUserList[j];
            legendObject[IN_FIX_TERM_FROM_BIG + ":" + product.name ] = "1";
        }

        countInFundFromBank += productInfo.inFundTransDeductionAmount;
        countInFundFromBig += productInfo.inBigUserFundAmount;
        countInFundFromMigrate += productInfo.inMigrateAmount;
        countOutFundToBank += productInfo.outTransWithdrawAmount;
    }

    if (countOutFundToBank != 0) {
        legendObject[OUT_FUND_TO_BANK] = "1";
    }

    if (countInFundFromBank != 0) {
        legendObject[IN_FUND_FROM_BANK] = "1";
    }
    if (countInFundFromBig != 0) {
        legendObject[IN_FUND_FROM_BIG] = "1";
    }
    if (countInFundFromMigrate != 0) {
        legendObject[IN_FUND_FROM_MIGRATE] = "1";
    }

    for (var legend in legendObject) {
        legendList.push(legend);
    }

    return legendList;
}

function getInitMap(legendList) {
    var map = {};
    for (var i = 0; i < legendList.length; i++) {
        map[legendList[i]] = 0;
    }
    return map;
}
function sortDetailSeries(a, b) {
    if ((a.data[0] - b.data[0]) < 0) {
        return -1
    } else {
        return 1;
    }
}
/**
 * 获取显示数据
 * @param data
 * @returns {Array}
 */
function getDetailSeries(data, legendList) {
    var list = [];
    var productInfoList = data.content.transInfoCellList;
    var inFixTermFromFundName = {};
    var inFundFromFixTermName = {};
    var outFundToFixTermName = {};
    var outFixTermToFundName = {};
    var inFixTermFromBankName = {};
    var inFixTermFromBigUserName = {};

    for (var i = 0; i < productInfoList.length; i++) {
        var productInfo = productInfoList[i].productInfo;
        for (var j = 0; j < productInfo.inFixTermFromFundList.length; j++) {
            var product = productInfo.inFixTermFromFundList[j];
            inFixTermFromFundName[IN_FIX_TERM_FROM_FUND + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.inFundFromFixTermList.length; j++) {
            var product = productInfo.inFundFromFixTermList[j];
            inFundFromFixTermName[IN_FUND_FROM_FIX_TERM + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.outFundToFixTermList.length; j++) {
            var product = productInfo.outFundToFixTermList[j];
            outFundToFixTermName[OUT_FUND_TO_FIX_TERM + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.outFixTermToFundList.length; j++) {
            var product = productInfo.outFixTermToFundList[j];
            outFixTermToFundName[OUT_FIX_TERM_TO_FUND + ":" + product.name ] = "1";
        }
        for (var j = 0; j < productInfo.inFixTermFromBankList.length; j++) {
            var product = productInfo.inFixTermFromBankList[j];
            inFixTermFromBankName[IN_FIX_TERM_FROM_BANK + ":" + product.name] = "1";
        }
        for (var j = 0; j < productInfo.inFixTermFromBigUserList.length; j++) {
            var product = productInfo.inFixTermFromBigUserList[j];
            inFixTermFromBigUserName[IN_FIX_TERM_FROM_BIG + ":" + product.name ] = "1";
        }
    }

    var dataMapList = [];
    for (var i = 0; i < productInfoList.length; i++) {
        var dataMap = getInitMap(legendList);

        var productInfo = productInfoList[i].productInfo;
        for (var j = 0; j < productInfo.inFixTermFromFundList.length; j++) {
            var product = productInfo.inFixTermFromFundList[j];
            dataMap[IN_FIX_TERM_FROM_FUND + ":" + product.name] = product.depositAmount;
        }
        for (var j = 0; j < productInfo.inFundFromFixTermList.length; j++) {
            var product = productInfo.inFundFromFixTermList[j];
            dataMap[IN_FUND_FROM_FIX_TERM + ":" + product.name] = product.depositAmount;
        }
        for (var j = 0; j < productInfo.inFixTermFromBankList.length; j++) {
            var product = productInfo.inFixTermFromBankList[j];
            dataMap[IN_FIX_TERM_FROM_BANK + ":" + product.name] = product.depositAmount;
        }
        for (var j = 0; j < productInfo.inFixTermFromBigUserList.length; j++) {
            var product = productInfo.inFixTermFromBigUserList[j];
            dataMap[IN_FIX_TERM_FROM_BIG + ":" + product.name ] = product.depositAmount;
        }

        dataMap[IN_FUND_FROM_BANK] = productInfo.inFundTransDeductionAmount;
        dataMap[IN_FUND_FROM_BIG] = productInfo.inBigUserFundAmount;
        dataMap[IN_FUND_FROM_MIGRATE] = productInfo.inMigrateAmount;

        for (var j = 0; j < productInfo.outFundToFixTermList.length; j++) {
            var product = productInfo.outFundToFixTermList[j];
            dataMap[OUT_FUND_TO_FIX_TERM + ":" + product.name] = -product.depositAmount;
        }
        for (var j = 0; j < productInfo.outFixTermToFundList.length; j++) {
            var product = productInfo.outFixTermToFundList[j];
            dataMap[OUT_FIX_TERM_TO_FUND + ":" + product.name ] = -product.depositAmount;
        }
        dataMap[OUT_FUND_TO_BANK] = -productInfo.outTransWithdrawAmount;
        dataMapList.push(dataMap)
    }

    for (var i = 0; i < legendList.length; i++) {
        var seriesData = [];
        var name = legendList[i];
        for (var j = 0; j < dataMapList.length; j++) {
            seriesData.push(dataMapList[j][name]);
        }

        var series = {
            name : name,
            type : "bar",
            stack : "day",
            barWidth : 10,
            data : seriesData
        };

        list.push(series);
    }

    //交易量
    var transAmountArr = [];
    for(var k = 0,length = productInfoList.length; k < length ; k++){
        var productInfo = productInfoList[k].productInfo;
        transAmountArr.push(productInfo.amountInit);
    }
    var transAmountData = {
        name : TOTAL_TRANS_MONEY,
        type : "line",
        yAxisIndex : 1,
        barWidth : 10,
        data : transAmountArr
    };
    list.push(transAmountData);

    return list.sort(sortDetailSeries);
}

/**
 * 渲染echarts
 * @param data 报表数据
 * @param label 显示类型
 */
function renderEcharts(data, type) {
    var legend = getDetailLegendList(data);
    var detailSeries = getDetailSeries(data, legend);
    //注意在此处加上“交易量”
    legend.push(TOTAL_TRANS_MONEY);
    var tradeX = getTradeX(data, type);

    //var dataZoom = getDataZoom(tradeX);
    var dataZoom = null;
    var label = "";
    if (!type) {
        label = '日';
    }
    switch (type) {
        case 0:
            label = "日";
            break;
        case 1:
            label = "周";
            break;
        case 2:
            label = "月";
            break;
        default:
            label = "日";
            break;
    }
    var legendList = [label + '净增交易额', '理财中金额'];
    var ecData = getOptionTotalData(data, label);

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
                    x : '12%',
                    x2 : '10%',
                    y2 : '500'
                },
                title : {
                    text : '理财(' + label + ')流水统计(万元)'
                },
                tooltip : {
                    trigger : 'axis',
                    axisPointer : { type : 'shadow' }
                },
                legend: {
                    x : 'center',
                    y : '280',
                    data : legend
                },
                toolbox : {
                    show : false,
                    orient : 'vertical',
                    feature : {
                        dataView : { show : true, readOnly : true },
                        restore : { show : true}
                    }
                },
                dataZoom : dataZoom,
                xAxis : [
                    {
                        type : 'category',
                        data : tradeX
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: function(value) {
                                return value/10000;
                            }
                        }
                    },
                    {
                        name : '交易量',
                        type : 'value',
                        splitNumber : 5,
                        splitLine : {
                            show : false
                        },
                        splitArea : {
                            show : false
                        },
                        axisLabel : {
                            formatter : function(value) {
                                return value/10000;
                            }
                        }
                    }
                ],
                series : detailSeries
            };
            var option_total = {
                title : {
                    text : "理财(" + label + ")交易额统计(万元)"
                },
                grid : {
                    x : '12%',
                    x2 : '12%',
                    y : '25%'
                },
                tooltip : {
                    trigger : 'axis',
                    axisPointer : { type : 'shadow' }
                },
                legend : {
                    x : 'center',
                    y : '11%',
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
                        name : label +'净增',
                        type : 'value',
                        splitNumber : 5,
                        axisLabel : {
                            formatter : function(value) {
                                if (getMax(option_total.series[1].data) == 0) {
                                    return value;
                                } else {
                                    return value/10000;
                                }
                            }
                        }
                    },
                    {
                        name : '总计',
                        type : 'value',
                        splitNumber : 5,
                        splitLine : {
                            show : false
                        },
                        splitArea : {
                            show : false
                        },
                        axisLabel : {
                            formatter : function(value) {
                                return value/10000;
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

function post(srcData, type) {
    require(['echarts/echarts'],function(ec){
        var echarts_total = ec.init(document.getElementById('echarts_total'), 'macarons');
        var echarts_statistic = ec.init(document.getElementById('echarts_statistic'), 'macarons');
        echarts_total.showLoading({
            text:'正在努力的加载数据中...'
        });
        echarts_statistic.showLoading({
            text:'正在努力的加载数据中...'
        });

        $.ajax({
            url : ERP_CONFIG.api_base_url + '/product_transinfo', // TODO:确定访问地址
            type : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify(srcData),
            datatype : 'json',
            success : function(data) {
                if (data && data.status == 0) {
                    $("#erp_tab_group").show();
                    $("#bottomDiv").show();
                    renderEcharts(data, type);
                }else{
                    alert("您没有访问权限！");
                }
                echarts_total.hideLoading();
                echarts_statistic.hideLoading();
            },
            error : function() {
                console.error("初始化获取数据失败");
                echarts_total.hideLoading();
                echarts_statistic.hideLoading();
            }
        });
    });
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

/**
 * 按天显示
 */
function listDay() {
    clearButtonActive();
    $("#ec_day").addClass("active");
    $("#date_picker").addClass("hide");

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
}

/**
 * 按周显示
 */
function listWeek() {
    clearButtonActive();
    $("#ec_week").addClass("active");
    $("#date_picker").addClass("hide");

    var date = new Date();
    var endStamp = getTimeStamp(date);
    var startStamp = getLastSevenWeek(date);

    var postData = {
        type : 1,
        startTimestamp : startStamp,
        endTimestamp : endStamp,
        userId : sessionStorage.getItem("userId")
    };
    post(postData, 1);
}

/**
 * 按月显示
 */
function listMonth() {
    clearButtonActive();
    $("#ec_month").addClass("active");
    $("#date_picker").addClass("hide");
    var date = new Date();
    var endStamp = getTimeStamp(date);
    var startStamp = getLastSevenMonth(date);

    var postData = {
        type : 2,
        startTimestamp : startStamp,
        endTimestamp : endStamp,
        userId : sessionStorage.getItem("userId")
    };
    post(postData, 2);
}

/**
 * 自定义显示
 */
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
        type : 0,
        startTimestamp : startDate,
        endTimestamp : endDate,
        userId : sessionStorage.getItem("userId")
    };

    post(postData, 0);
}

/**
 * 获取每日增长列表
 * @param data 数据源
 * @returns {Array} 每日增长列表
 */
function getIncrease(data) {
    var increate = [];
    var dataList = data.content.transInfoCellList;
    for (var i = 0; i < dataList.length; i++) {
        var increaseOneDay = dataList[i].incremental;
        var withdrawOneDay = dataList[i].withdrawTotal;
        if (increaseOneDay) {
            increate.push(Math.round(increaseOneDay*100)/100);
        } else {
            increate.push('0');
        }
    }
    return increate;
}

/**
 * 获取历史总销售额
 * @param data 数据源
 * @returns {Array} 历史总销售额累加表
 */
function getTotal(data) {
    var increate = [];
    var dataList = data.content.transInfoCellList;
    for (var i = 0; i < dataList.length; i++) {
        var increaseOneDay = dataList[i].depositTotal;
        var withdrawOneDay = dataList[i].withdrawTotal;
        if (increaseOneDay) {
            increate.push(Math.round(increaseOneDay*100)/100);
        } else {
            increate.push('0');
        }
    }
    return increate;
}


function getMax(total) {
    var max = 0;
    for (var i = 0; i < total.length; i++) {
        if (total[i] > max) {
            max = total[i];
        }
    }
    return max;
}
function getOptionTotalData(data, label) {
    var ecData = [];
    var increase = getIncrease(data);
    var total = getTotal(data);

    var tradeItem = {
        name : label + '净增交易额',
        type : 'bar',
        barWidth : 10,
        data : increase
    }
    var tradeItem1 = {
        name : '理财中金额',
        type : 'line',
        yAxisIndex : 1,
        data : total
    }

    ecData.push(tradeItem1);
    ecData.push(tradeItem);
    return ecData;
};

function getDataZoom(tradeX) {
    var size = tradeX.length;
    var dataZoom = {
        show : false,
        start : 0,
        end : 100
    }
    if (size > 20) {
        dataZoom.show = true;
        dataZoom.start = parseInt(100 - 20/size*100);
    }
    console.debug(dataZoom);
    return dataZoom;
}
