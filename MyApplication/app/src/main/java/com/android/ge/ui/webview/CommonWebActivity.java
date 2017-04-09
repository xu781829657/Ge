package com.android.ge.ui.webview;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;


import butterknife.Bind;

/**
 * Created by xudengwang on 2016/6/24.
 */
public class CommonWebActivity extends CommonBaseActivity {


    @Bind(R.id.webview)
    WebView mWebView;

    //url?organization_id=20&id=18&access_token=Bearer%207d6095b557c65a969af3d821fc7954c3d3ab2bb2f634f0780476c6b738a9fd5f
    private String LOAD_URL;
    private String mUrl;
    private String mId;
    private static final String URL_PRE = "http://survey-staging.60kou.com/index.html";

    @Override
    protected void initData() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mUrl = bundle.getString("url");
            mId = bundle.getString("id");
            LogUtils.d(getClass(), "mUrl:" + mUrl + ",mid:" + mId);
            if (TextUtils.isEmpty(mUrl)) {
                mUrl = URL_PRE;
            }

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

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.d(getClass(), "protocol url:" + url);
                if (url.contains("back")) {
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


}
