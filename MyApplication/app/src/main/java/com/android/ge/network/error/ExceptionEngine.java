package com.android.ge.network.error;

import com.alibaba.fastjson.JSONException;
import com.google.gson.JsonParseException;

import java.net.ConnectException;

import cz.msebera.android.httpclient.ParseException;
import retrofit2.adapter.rxjava.HttpException;

import static com.loopj.android.http.LogInterface.ERROR;

/**
 * Created by xudengwang on 16/11/15.
 */

public class ExceptionEngine {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ErrorCode.HTTP_ERROR);
            switch (httpException.code()) {
                case INTERNAL_SERVER_ERROR:
                    ex.message = "请求500错误";
                    break;
                case BAD_GATEWAY:
                    ex.message = "请求502错误";
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.message = "请求503错误";
                    break;
                case GATEWAY_TIMEOUT:
                    ex.message = "请求503错误";
                    break;
                case UNAUTHORIZED:
                    ex.message = "请求401错误";
                    break;
                case FORBIDDEN:
                    ex.message = "请求403错误";
                    break;
                case NOT_FOUND:
                    ex.message = "请求404错误";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "请求超时错误";
                    break;
                default:
                    ex.message = "网络错误";  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ErrorCode.PARSE_ERROR);
            ex.message = "解析错误";            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ErrorCode.NETWORD_ERROR);
            ex.message = "连接失败";  //均视为网络错误
            return ex;
        } else if (e instanceof ErrorException) {
            ex = new ApiException(e, ErrorCode.NETWORD_ERROR);
            ex.message = ((ErrorException) e).getMessage();  //均视为返回的error错误
            return ex;
        } else {
            ex = new ApiException(e, ErrorCode.UNKNOWN);
            ex.message = "网络错误";          //未知错误
            return ex;
        }
    }
}