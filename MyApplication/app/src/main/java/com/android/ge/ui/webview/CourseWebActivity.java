package com.android.ge.ui.webview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.web.AndroidBridge;
import com.android.ge.ui.base.CommonBaseActivity;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

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
public class CourseWebActivity extends CommonBaseActivity {


    @Bind(R.id.webview)
    WebView mWebView;

    //url?organization_id=20&id=18&access_token=Bearer%207d6095b557c65a969af3d821fc7954c3d3ab2bb2f634f0780476c6b738a9fd5f
    private String LOAD_URL;


    //module/index.html?org_id=xxx&course_id=xxxx&token=xxxx&entryId=xxxx&entryType=xxx
    private static final String URL_PRE = "http://static.31academy.cn/module/index.html?";

    //
    private static final String FORMAT_COURSE_PARAM = "?path_id={%1$s}&token={%1$s}";

    private String mParamCourseId;
    private String mParamType;
    private String mParamTypeId;

    /** 视频全屏参数 */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @Override
    protected void initData() {
        //LogUtils.d("new AndroidBridge().getNetWorkType():"+new AndroidBridge().getNetWorkType());
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mParamCourseId = bundle.getString(CommonConstant.PARAM_COURSE_ID);
            mParamType = bundle.getString(CommonConstant.PARAM_ENTRY_TYPE);
            mParamTypeId = bundle.getString(CommonConstant.PARAM_ENTRY_ID);
            LogUtils.d(getClass(), "mParamCourseId:" + mParamCourseId);
            RequestParams params = new RequestParams();
            params.put(CommonConstant.PARAM_ENTRY_TYPE, mParamType);
            params.put(CommonConstant.PARAM_ENTRY_ID, mParamTypeId);
            params.put(CommonConstant.PARAM_COURSE_ID, mParamCourseId);
            params.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
            params.put(CommonConstant.PARAM_TOKEN, Store.getToken());
            params.put(CommonConstant.PARAM_TIME, String.valueOf(System.currentTimeMillis()));
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
        initWebView();
//        WebSettings webSettings = mWebView.getSettings();
//        // showLoadingDialog(null);
//        webSettings.setJavaScriptEnabled(true);
//        // 浏览器不支持多窗口显示
//        webSettings.setSupportMultipleWindows(true);
//        // 页面是否可以进行缩放
//        webSettings.setSupportZoom(false);
//        mWebView.addJavascriptInterface(new AndroidBridge(), "android");
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtils.d(getClass(), "protocol url:" + url);
//                if (url.contains("backapp")) {
//                    finish();
//                } else {
//                    view.loadUrl(url);
//                }
//                return true;
//            }
//        });
//
//        LogUtils.d(getClass(), "first protocol url:" + LOAD_URL);
//
//        mWebView.loadUrl(LOAD_URL);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.reload();
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


    /** 展示网页界面 **/

    public void initWebView() {
        WebChromeClient wvcc = new WebChromeClient();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 加载缓存内容
        //mWebView.addJavascriptInterface(new AndroidBridge(), "android");
        mWebView.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
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
        };
        mWebView.setWebViewClient(wvc);

        mWebView.setWebChromeClient(new WebChromeClient() {

            /*** 视频播放相关的方法 **/

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(CourseWebActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });

        // 加载Web地址
        mWebView.loadUrl(LOAD_URL);
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        CourseWebActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(CourseWebActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        mWebView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


}
