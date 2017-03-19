package com.android.ge.network.error;

import java.io.IOException;

/**
 * Created by xudengwang on 2016/12/19.
 */

public class ErrorException extends RuntimeException {
    private String message;

    public ErrorException(Throwable throwable, String errMsg) {
        super(throwable);

        this.message = errMsg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
