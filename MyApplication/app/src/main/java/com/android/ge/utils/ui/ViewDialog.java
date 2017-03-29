package com.android.ge.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.ge.R;

public class ViewDialog {

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.common_view_loding_dialog, null);
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.loding_view_lin);
        TextView msgTxt = (TextView) v.findViewById(R.id.loding_message_text);
        if (TextUtils.isEmpty(msg)) {
            msgTxt.setVisibility(View.GONE);
        } else {
            msgTxt.setText(msg);
            msgTxt.setVisibility(View.VISIBLE);
        }

        final Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);

        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    loadingDialog.dismiss();
                }
                return false;

            }
        });
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        return loadingDialog;
    }


    public static void showPromptDialog(Context ctx, String title, String msg, DialogInterface.OnClickListener ensureListener, DialogInterface.OnClickListener cancelListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setNegativeButton(ctx.getString(R.string.alt_cancel), cancelListener);
        builder.setPositiveButton(ctx.getString(R.string.alt_continue), ensureListener);
        builder.show();
    }

    public static void showSingleDialog(Context ctx, String title, String msg, DialogInterface.OnClickListener ensureListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setPositiveButton(ctx.getString(R.string.alt_ensure), ensureListener);
        builder.setCancelable(false);
        builder.show();
    }



}
