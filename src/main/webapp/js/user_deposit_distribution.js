/**
 * Created by tobepro on 2015/6/29 0029.
 */

var queryType = {
    DEFAULT : 0,
    AVERAGE : 1,
    TOTAL : 2,
    PERCENT : 3
}

$(function() {
    require.config({
        paths: {
            echarts : 'js/echarts'
        }
    });

    var date = new Date();
    var distributionData = {
        type : 1,
        startTimestamp : getLastSevenDay(date),
        endTimestamp: getTimeStamp(date),
        productId : 0,
        userId : sessionStorage.getItem("userId")
    };

    $.ajax({
        url : ERP_CONFIG.api_base_url + '/product_list', // TODO:确定访问地址
        type : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        datatype : 'json',
        success : function(data) {
            if (data) {
                push_to_radio_group(data);
            }
        },
        error : function() {
            console.error("初始化获取数据失败");
        }
    });

    post(distributionData, "全部产品人均交易额");

    $(".selectpicker").selectpicker();
    $(".selectpicker").on("change", search);

    $("#ec_average").addClass("active");

    $("#refresh").on("click", search);

    $('#beginTime').date();
    $('#endTime').date();

    $("#ec_average").on("click", function() {
        clearButtonActive();
        $("#ec_average").addClass("active");
        search();
    });
    $("#ec_total").on("click", function() {
        clearButtonActive();
        $("#ec_total").addClass("active");
        search();
    });
    $("#ec_percent").on("click", function() {
        clearButtonActive();
        $("#ec_percent").addClass("active");
        search();
    });
});

//获取企业code
function getParameterByName(name){
    var values = decodeURIComponent((location.search.match(RegExp("[?|&]" + name + '=([^\&]+)'))||[,null])[1]);
    return  values == "" || values == "null" ? "" : values;
};

function getActiveItemStr() {
    var ret = "";
    switch (getSelectType()) {
        case queryType.AVERAGE:
            ret = "人均交易额";
            break;
        case queryType.TOTAL:
            ret = "总交易额";
            break;
        case queryType.PERCENT:
            ret = "人数占比";
            break;
        default:
            ret = "人均交易额";
            break;
    }
    return ret;
}

function searchByDate(start, end) {
    var productId = $(".selectpicker").find("option:selected").val();

    if (!productId) {
        return;
    }

    var date = new Date();

    var postData = {
        type : getSelectType(),
        productId : productId,
        startTimestamp : start,
        endTimestamp : end,
        userId : sessionStorage.getItem("userId")
    };

    var title = "";
    title += $(".selectpicker").find("option:selected").text();
    title += getActiveItemStr();
    title = trimSpaceEnter(title);
    post(postData, title);
}

function search() {
    var date = new Date();
    var start = $("#beginTime").val();
    var end = $("#endTime").val();
    if (start > end) {
        return;
    }
    if (!start || !end) {
        searchByDate(getLastSevenDay(date), getTimeStamp(date));
    } else {
        searchByDate(convert_date(start), convert_date(end));
    }
}

function trimSpaceEnter(resultStr) {
    var resultStr=resultStr.replace(/\ +/g,"");//去掉空格
    resultStr=resultStr.replace(/[\r\n]/g,"");//去掉回车换行
    return resultStr;
}

/**
 * 填充产品列表
 * @param list
 */
function push_to_radio_group(data) {
    var radio_group = $("#sel_product");
    var list = data.content.productList;
    for (var i = 0; i < list.length; i++) {
        radio_group.append(new Option(list[i].name, list[i].id));
    }
    radio_group.selectpicker('refresh');
}

function getRoseData(data) {
    var ret = [];
    if (data.content.once != 0) {
        ret.push({
            name : '一次',
            value : data.content.once
        });
    }
    if (data.content.twice != 0) {
        ret.push({
            name : '两次',
            value : data.content.twice
        });
    }
    if (data.content.three != 0) {
        ret.push({
            name : '三次',
            value : data.content.three
        });
    }
    if (data.content.four != 0) {
        ret.push({
            name : '四次',
            value : data.content.four
        });
    }
    if (data.content.five != 0) {
        ret.push({
            name : '五次及以上',
            value : data.content.five
        });
    }

    return ret;
}

function getLegendList(ecData) {
    var list = [];
    for (var i = 0; i < ecData.length; i++) {
        list.push(ecData[i].name);
    }
    return list;
}
function renderEcharts(data, title) {
    var ecData = getRoseData(data);
    var legendList = getLegendList(ecData);

    require(
        [
            'echarts',
            'echarts/chart/pie'
        ],
        function(ec) {
            var myChart = ec.init(document.getElementById('echarts'), 'macarons');
            var winWidth = document.documentElement.clientWidth;

            var option = {
                title : {
                    text : title
                },
                tooltip : {
                    trigger : 'item',
                    formatter : "{a} <br/>{b} : {c} ({d}%)"
                },
                legend : {
                    x : 'center',
                    y : '85%',
                    data : legendList
                },
                series : [
                    {
                        name : title,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false
                                },
                                labelLine : {
                                    show : false
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true
                                },
                                labelLine : {
                                    show : true
                                }
                            }
                        },
                        type : 'pie',
                        radius : [15, 70],     // 半径
                        center : ['50%', '40%'],  // 圆心坐标
                        roseType : 'area',      // 面积模式
                        //max : 40,               // 指定最大值
                        sort : 'ascending',     // 数据排序
                        data : ecData
                    }
                ]
            };

            myChart.setOption(option);

            $(window).resize(function() {
                myChart.resize();
            });

            $(window).beforeunload = function() {
                myChart.clear();
                myChart.dispose();
            }
        }
    );
}

function post(srcData, title) {
    require(['echarts/echarts'],function(ec) {
        var myChart = ec.init(document.getElementById('echarts'), 'macarons');
        myChart.showLoading({
            text: '正在努力的加载数据中...'
        });

        $.ajax({
            url : ERP_CONFIG.api_base_url + '/user_purchase', // TODO:确定访问地址
            type : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify(srcData),
            datatype : 'json',
            success : function(data) {
                if (data && data.status == 0) {
                    $("#userDepositBottomDiv").show();
                    renderEcharts(data, title); // TODO: title
                }else{
                    alert("您没有访问权限！");
                    $("#userDepositContainerDiv").hide();
                }
                myChart.hideLoading();
            },
            error : function() {
                console.error("初始化获取数据失败");
                myChart.hideLoading();
            }
        });
    });
}

function getSelectType() {
    if ($("#ec_average").hasClass("active")) {
        return queryType.AVERAGE;
    }
    if ($("#ec_total").hasClass("active")) {
        return queryType.TOTAL;
    }
    if ($("#ec_percent").hasClass("active")) {
        return queryType.PERCENT;
    }
    return queryType.AVERAGE;
}

function clearButtonActive() {
    $("#ec_average").removeClass("active");
    $("#ec_total").removeClass("active");
    $("#ec_percent").removeClass("active");
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