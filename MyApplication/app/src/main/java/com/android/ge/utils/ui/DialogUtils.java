package com.android.ge.utils.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;


/**
 * dialog显示
 * Created by WWW on 2015/10/12.
 */
public class DialogUtils {

    private static AlertDialog dialog;
    private static AlertDialog.Builder builder;

    /**
     * 显示普通dailog
     *
     * @param mContext
     * @param title
     * @param msg
     * @param okText
     * @param okInterface
     * @param errorText
     * @param errorInterface
     */
    public static void showDialog(Context mContext, String title,
                                  String msg, String okText,
                                  DialogInterface.OnClickListener okInterface,
                                  String errorText, DialogInterface.OnClickListener errorInterface) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        builder = new AlertDialog.Builder(mContext);
        if (!TextUtils.isEmpty(errorText)) {
            builder.setPositiveButton(errorText, errorInterface);
        }
        dialog = builder.setTitle(title).setMessage(msg)
                .setNegativeButton(okText, okInterface)
                .create();
        dialog.show();
    }

    /**
     * 在dialog模式的activity中显示dialog
     * @param mContext
     * @param title
     * @param msg
     * @param okText
     * @param okInterface
     * @param errorText
     * @param errorInterface
     */
    public static void showDialogInDlgActivity(final Context mContext, String title,
                                  String msg, String okText,
                                  DialogInterface.OnClickListener okInterface,
                                  String errorText, DialogInterface.OnClickListener errorInterface) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        builder = new AlertDialog.Builder(mContext);

        if (!TextUtils.isEmpty(okText)) {
            builder.setPositiveButton(okText, okInterface);
        }
        if (!TextUtils.isEmpty(errorText)) {
            builder.setNegativeButton(errorText, errorInterface);
        }

        dialog = builder.setTitle(title).setMessage(msg)
                .create();
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ((CommonBaseActivity) mContext).finish();
            }
        });
    }


    /**
     * 显示列表形式dialog
     *
     * @param mContext
     * @param title
     * @param item
     * @param listener
     */
    public static void showListDialog(Context mContext, String title, String[] item, DialogInterface.OnClickListener listener) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        builder = new AlertDialog.Builder(mContext);
        dialog = builder.setTitle(title).setItems(item, listener)
                .setPositiveButton("取消", null).create();
        dialog.show();
    }

    /**
     * 显示带View
     *
     * @param mContext
     * @param title
     * @param v
     */
    public static void showViewDialog(Context mContext, String title, View v, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancleListener) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        builder = new AlertDialog.Builder(mContext);
        if (okListener != null) {
            builder.setNegativeButton("确定", okListener);
        }
        if (cancleListener != null) {
            builder.setPositiveButton("取消", cancleListener);
        }
        dialog = builder.setTitle(title).setView(v).setCancelable(false).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showCommonPhotoSlectListDialog(Context context,DialogInterface.OnClickListener listener) {
       AlertDialog.Builder alert = new AlertDialog.Builder(context);
        //alert.setTitle(Base.string(R.string.title_select_photo));
        //设置普通文本格式的对话框，设置的是普通的Item；
        alert.setItems(R.array.photo_select,listener).create();
        alert.show();
    }

    public static void showWXShareSlectListDialog(Context context,DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        //alert.setTitle(Base.string(R.string.title_select_photo));
        //设置普通文本格式的对话框，设置的是普通的Item；
        alert.setItems(R.array.wx_share_select,listener).create();
        alert.show();
    }
}
