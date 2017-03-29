package com.android.ge.network.error;

/**
 * Created by xudengwang on 16/11/15.
 */

public class ErrorCode {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;


    public static String getErrorMessage(String codestr) {
        Integer code = Integer.valueOf(codestr);

        switch (code){
            case 1:
                return "服务不可用";
            case 12:
                return "提供的凭证无效";
            case 43:
                return "系统处理出错";
            case 60:
                return "参数不合法";

            default:
                return "未知错误";
        }

    }
}
