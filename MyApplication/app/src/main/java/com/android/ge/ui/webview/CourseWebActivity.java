package com.android.ge.ui.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.constant.ThirdSDKConstant;
import com.android.ge.controller.Session;
import com.android.ge.controller.Store;
import com.android.ge.model.share.WxShareInfo;
import com.android.ge.network.NetWorkConstant;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.utils.DeviceUtil;
import com.android.ge.utils.ui.DialogUtils;
import com.loopj.android.http.RequestParams;
import com.mob.MobSDK;

import java.net.URLDecoder;
import java.util.HashMap;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

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
    private String LOAD_URL;

    //module/index.html?org_id=xxx&course_id=xxxx&token=xxxx&entryId=xxxx&entryType=xxx
//    private static final String URL_PRE = "http://static.31academy.cn/module/index.html?";
    private static final String URL_PRE_COURSE = NetWorkConstant.H5_URL + "/module/index.html?";
    private static final String URL_PRE_PATH = NetWorkConstant.H5_URL + "/module/history.html?";

    private String mParamCourseId;
    private String mParamType;
    private String mParamTypeId;
    private String mParamCx;
    private int mH5Type;//type = 0课程,1路径

    private String COURSE_SHARE_URL = "http://mobile.goelite.cn/share/course/{id}?type=wechat";

    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup
            .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;


    private int SHARE_TYPE = 1;
    private static final int SHARE_TYPE_WECHAT = 1;
    private static final int SHARE_TYPE_WECHAT_MOMENTS = 2;
    private String mParamPathId;


    private WxShareInfo mShareInfo;

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissLoadingDialog();
            switch (msg.what) {
                case 1:
                    if (SHARE_TYPE == SHARE_TYPE_WECHAT) {
                        Base.showToast("微信好友分享成功");
                    } else {
                        Base.showToast("微信朋友圈分享成功");
                    }
                    break;
                case 2:
                    if (SHARE_TYPE == SHARE_TYPE_WECHAT) {
                        Base.showToast("微信好友分享失败" + (String) msg.obj);
                    } else {
                        Base.showToast("微信朋友圈分享失败" + (String) msg.obj);
                    }
                    break;
                case 3:
                    Base.showToast("分享操作取消!");
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        MobSDK.init(this, ThirdSDKConstant.MOB_APP_KEY, ThirdSDKConstant.MOB_APP_SECRET);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mParamPathId = bundle.getString(CommonConstant.PARAM_PATH_ID);
            mParamCourseId = bundle.getString(CommonConstant.PARAM_COURSE_ID);
            mParamType = bundle.getString(CommonConstant.PARAM_ENTRY_TYPE);
            mParamTypeId = bundle.getString(CommonConstant.PARAM_ENTRY_ID);
            mParamCx = bundle.getString(CommonConstant.PARAM_CX);
            LogUtils.d(getClass(), "mParamCourseId:" + mParamCourseId);
            RequestParams params = new RequestParams();
            params.put(CommonConstant.PARAM_ENTRY_TYPE, mParamType);
            params.put(CommonConstant.PARAM_ENTRY_ID, mParamTypeId);
            if (!TextUtils.isEmpty(mParamCourseId)) {
                params.put(CommonConstant.PARAM_COURSE_ID, mParamCourseId);
                mH5Type = 0;
            }
            if (!TextUtils.isEmpty(mParamPathId)) {
                params.put(CommonConstant.PARAM_PATH_ID, mParamPathId);
                mH5Type = 1;
            }
            params.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
            params.put(CommonConstant.PARAM_TOKEN, Store.getToken());
            if(!TextUtils.isEmpty(mParamCx)){
                params.put(CommonConstant.PARAM_CX, mParamCx);
            }
            params.put(CommonConstant.PARAM_TIME, String.valueOf(System.currentTimeMillis()));
            params.put(CommonConstant.PARAM_LANGUAGE, DeviceUtil.localLanguageIsZh() ? "zh" : "en");
            LogUtils.d(getClass(), "111map.string:" + params.toString());
            StringBuilder builder = new StringBuilder();
            if (0 == mH5Type) {
                builder.append(URL_PRE_COURSE);
            } else {
                builder.append(URL_PRE_PATH);
            }
            builder.append(params.toString());
            // builder.append("#/Detail");
            LOAD_URL = builder.toString();
        } else {
            LOAD_URL = URL_PRE_COURSE;
        }
        LogUtils.d(getClass(), "LOAD_URL:" + LOAD_URL);


        mContext = this;
        initWebView();
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


    /**
     * 展示网页界面
     **/

    public void initWebView() {
        WebChromeClient wvcc = new WebChromeClient();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 加载缓存内容
        webSettings.setAppCacheEnabled(true);
        //mWebView.addJavascriptInterface(new AndroidBridge(), "android");
        mWebView.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.d(getClass(), "protocol url:" + url);
                if (url.contains("backapp")) {
                    finish();
                } else if (url.contains("shareapp")) {
                    try {
                        mShareInfo = new WxShareInfo();
                        Uri uri = Uri.parse(URLDecoder.decode(url, "utf-8"));

                        String course_id = uri.getQueryParameter("course_id");
                        String share_title = uri.getQueryParameter("share_title");
                        String share_sub_title = uri.getQueryParameter("share_sub_title");
                        // String share_image_url = uri.getQueryParameter("share_image_url");
                        mShareInfo.share_url = COURSE_SHARE_URL.replace("{id}", course_id + "");
                        LogUtils.d("shareapp:---course_id:" + course_id + " share_title:" + share_title + "," +
                                "share_sub_title:" + share_sub_title + ",share_url:" + mShareInfo.share_url);
                        mShareInfo.course_id = course_id;
                        //mShareInfo.share_image_url = share_image_url;
                        mShareInfo.share_sub_title = URLDecoder.decode(share_sub_title, "utf-8");
                        mShareInfo.share_title = URLDecoder.decode(share_title, "utf-8");
                        showShareSelect();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        LogUtils.d(ex.getMessage() + "");
                    }

                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                dismissLoadingDialog();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                Base.showToast("error.code:" + error.getErrorCode() + ",error.descrip:" + error.getDescription());
            }
        };
        mWebView.setWebViewClient(wvc);

        mWebView.setWebChromeClient(new WebChromeClient() {

            /*** 视频播放相关的方法 **/

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(CourseWebActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
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

        //webview同步cookie缓存
        boolean syncCookie = Session.syncCookie(LOAD_URL,Store.getToken());
        LogUtils.d("syncCookie:"+syncCookie);
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


    private void showShareSelect() {
        DialogUtils.showWXShareSlectListDialog(mContext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        share(Wechat.NAME);
                        break;
                    case 1:
                        share(WechatMoments.NAME);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //微信好友或朋友圈分享
    private void share(final String name) {
        if (mShareInfo == null) {
            return;
        }
        //showLoadingDialog(null);
        if (Wechat.NAME.equalsIgnoreCase(name)) {
            SHARE_TYPE = SHARE_TYPE_WECHAT;
        } else {
            SHARE_TYPE = SHARE_TYPE_WECHAT_MOMENTS;
        }
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(mShareInfo.share_title);
        sp.setText(mShareInfo.share_sub_title);
        sp.setUrl(mShareInfo.share_url);
        sp.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.share_logo));
        sp.setSite(Base.string(R.string.app_name));

        Platform wechat = ShareSDK.getPlatform(name);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        wechat.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                LogUtils.d("onError------" + arg2.getMessage());
                Message message = new Message();
                message.what = 2;
                message.obj = arg2.getMessage();
                mHanlder.sendMessage(message);
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                //分享成功的回调
                LogUtils.d("onComplete------" + arg2.toString() + "openid:" + arg0.getDb().getUserId());
                Message message = new Message();
                message.what = 1;
                mHanlder.sendMessage(message);
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                LogUtils.d("onCancel------");
                Message message = new Message();
                message.what = 3;
                mHanlder.sendMessage(message);
            }
        });
        wechat.share(sp);
    }


}
