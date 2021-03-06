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
import com.android.ge.controller.Session;
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
 */
public class NewsWebActivity extends CommonBaseActivity {


    @Bind(R.id.webview)
    WebView mWebView;

    //url?organization_id=20&id=18&access_token=Bearer%207d6095b557c65a969af3d821fc7954c3d3ab2bb2f634f0780476c6b738a9fd5f
    private String LOAD_URL;


    //module/news.html?newsId=xxx&token=xxxx
    private static final String URL_PRE = NetWorkConstant.H5_URL+"/module/news.html?";
    private String mParamNewsId;

    @Override
    protected void initData() {
        //LogUtils.d("new AndroidBridge().getNetWorkType():"+new AndroidBridge().getNetWorkType());
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mParamNewsId = bundle.getString(CommonConstant.PARAM_NEWS_ID);
            LogUtils.d(getClass(),"mParamNewsId:"+mParamNewsId);
            RequestParams params = new RequestParams();
            params.put(CommonConstant.PARAM_NEWS_ID, mParamNewsId);
            //params.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
            params.put(CommonConstant.PARAM_TOKEN, Store.getToken());
            //params.put(CommonConstant.PARAM_TIME, String.valueOf(System.currentTimeMillis()));
            params.put(CommonConstant.PARAM_LANGUAGE, DeviceUtil.localLanguageIsZh()? "zh":"en");
            LogUtils.d(getClass(), "111map.string:" + params.toString());
            StringBuilder builder = new StringBuilder();
            builder.append(URL_PRE);
            builder.append(params.toString());
           // builder.append("#/Detail");
            LOAD_URL = builder.toString();
        } else {
            LOAD_URL = URL_PRE;
        }
        LogUtils.d(getClass(), "LOAD_URL:" + LOAD_URL);


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
        //webview同步cookie缓存
        boolean syncCookie = Session.syncCookie(LOAD_URL,Store.getToken());
        LogUtils.d("syncCookie:"+syncCookie);
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
