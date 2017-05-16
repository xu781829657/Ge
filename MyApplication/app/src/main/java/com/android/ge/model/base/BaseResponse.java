package com.android.ge.model.base;

/**
 * Created by xudengwang on 2017/4/11.
 */

public class BaseResponse<T> {
    public String code;
    public String result;
    public String message;
    public T data;
}