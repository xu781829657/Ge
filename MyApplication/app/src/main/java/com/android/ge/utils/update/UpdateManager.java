package com.android.ge.utils.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.ge.R;
import com.android.ge.constant.CommonConstant;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class UpdateManager {

    private Context mContext;

    // 返回的安装包url
    private Dialog noticeDialog;

    private Dialog downloadDialog;
    /* 下载包安装路径 */
//	private static final String savePath = "/sdcard/updatedemo/";

    private static final String saveFileName = CommonConstant.ROOT_CACHE_DIR + File.separator + "cuike.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
//    private ProgressBar mProgress;
    private MaterialProgressBar mProgress;
    private TextView mTvProgress;

    private static final int MSG_DOWN_UPDATE = 1;

    private static final int MSG_DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;

    private boolean interceptFlag = false;

    public static final String VERSION_UPDATE_RECEIVER = "lohas.android.app.update.receiver";

    private UpdateInfo mUpdateInfo;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    mTvProgress.setText(progress+"%");
                    break;
                case MSG_DOWN_OVER:
                    downloadDialog.dismiss();
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    // 外部接口让主Activity调用
    // public void checkUpdateInfo(){
    // MyLog.d(getClass(), "checkUpdateInfo");
    // showNoticeDialog();
    // }

    public void showUpdateInfo(Context context, UpdateInfo info) {
        this.mUpdateInfo = info;
        mContext = context;
        showNoticeDialog(context, info);
    }

    private void showNoticeDialog(Context context, UpdateInfo info) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle("发现新版本" + info.androidVersion);
        builder.setMessage(info.androidChangeLog);

        builder.setPositiveButton("立即更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        if (info.androidForce != 1) {
            builder.setNegativeButton("稍后再说 ",
                    new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        noticeDialog = builder.create();
        if (info.androidForce == 1) {
            noticeDialog.setCancelable(false);
        }
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
    }

    private void showDownloadDialog() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.app_updating));
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.view_update_download, null);
        mProgress = (MaterialProgressBar) v.findViewById(R.id.progressbar);
        mTvProgress = (TextView) v.findViewById(R.id.tv_progress);
        builder.setView(v);
        builder.setNegativeButton("取消",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        interceptFlag = true;

                    }
                });
        downloadDialog = builder.create();
//        downloadDialog.setContentView(v, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT));
        downloadDialog.show();

        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(mUpdateInfo.androidApkUrl);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(CommonConstant.ROOT_CACHE_DIR);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(MSG_DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(MSG_DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);

    }
}
