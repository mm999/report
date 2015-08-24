/**
 * Created by tobepro on 2015/7/8 0008.
 */

/**
 * 获取当前时间戳（精确到秒）
 * @param date 当前时间
 * @returns {Number} 当前时间戳
 */
function getTimeStamp(date) {
    return parseInt(date.getTime()/1000);
}

/**
 * 获取七周前时间戳（精确到秒）
 * @param date 当前时间
 * @returns {number} 七周前时间戳
 */
function getLastSevenWeek(date) {
    return parseInt(date.getTime()/1000) - 6*7*60*60*24;
}

/**
 * 获取七天前时间戳（精确到秒）
 * @param date 当前时间
 * @returns {number} 七天前时间戳
 */
function getLastSevenDay(date) {
    return parseInt(date.getTime()/1000) - 6*60*60*24;
}

/**
 * 获取七个月前时间戳（精确到秒）
 * @param date 当前时间
 * @returns {Number} 七个月前时间戳
 */
function getLastSevenMonth(date) {
    var monthNow = date.getMonth();
    var sevenMonth = monthNow - 6;
    if (sevenMonth < 0) {
        sevenMonth = monthNow + 12 -6;
    }
    date.setMonth(sevenMonth);
    return parseInt(date.getTime()/1000);
}

/**
 * 格式化字符串转时间
 * @param dateStr 格式化字符串
 * @returns {Date} 日期
 */
function getDateStampFromStr(dateStr) {
    var date = new Date(dateStr + " 00:00:00");
    return getTimeStamp(date);
}
