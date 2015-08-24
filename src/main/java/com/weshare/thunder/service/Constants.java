package com.weshare.thunder.service;

/**
 * Created by JiMeng on 2015/5/7.
 */
public interface Constants {
    public static int BOUND_BANK_CARD_LIMIT = 10;
    public static int SECONDS_OF_ONE_DAY = 24 * 60 * 60;
    public static int SECONDS_OF_ONE_HOUR = 60 * 60;

    public interface CardSubType {
        public static byte CARD_SUB_TYPE_WITHDRAW = 0; //授信提现卡
        public static byte CARD_SUB_TYPE_DEPOSIT = 1; //理财卡
        public static byte CARD_SUB_TYPE_GUARANTEE = 2; //担保卡
    }

    public interface CardType {
        public static byte CARD_TYPE_DEBIT = 0; //借记卡
        public static byte CARD_TYPE_CREDIT = 1; //信用卡
        public static byte CARD_TYPE_UNKNOWN = 2; //未知卡类型
    }

    public interface TransStatus {
        public static byte INPROCESS = 0;
        public static byte SUCCESS = 1;
        public static byte FAILURE = 2;
    }

    public interface CardStatus {
        public static byte INPROCESS = 0;
        public static byte SUCCESS = 1;
        public static byte FAILURE = 2;
        public static byte DELETED = 3;
    }

    public interface DeductionPreAuthMode {
        public static byte NORMAL = 0; //0:正常扣款（不采用预授权）
        public static byte PRE_AUTH_COMPLETE = 1; //1:预授权完成扣款（借款担保时的预授权完成）
        public static byte PRE_AUTH = 2; //2:预授权扣款（强制扣款时，发起预授权，然后完成）
    }

    public interface WithdrawType {
        public static byte  LOAN = 0; //0:借款提现
        public static byte  DEPOSIT = 1; //1:理财提现
        public static byte  VERIFICATION_ONE_CENT = 2; //2:校验1分钱打款
    }

    public interface DeductionType {
        public static byte DEPOSIT = 0; //0:理财扣款
        public static byte REPAYMENT_INITIATIVE = 1; //1：主动还款
        public static byte REPAYMENT_EXPIRATION = 2; //2：到期扣款
        public static byte OVERDUE = 3; //3：逾期扣款
        public static byte FORCE = 4; //4：强制扣款
        public static byte VERIFICATION_ZERO = 5; //5：校验0金额扣款
        public static byte VERIFICATION_ONE_CENT = 6; //6：校验1分钱扣款
        public static byte BUY_VIP = 7; //7：购买会员扣款
        public static byte RISK_CONTROL = 8; //8：风控临时扣款
    }

    public interface ResponseStatus {
        public static byte SUCCESS = 0;
        public static byte FAILURE = 1;
    }

    public interface UserState {
        public static byte NORMAL = 0; //正常
        public static byte FROZEN = 1; //冻结
        public static byte UNBOUND = 2; //解绑
        public static byte LOGOUT = 3; //注销
    }

    public interface IdCertifyResultCode {
        public static String  SUCCESS = "0000"; //sf00.一致
        public static String  FAILURE_NOT_IDENTICAL = "0002"; //sf01.不一致
        public static String  FAILURE_LESS_THAN_18 = "0003"; //sf02.不满18岁
        public static String  FAILURE_UNKNOWN_OR_BAD_PARAMETER = "0004"; //sf03.未知错误（或参数不正确）
        public static String  FAILURE_OTHER = "0005"; //sf04.验证位验证不通过
    }

    public interface DepositProductType {
        public static byte FUND = 0;
        public static byte FIX_TERM = 1;
        public static byte ACTIVITY = 2;
    }

    public interface DepositProductSetType {
        public static byte PRODUCT = 0;
        public static byte GROUP = 1;
    }

    public static int PRODUCT_ID_FUND = 1;

    public interface DepositState {
        public static byte DEPOSITING = 1; //1:理财中
        public static byte DEPOSIT_DONE = 2; //2:理财完成
        public static byte DEDUCTING = 3; //3:扣款中
        public static byte DEDUCTE_FAILED = 4; //4:扣款失败
    }

    public interface DepositProductValidState {
        public static boolean INVALID = false;
        public static boolean VALID = true;
    }

    public interface DepositSource {
        public static byte BANKCARD = 0; //0:来自银行卡
        public static byte FUND = 1; //1：来自闪电宝
    }

    public interface DepositType {
        public static byte FIRST_TIME = 0; //0:首次理财
        public static byte AUTO_CONTINUE = 1; //1:自动续投
        public static byte MANUAL_CONTINUE = 1; //2:手动再次理财
    }

    public  interface  CornerMarkType {
        public static byte EMPTY = 0;
        public static byte HOT = 1;
        public static byte NEW_USER = 2;
        public static byte GROUP_PURCHASE = 3;
        public static byte IN_PROGRESS = 4;
        public static byte FINISHED = 5;
        public static byte USED = 6;
        public static byte OVERTIME = 7;
    }

    public interface  UserPurchaseType {
        public static byte TRADE_AVERAGE = 1;
        public static byte TRADE_TOTAL = 2;
        public static byte TRADE_PERCENT = 3;
    }

    public interface  UserCountType {
        public static byte ONCE = 1;
        public static byte TWICE = 2;
        public static byte THREE = 3;
        public static byte FOUR = 4;
        public static byte FIVE_AND_MORE = 5;
    }

    public final static int CONFIG_GLOBAL_ID = 1; // the id of config_global table.

    public final static String SYS_INVITOR = "0"; // user is actived by system.

    public final static boolean USER_MIGRATION_ENABLE = true;

}
