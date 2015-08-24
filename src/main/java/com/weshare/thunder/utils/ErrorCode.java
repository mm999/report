package com.weshare.thunder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.Assert;

import java.util.Locale;
import java.util.Objects;

/**
 * Created by lishaoyan on 2015/5/20.
 *
 * Error Code Rules
 *
 *  - an integer (>=0)
 *  - range:
 *              0  succes, no error
 *        1 - 999: system errors or general errors
 *    1000 - 1999: user's errors
 *    2000 - 2999: bankcard's errors
 *    3000 - 3999: transaction's errors
 */
public class ErrorCode {
    private static final Logger logger = LoggerFactory.getLogger(ErrorCode.class);

    // error message prefix
    private static final String ERR_MESSAGE_FORMAT = "error.%d";

    // message bundle source
    private static AbstractMessageSource messageSource = null;

    // system error
    public static final int SYS_SUCCESS = 0;
    public static final int SYS_INVALID_FILE = 1;
    public static final int SYS_UP_REQ_ISNULL = 2;

    public static final int SYS_MOBILE_VERIFY_FAIL=3;
    public static final int SYS_PARAMETER_ERROR = 4;
    public static final int SYS_SEND_VERIFY_CODE_ERROR = 5;
    public static final int SYS_JSON_FORMAT_ERROR = 6;
    public static final int SYS_TOKEN_INVALID = 7;


    // user error
    public static final int USER_BAD_USERNAME = 1000;
    public static final int USER_GID_INNULL = 1001;
    public static final int USER_HAS_NOBANKCARDS = 1002;
    public static final int USER_NOTACTIVITED = 1003;
    public static final int USER_NOTCERTIFIED = 1004;
    public static final int USER_NOTEXISTS = 1005;
    public static final int USER_ALREADYACTIVE = 1006;
    public static final int USER_STATUS_ABNORMAL = 1007;
    public static final int USER_INVALID_INVITATIONCODE = 1008;
    public static final int USER_HAS_NOMOBILE = 1009;
    public static final int USR_HAS_NO_DEPOSITCARD = 1010;
    public static final int USER_INVALID_PASSWORD = 1011;
    public static final int USER_BAD_OLD_PASSWORD = 1012;
    public static final int USER_MOBILE_ALREADY_USED = 1013;
    public static final int USER_INVALID_MOBILE = 1014;
    public static final int USER_FUND_NOTEXISTS = 1015;
    public static final int USER_NOT_ALLOWED = 1016;
    public static final int USER_PASSWORD_ERROR = 1017;
    public static final int USER_INVALID_IDCARD = 1018;
    public static final int USER_PASSWORD_LOCK = 1019;

    // card error
    public static final int CARD_INVALID_NUMBER = 2000;
    public static final int CARD_WRONG_BANK = 2001;

    public static final int CARD_UNKNOWNBANKCARD = 2002;
    public static final int CARD_BANKCARDLIMITISTEN = 2003;
    public static final int CARD_ADDDUPLICATECARD = 2004;
    public static final int CARD_USERNOTFOUND = 2005;
    public static final int CARD_IDCARDISNULL = 2006;
    public static final int CARD_INVAILIDIDCARD = 2007;
    public static final int CARD_IDCARDLENGTHWRONG = 2008;
    //public static final int CARD_SMSCODEVERIFYFAIL = 2009;
    public static final int CARD_IDCARDINVALID = 2010;
    public static final int CARD_IDVERIFY_NAMEIDCARDNOTMATCH = 2011;
    public static final int CARD_IDVERIFY_LESSTHANEIGHTEEN = 2012;
    public static final int CARD_IDVERIFY_UNKNOWNFAILURE = 2013;
    public static final int CARD_DEBITCARDCANNOTBEGUARANTEE = 2014;
    public static final int CARD_CREDITCARDCANNOTBEDEPOSIT = 2015;
    public static final int CARD_CARDNOTSUPPORTREPAYMENT = 2016;
    public static final int CARD_SHOULDBECREDITCARD = 2017;
    public static final int CARD_CARDVERIFYINPROCESS = 2018;
    public static final int CARD_BANKCARDINSERTFAILED = 2019;
    public static final int CARD_SHOULDBEDEBITCARD = 2020;
    public static final int CARD_SHOULDNOTBECREDITCARD = 2022;
    public static final int CARD_GETBANKCARDINFOFAILED = 2023;
    public static final int CARD_BANKCARDNOTSUPPORTED = 2024;
    public static final int CARD_DELETED_ALREADY = 2025;
    public static final int CARD_CARD_USER_NOT_MATCHED = 2026;
    public static final int CARD_DUPLICATEPHONENUM = 2027;
    public static final int CARD_NOTFOUND = 2028;
    public static final int CARD_HASFAILURE = 2029;
    public static final int CARD_HASINPROCESS = 2030;
    public static final int CARD_INPROCESS = 2031;
    public static final int CARD_ADDFAILURE = 2032;
    public static final int CARD_QUERY_TIMEOUT = 2033;
    public static final int CARD_USER_LIGHTNING_NOT_FOUND = 2034;
    public static final int CARD_USER_BASE_NOT_FOUND = 2035;
    public static final int CARD_CONFIG_BANK_NOT_FOUND = 2036;
    public static final int CARD_SUCCESS_BANK_CARD_NOT_FOUND = 2037;
    public static final int CARD_INPROCESS_BANK_CARD_NOT_FOUND = 2038;
    public static final int CARD_FAILURE_BANK_CARD_NOT_FOUND = 2039;
    public static final int CARD_NO_AVAILABLE_GID = 2040;
    public static final int CARD_IN_DEPOSIT_OCCUPY = 2041;
    public static final int CARD_IN_GUARANTEE_OCCUPY = 2042;
    public static final int CARD_IN_LOAN_OCCUPY = 2043;
    public static final int CARD_NEEDWITHDRAW = 2044;
    public static final int CARD_INDEPOSIT = 2045;
    public static final int CARD_STATUS_ABNORMAL = 2046;
    public static final int CARD_CARDNOTSUPPORTDEPOSIT = 2047;
    public static final int CARD_USER_CHANNEL_WECHAT_NOT_FOUND = 2048;
    public static final int CARD_CREDIT_NOTSUPPORT = 2049;
    public static final int CARD_IDVERIFY_DUPLICATEID = 2050;
    public static final int CARD_BANKCARDCANNOTBEFOUND = 2051;

    public static final int ASSETS_CAPITAlDEPOSITINOUT_NOTFOUND = 3001;
    public static final int ASSETS_USERFINANCIAL_NOTFOUND = 3002;
    public static final int ASSETS_BANKCARD_NOTFOUND = 3003;
    public static final int ASSETS_PARAERROR = 3004;
    public static final int ASSETS_USERSTATE_ABNORMAL = 3005;
    public static final int ASSETS_WITHDRAW_AMOUNTEXCEED = 3006;
    public static final int ASSETS_WITHDRAW_EXCEEDCARDLIMIT = 3007;
    public static final int ASSETS_EXCEEDCARDLIMIT = 3008;
    public static final int ASSETS_RECORDDEPOSIT_NOTFOUND = 3009;
    public static final int ASSETS_RECORDDEPOSIT_STATEERROR = 3010;
    public static final int ASSETS_SETTLEDEPOSITREC_ERROR = 3011;
//    public static final int ASSETS_PASSWORD_ERROR = 3012;
    public static final int ASSETS_TRANSWITHDRAW_NOTFOUND = 3013;
    public static final int ASSETS_WITHDRAW_UPDATEERROR = 3014;

    // deposit error
    public static final int DEPOSIT_CAPITAlDEPOSITINOUT_NOTFOUND = 4001;
    public static final int DEPOSIT_USERFINANCIAL_NOTFOUND = 4002;
    public static final int DEPOSIT_BANKCARD_NOTFOUND = 4003;
    public static final int DEPOSIT_WITHDRAW_PARAERROR = 4004;
    public static final int DEPOSIT_USERSTATE_ABNORMAL = 4005;
    public static final int DEPOSIT_WITHDRAW_AMOUNTEXCEED = 4006;
    public static final int DEPOSIT_WITHDRAW_EXCEEDCARDLIMIT = 4007;
    public static final int DEPOSIT_EXCEEDCARDLIMIT = 4008;
    public static final int DEPOSIT_RECORDDEPOSIT_NOTFOUND = 4009;
    public static final int DEPOSIT_RECORDDEPOSIT_STATEERROR = 4010;
    public static final int DEPOSIT_SETTLEDEPOSITREC_ERROR = 4011;
    public static final int DEPOSIT_PRODUCT_NOT_FOUND = 4012;
    public static final int DEPOSIT_NO_PASSWORD = 4013;
    public static final int DEPOSIT_CONFIG_PRODUCT_DISPLAY_ORDER_NOT_FOUND = 4014;
    public static final int DEPOSIT_CONFIG_VOUCHER_NOT_FOUND = 4015;
    public static final int DEPOSIT_VOUCHER_NOT_FOUND = 4016;
    public static final int DEPOSIT_HISTORY_FUND_DAILY_EARNING_NOT_FOUND = 4017;
//    public static final int DEPOSIT_PASSWORD_ERROR = 4018;
    public static final int DEPOSIT_FUND_INSUFFICENT = 4019;
    public static final int DEPOSIT_AMOUNT_LESS_THEN_CONDITION_ENTRANCE = 4020;
    public static final int DEPOSIT_AMOUNT_LESS_THEN_CONDITION_SINGLE_MIN = 4021;
    public static final int DEPOSIT_AMOUNT_GREAT_THEN_CONDITION_PERSON_MAX = 4022;
    public static final int DEPOSIT_AMOUNT_GREAT_THEN_CONDITION_MAX = 4023;
    public static final int DEPOSIT_FUND_TOTAL_AMOUNT_SHOULD_NOT_BE_QUERIED_BY_PRODUCT_ID = 4024;
    public static final int DEPOSIT_EVENT_NOT_FOUND = 4025;

    // statistic error
//    public static final int STATISTIC_ = 4001;

    /**
     * get the error message of the specified error code
     * @param errorCode the error code
     * @param locale  the locale of error message
     * @return the error messsage
     */
    public static String getErrorMessage(int errorCode, Objects[] objects, Locale locale) {
        Assert.isTrue(errorCode >= 0, "error code must great than zero");

        if (messageSource == null) {
            logger.warn("can't get message because message bundle source is null");
            return String.format("ErrorCode = %d. Message Source isn't ready.", errorCode);
        }

        Locale lc = (locale == null) ? Locale.CHINA : locale;
        String message = messageSource.getMessage(String.format(ERR_MESSAGE_FORMAT, errorCode), objects, lc);

        if (message == null) {
            message = String.format("ErrorCode = %d. Message resource isn't ready.", errorCode);
        }

        return message;
    }

    /**
     *  get the error message of the specified error code
     * @param errorCode the error code
     * @return the error message with China Locale
     */
    public static String getErrorMessage(int errorCode) {
        return getErrorMessage(errorCode, null, null);
    }

    /**
     *  decide if the message bundle source is set
     * @return true if the message bunldes source is set, or false
     */
    public static boolean isMessageSourceReady() {
        return messageSource != null;
    }

    /**
     * set the message source bundle. it should be set before getMessage is invoked.
     * @param messageSource the message bundle source
     */
    public static void setMessageSource(AbstractMessageSource messageSource) {
        ErrorCode.messageSource = messageSource;
    }
}
