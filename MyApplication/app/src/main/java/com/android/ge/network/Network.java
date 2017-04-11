
package com.android.ge.network;

import android.text.TextUtils;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.constant.CommonConstant;
import com.android.ge.network.api.CourseApi;
import com.android.ge.network.error.ErrorCode;
import com.android.ge.network.error.ErrorException;
import com.android.ge.utils.JsonParseUtil;
import com.android.ge.utils.PreferencesUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Internal.logger;

public class Network {
    private static CourseApi courseApi;
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static CourseApi getCourseApi() {
        if (courseApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(genericClient())
                    .baseUrl(NetWorkConstant.BASE_URL)

                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            courseApi = retrofit.create(CourseApi.class);
        }
        return courseApi;
    }

    public static CourseApi getCourseApi(String apitag) {
        if (courseApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(genericClient())
                    .baseUrl(NetWorkConstant.BASE_URL)

                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            courseApi = retrofit.create(CourseApi.class);
        }
        LogUtils.d("接口类型：" + apitag + "-----------");
        return courseApi;
    }

//    private static Interceptor mTokenInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request originalRequest = chain.request();
//            String token = SharePrefUtils.getToken(MyApplication.context());
//            if (TextUtils.isEmpty(token)) {
//                return chain.proceed(originalRequest);
//            }
//            Request authorised = originalRequest.newBuilder()
//                    .header("Authorization", "Bearer " + token)
//                    .build();
//            return chain.proceed(authorised);
//        }
//    };

    private static OkHttpClient genericClient() {
        if (okHttpClient == null) {
            //okhttp3 提供的日志系统
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .addInterceptor(logging)
                    .addNetworkInterceptor(new LoggingInterceptor())
                    .build();
        }
        return okHttpClient;
    }
    /**
     * ClientID: 客户端id, 可采用友盟的 utdid

     Platform: (iphone/ipad/android)

     PlatformVersion: (6.4/9) 表示平台的版本如 iOS 9 这里填9

     Brand: (xiaomi/huawei/iphone…)

     ClientVersion: 客户端 build 版本号

     MID: 用户登录情况下传 Membership id

     Net: 网络类型 (2G/3G/4G/wifi/other) other为未知类型
     */
    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
  //          long t1 = System.nanoTime();
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = null;
            String token = PreferencesUtils.getUserData(Base.getContext(),PreferencesUtils.KEY_TOKEN);
            LogUtils.d(getClass(), "token:" + token);
            Request authorised = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token).build();
            int source1 = 0;
            Headers headers = authorised.headers();
            for(int buffer1 = headers.size(); source1 < buffer1; ++source1) {
                LogUtils.d("header:"+headers.name(source1) + ": " + headers.value(source1));
            }
            response = chain.proceed(authorised);
            if (response != null) {
                LogUtils.d(getClass(), "response.code()" + response.code()+",response.isSuccessful():"+response.isSuccessful());
                //
                if (!response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    long contentLength = responseBody.contentLength();
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        try {
                            charset = contentType.charset(Charset.forName("UTF-8"));
                        } catch (UnsupportedCharsetException e) {

                            return response;
                        }
                    }

//                    if (contentLength != 0) {
//                        String responseBobyStr = buffer.clone().readString(charset);
//                        LogUtils.d("LoggingInterceptor:" + responseBobyStr);
//                        String code = "";
//                        try {
//                            code = JsonParseUtil.jsonToString(responseBobyStr, CommonConstant.TAG_CODE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        LogUtils.d(getClass(),"code:"+code);
//                        if(!"0".equalsIgnoreCase(code)) {
//                            String error = ErrorCode.getErrorMessage(code);
//                            //主动抛出错误
//                            if (!TextUtils.isEmpty(error)) {
//                                throw new ErrorException(new Throwable(error),error);
//                            }
//                        }
//                    }
                }
            }


//            long t2 = System.nanoTime();
//            logger.info(String.format("Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//            logger.info(String.format("Received response result: %s",response.body().string()+""));
            return response;
        }
    }

}
