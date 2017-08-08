package com.android.ge.ui.webview;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.web.AndroidBridge;
import com.android.ge.network.NetWorkConstant;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.utils.DeviceUtil;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;

/**
 * Created by xudengwang on 2016/6/24.
 * <p>
 * http://static.31academy.cn/module/history.html?path_id={path_id}&token={token}#/Learn
 * 路径id:path_id
 * token:token
 * <p>
 * <p>
 * <p>
 * 机构id:org_id
 * 课程id:course_id
 * token:token
 *
 * 考试H5
 */
public class ExamWebActivity extends CommonBaseActivity {

    @Bind(R.id.webview)
    WebView mWebView;

    private String LOAD_URL;

    //module/exam.html?isFinish=xx&examinationId=xxx&token=xxxx&entryId=xxx&entryType=xxx
    private static final String URL_PRE = NetWorkConstant.H5_URL+"/module/exam.html?";
    private String mParamExamId;
    private String mParamType;
    private String mParamTypeId;
    private String mParamIsFinish;

    @Override
    protected void initData() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mParamExamId = bundle.getString(CommonConstant.PARAM_EXAM_ID);
            mParamType = bundle.getString(CommonConstant.PARAM_ENTRY_TYPE);
            mParamTypeId = bundle.getString(CommonConstant.PARAM_ENTRY_ID);
            mParamIsFinish = bundle.getString(CommonConstant.PARAM_ISFINISH);
            LogUtils.d(getClass(), "mParamExamId:" + mParamExamId);
            RequestParams params = new RequestParams();
            params.put(CommonConstant.PARAM_ENTRY_ID, mParamTypeId);
            params.put(CommonConstant.PARAM_ENTRY_TYPE, mParamType);
            params.put(CommonConstant.PARAM_EXAM_ID, mParamExamId);
            params.put(CommonConstant.PARAM_ISFINISH, mParamIsFinish);
            params.put(CommonConstant.PARAM_TOKEN, Store.getToken());
            params.put(CommonConstant.PARAM_TIME, String.valueOf(System.currentTimeMillis()));
            params.put(CommonConstant.PARAM_LANGUAGE, DeviceUtil.localLanguageIsZh()? "zh ":"en");
            LogUtils.d(getClass(), "111map.string:" + params.toString());
            StringBuilder builder = new StringBuilder();
            builder.append(URL_PRE);
            builder.append(params.toString());
           // builder.append("#/Learn");
            LOAD_URL = builder.toString();

        } else {
            LOAD_URL = URL_PRE;
        }
        LogUtils.d(getClass(), "LOAD_URL_EXAM:" + LOAD_URL);


        mContext = this;
        WebSettings webSettings = mWebView.getSettings();
        // showLoadingDialog(null);
        webSettings.setJavaScriptEnabled(true);
        // 浏览器不支持多窗口显示
        webSettings.setSupportMultipleWindows(true);
        // 页面是否可以进行缩放
        webSettings.setSupportZoom(false);
        mWebView.addJavascriptInterface(new AndroidBridge(), "android");
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.d(getClass(), "protocol url:" + url);
                if (url.contains("backapp")) {
                    finish();
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        LogUtils.d(getClass(), "first protocol url:" + LOAD_URL);

        mWebView.loadUrl(LOAD_URL);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_common_web;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
