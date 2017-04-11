package com.android.ge.network.error;

/**
 * Created by xudengwang on 16/11/15.
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}