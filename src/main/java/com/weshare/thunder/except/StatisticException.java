package com.weshare.thunder.except;

/**
 * Created by JiMeng on 2015/5/20.
 */
public class StatisticException extends RuntimeException {

    private int errorCode;

    public StatisticException(String msg) {
        super(msg);
    }

    public StatisticException(int errorCode) {
        this.errorCode = errorCode;
    }

    public StatisticException() {
        super();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
