package com.weshare.thunder.except;

/**
 * Created by JiMeng on 2015/5/20.
 */
public class SuccessException extends RuntimeException {

    private int errorCode;
    private Object content;

    public SuccessException(String msg) {
        super(msg);
    }

    public SuccessException(int errorCode) {
        this.errorCode = errorCode;
    }

    public SuccessException() {
        super();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
