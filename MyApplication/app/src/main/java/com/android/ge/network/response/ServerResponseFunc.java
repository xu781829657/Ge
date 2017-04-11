package com.android.ge.network.response;

import com.android.ge.model.base.BaseResponse;
import com.android.ge.network.error.ErrorCode;
import com.android.ge.network.error.ServerException;

import rx.functions.Func1;

//拦截固定格式的公共数据类型Response<T>,判断里面的状态码
public class ServerResponseFunc<T> implements Func1<BaseResponse<T>, T> {
    @Override
    public T call(BaseResponse<T> response) {
        //对返回码进行判断，如果不是0，则证明服务器端返回错误信息了，便根据跟服务器约定好的错误码去解析异常
        if (!response.code.equals("0")) {
            //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new ServerException(ErrorCode.getErrorMessage(response.code), Integer.valueOf(response.code));
        }
        //服务器请求数据成功，返回里面的数据实体
        return response.data;
    }
}