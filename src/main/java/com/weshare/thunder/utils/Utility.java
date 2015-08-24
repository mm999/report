package com.weshare.thunder.utils;

import com.weshare.thunder.model.LightningResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/5/14.
 */
public final class Utility {
    private final static Logger logger = LoggerFactory.getLogger(Utility.class);


    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * format time to "yyyy-MM-dd"
     * @param time
     * @return
     */
    public static String formatTime(int time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(time);
    }

    /**
     * get the day end time by given the time point
     * e.g. given timePoint is "2015.5.13 13:45:32", the returned day end time is "2015.5.13 23:59:59"
     * @param timePoint
     * @return
     */
    public static int getDayEndTime(int timePoint){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        long theTime = ((long)timePoint)*1000;
        calendar.setTimeInMillis(theTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.clear();
        calendar.set(year, month, day, 23, 59, 59);
        int ret = (int)(calendar.getTimeInMillis()/1000);
        return ret;
    }

    public static int getDayStartTime(int timePoint) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        long theTime = ((long)timePoint)*1000;
        calendar.setTimeInMillis(theTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.clear();
        calendar.set(year, month, day, 0, 0, 0);
        int ret = (int)(calendar.getTimeInMillis()/1000);
        /*
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String curday = sf.format(System.currentTimeMillis());
        int ret = timePoint;
        try {
            ret = (int)((sf.parse(curday).getTime())/1000);
        } catch(ParseException ex) {
            ex.printStackTrace();
        }
        */
        return ret;
    }

    public static int getLastHourEndTime(int timePoint) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH");
        String curHour = sf.format(System.currentTimeMillis());
        int ret = timePoint;
        try {
            ret = (int)((sf.parse(curHour).getTime())/1000);
        } catch(ParseException ex) {
            ex.printStackTrace();
        }
        return ret - 1;
    }

//    public static LightningResponse getErrorResponse(int status) {
//        LightningResponse res = new LightningResponse();
//        res.setStatus(status);
//        switch (status)
//        {
//            case -1:
//            {
//                res.setMessage("user doesn't exist");
//                break;
//            }
//            case -2:
//            {
//                res.setMessage("parameter error");
//                break;
//            }
//            case -1000:
//            {
//                res.setMessage("request is null");
//                break;
//            }
//            case -1001:
//            {
//                res.setMessage("user is already activited");
//                break;
//            }
//            case -1002:
//            {
//                res.setMessage("wrong invitation code");
//                break;
//            }
//            case -1003:
//            {
//                res.setMessage("user is not in normal state");
//                break;
//            }
//            case -1004:
//            {
//                res.setMessage("send mobile verification error");
//                break;
//            }
//            case -1005:
//            {
//                res.setMessage("Mobile no is already used");
//                break;
//            }
//            case -1006:
//            {
//                res.setMessage("Verify code is incorrect");
//                break;
//            }
//            default:
//            {
//                res.setMessage("system error");
//                break;
//            }
//        }
//        return res;
//    }


    public static LightningResponse getErrorResponse(int status, ReloadableResourceBundleMessageSource messageSource) {
        return getErrorResponse(status, null, messageSource);
    }

    public static LightningResponse getErrorResponse(int status, Object[] args, ReloadableResourceBundleMessageSource messageSource) {
        LightningResponse res = new LightningResponse();
        res.setStatus(status);
        switch (status)
        {

            case ErrorCode.USER_PASSWORD_LOCK:
            {
                res.setMessage(messageSource.getMessage("response.failure.userpasswordlock", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_INVALID_IDCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.useridcardinvalid", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_NOT_ALLOWED:
            {
                res.setMessage(messageSource.getMessage("response.failure.usernotallowed", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.SYS_TOKEN_INVALID:
            {
                res.setMessage(messageSource.getMessage("response.failure.tokeninvalid", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.SYS_JSON_FORMAT_ERROR:
            {
                res.setMessage(messageSource.getMessage("response.failure.jsonformaterror", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_MOBILE_ALREADY_USED:
            {
                res.setMessage(messageSource.getMessage("response.failure.usermobilealreadyused", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USR_HAS_NO_DEPOSITCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.userhasnodepositcard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_EXCEEDCARDLIMIT:
            {
                res.setMessage(messageSource.getMessage("response.failure.depositexceedcardlimit", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CARDNOTSUPPORTDEPOSIT:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardnotsupportdeposit", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_BANKCARDCANNOTBEFOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.bankcardcannotbefound", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_STATUS_ABNORMAL:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardstatusabnormal", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_HAS_NOMOBILE:
            {
                res.setMessage(messageSource.getMessage("response.failure.userhasnomobile", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.SYS_SEND_VERIFY_CODE_ERROR:
            {
                res.setMessage(messageSource.getMessage("response.failure.syssendverifycodeerror", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.SYS_PARAMETER_ERROR:
            {
                res.setMessage(messageSource.getMessage("response.failure.sysparamerror", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.SYS_MOBILE_VERIFY_FAIL:
            {
                res.setMessage(messageSource.getMessage("response.failure.sysmobileverifyfail", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_INVALID_INVITATIONCODE:
            {
                res.setMessage(messageSource.getMessage("response.failure.userinvalidinvitationcode", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_STATUS_ABNORMAL:
            {
                res.setMessage(messageSource.getMessage("response.failure.userstatusabnormal", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_ALREADYACTIVE:
            {
                res.setMessage(messageSource.getMessage("response.failure.useralreadyactive", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_NOTEXISTS: {
                res.setMessage(messageSource.getMessage("response.failure.usernotexists", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_NOTACTIVITED:
            {
                res.setMessage(messageSource.getMessage("response.failure.usernotactivated", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_NOTCERTIFIED: {
                res.setMessage(messageSource.getMessage("response.failure.usernotcertified", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.SYS_UP_REQ_ISNULL:
            {
                res.setMessage(messageSource.getMessage("response.failure.reqisnull",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_GID_INNULL:
            {
                res.setMessage(messageSource.getMessage("response.failure.usergidisnull",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_HAS_NOBANKCARDS:
            {
                res.setMessage(messageSource.getMessage("response.failure.userhasnobankcards",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_INVALID_PASSWORD:
            {
                res.setMessage(messageSource.getMessage("response.failure.invalidpassword",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_BAD_OLD_PASSWORD:
            {
                res.setMessage(messageSource.getMessage("response.failure.badoldpassword",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_PASSWORD_ERROR:
            {
                res.setMessage(messageSource.getMessage("response.failure.password_error", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_INVALID_MOBILE:
            {
                res.setMessage(messageSource.getMessage("response.failure.invalidmobile",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.USER_FUND_NOTEXISTS:
            {
                res.setMessage(messageSource.getMessage("response.failure.invalidfundtotal",args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_UNKNOWNBANKCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.unknownbankcard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_BANKCARDLIMITISTEN:
            {
                res.setMessage(messageSource.getMessage("response.failure.bankcardlimitisten", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_ADDDUPLICATECARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.addduplicatecard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDCARDISNULL:
            {
                res.setMessage(messageSource.getMessage("response.failure.idcardisnull", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_INVAILIDIDCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.invailididcard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDCARDLENGTHWRONG:
            {
                res.setMessage(messageSource.getMessage("response.failure.idcardlengthwrong", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDCARDINVALID:
            {
                res.setMessage(messageSource.getMessage("response.failure.idcardinvalid", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDVERIFY_NAMEIDCARDNOTMATCH:
            {
                res.setMessage(messageSource.getMessage("response.failure.idverify.nameidcardnotmatch", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDVERIFY_LESSTHANEIGHTEEN:
            {
                res.setMessage(messageSource.getMessage("response.failure.idverify.lessthaneighteen", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDVERIFY_DUPLICATEID:
            {
                res.setMessage(messageSource.getMessage("response.failure.idverify.duplicateid", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IDVERIFY_UNKNOWNFAILURE:
            {
                res.setMessage(messageSource.getMessage("response.failure.idverify.unknownfailure", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_DEBITCARDCANNOTBEGUARANTEE:
            {
                res.setMessage(messageSource.getMessage("response.failure.debitcardcannotbeguarantee", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CREDITCARDCANNOTBEDEPOSIT:
            {
                res.setMessage(messageSource.getMessage("response.failure.creditcardcannotbedeposit", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CARDNOTSUPPORTREPAYMENT:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardnotsupportrepayment", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_SHOULDBECREDITCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.shouldbecreditcard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CARDVERIFYINPROCESS:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardverifyinprocess", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_BANKCARDINSERTFAILED:
            {
                res.setMessage(messageSource.getMessage("response.failure.bankcardinsertfailed", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_SHOULDBEDEBITCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.shouldbedebitcard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_SHOULDNOTBECREDITCARD:
            {
                res.setMessage(messageSource.getMessage("response.failure.shouldnotbecreditcard", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_GETBANKCARDINFOFAILED:
            {
                res.setMessage(messageSource.getMessage("response.failure.getbankcardinfofailed", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_BANKCARDNOTSUPPORTED:
            {
                res.setMessage(messageSource.getMessage("response.failure.bankcardnotsupported", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CARD_USER_NOT_MATCHED:
            {
                res.setMessage(messageSource.getMessage("response.failure.invalidusername", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_DUPLICATEPHONENUM:
            {
                res.setMessage(messageSource.getMessage("response.failure.duplicatephoneno", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_NOTFOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardnotfound", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CREDIT_NOTSUPPORT:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardcreditnotsupport", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_HASINPROCESS:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardhasinprocess", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_HASFAILURE:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardhasfailure", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_ADDFAILURE:
            {
                res.setMessage(messageSource.getMessage("response.failure.addfailure", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_INPROCESS:
            {
                res.setMessage(messageSource.getMessage("response.failure.inprocess", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_QUERY_TIMEOUT:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardquerytimeout", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IN_DEPOSIT_OCCUPY:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardindeposit", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IN_GUARANTEE_OCCUPY:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardinguarantee", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_IN_LOAN_OCCUPY:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardinloan", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_NEEDWITHDRAW:
            {
                res.setMessage(messageSource.getMessage("response.failure.cardneedwithdraw", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_DELETED_ALREADY:
            {
                res.setMessage(messageSource.getMessage("response.failure.carddeletedalready", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_PARAERROR:
            {
                res.setMessage(messageSource.getMessage("response.failure.withdrawparaerror", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_USERSTATE_ABNORMAL:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_userstateerror", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_WITHDRAW_AMOUNTEXCEED:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_withdrawamountexceed", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_WITHDRAW_EXCEEDCARDLIMIT:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_withdrawexceedcardlimit", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_USER_CHANNEL_WECHAT_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_user_channel_wechat_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_USER_LIGHTNING_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_user_lightning_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_USER_BASE_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_user_base_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_CONFIG_BANK_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_config_bank_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_SUCCESS_BANK_CARD_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_success_bank_card_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_INPROCESS_BANK_CARD_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_inprocess_bank_card_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_FAILURE_BANK_CARD_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_failure_bank_card_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.CARD_NO_AVAILABLE_GID:
            {
                res.setMessage(messageSource.getMessage("response.failure.card_no_available_gid", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_BANKCARD_NOTFOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_bankcard_notfound", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_USERFINANCIAL_NOTFOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.assets_userfinancial_notfound", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_TRANSWITHDRAW_NOTFOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.assets_transwithdraw_notfound", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_PRODUCT_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_deposit_product_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_NO_PASSWORD:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_no_password", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_CONFIG_PRODUCT_DISPLAY_ORDER_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_config_product_display_order_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_CONFIG_VOUCHER_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_config_voucher_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_VOUCHER_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_voucher_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_HISTORY_FUND_DAILY_EARNING_NOT_FOUND: {
                res.setMessage(messageSource.getMessage("response.failure.deposit_history_fund_daily_earning_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_FUND_INSUFFICENT:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_fund_insufficent", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_USERFINANCIAL_NOTFOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_userfinancial_notfound", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_AMOUNT_LESS_THEN_CONDITION_ENTRANCE:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_amount_less_then_condition_entrance", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_AMOUNT_LESS_THEN_CONDITION_SINGLE_MIN:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_amount_less_then_condition_single_min", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_AMOUNT_GREAT_THEN_CONDITION_PERSON_MAX:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_amount_great_then_condition_person_max", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_AMOUNT_GREAT_THEN_CONDITION_MAX:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_amount_great_then_condition_max", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_FUND_TOTAL_AMOUNT_SHOULD_NOT_BE_QUERIED_BY_PRODUCT_ID:
            {
                res.setMessage(messageSource.getMessage("response.failure.fund_total_amount_should_not_be_queried_by_product_id", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.DEPOSIT_EVENT_NOT_FOUND:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_event_not_found", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            case ErrorCode.ASSETS_WITHDRAW_UPDATEERROR:
            {
                res.setMessage(messageSource.getMessage("response.failure.deposit_update_error", args, Locale.SIMPLIFIED_CHINESE));
                break;
            }
            default:
            {
                res.setMessage("system error");
                break;
            }
        }
        return res;
    }

    /**
     * generate a formated OrderID as "SD201505221814235", displayed in wechat/client UI
     * @param
     * @return orderID
     */
    public static String newOrderId() {
        return "SD" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    /**
     * Encrypt plain text
     * @param plainText
     * @return encrypted string for cvn2
     */
    public static String encryptText(String plainText) {

        return plainText;
    }

    /**
     * Decrypt to plain text
     * @param decryptedText
     * @return encrypted string for cvn2
     */
    public static String decryptedText(String decryptedText) {

        return decryptedText;
    }



    /**
     * Mask the bankcard Number, e.g. 18911113927 to 189******3927
     * @param bankCardNo
     * @return masked phone number
     */
    public static String maskBankCard(String bankCardNo) {
        if(bankCardNo.length() <= 8) {
            return bankCardNo;
        }
        return bankCardNo.substring(0,4) + "******" + bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
    }

    /**
     * Mask the user name, e.g. 张小�???to **�???
     * @param userName
     * @return masked phone number
     */
    public static String maskUserName(String userName) {
        switch (userName.length()) {
            case 0:
            case 1:
                return "*";
            default:
                return userName.replaceAll(".", "*").replaceFirst(".$",userName.substring(userName.length() - 1));
        }
    }

    public static String maskPhoneNum(String phoneNum) {
        if(phoneNum==null || phoneNum.length() < 4) {
            return phoneNum;
        }
        return phoneNum.substring(0,3) + "****" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
    }

    /**
     * Convert current time into int type. used for create_time/update time
     * @param
     * @return timestamp
     */
    public static int getCurrentTimeStamp() {
        if(debugCurrentTimeStamp == 0) {
            return (int) (System.currentTimeMillis() / 1000);
        }else {
            return debugCurrentTimeStamp;
        }
    }

    private static int debugCurrentTimeStamp = 0;

    /**
     * For test purpose, getCurrentTimeStamp will return the timestamp if non-zero.
     * @param
     * @return timestamp
     */
    public static void setDebugCurrentTimeStamp(int timeStamp) {
        debugCurrentTimeStamp = timeStamp;
    }


    /**
     * generate a LightningResponse with status 0, content will be converted to json
     * @param msg
     * @param content
     * @return
     */
    public static LightningResponse getRightResponse(String msg, Object content){
        LightningResponse res = new LightningResponse();
        res.setStatus(0);
        res.setMessage(msg);
        res.setContent(content);
//        if(content != null) {
//            Gson gson = new Gson();
//            res.setContent(gson.toJson(content));
//        } else{
//            res.setContent("");
//        }
        return res;
    }

    public static String generateInvitationCode(Set<String> existingCode){
        int invitationCode = (int)((Math.random()*9+1)*100000);
        if(existingCode != null){
            while(existingCode.contains(String.valueOf(invitationCode))){
                invitationCode++;
                if(invitationCode>=999999){
                    invitationCode = (int)((Math.random()*9+1)*100000);
                }
            }
        }
        return String.valueOf(invitationCode);
    }

    public static String generateOrderId(String gid) {
        if (null == gid) {
            return "";
        }
        return "SDLC" + gid.replaceAll("-", "");
    }

    public static String getDataFormatString(int currentSec) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(((long)currentSec)*1000);
    }

    public static String convertDecimal2PercentRate(BigDecimal rate) {
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(1);
        return percent.format(rate.setScale(3, BigDecimal.ROUND_DOWN));
    }
}
