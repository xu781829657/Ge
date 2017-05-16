package com.android.ge.network.error;

/**
 * Created by xudengwang on 16/11/15.
 */

public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }
    public ApiException(Throwable throwable,String message) {
        super(throwable);
        this.message = message;
    }
}