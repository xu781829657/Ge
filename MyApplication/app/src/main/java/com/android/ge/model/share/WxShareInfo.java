package com.android.ge.model.share;

import android.text.TextUtils;

/**
 * Created by xudengwang on 17/8/12.
 */

public class WxShareInfo {
    public String course_id;
    public String share_url;
    public String share_title;
    public String share_sub_title;
    public String share_image_url;

    public String getShare_title() {
        if (TextUtils.isEmpty(share_title)) {
            share_title = "";
        }
        return share_title;
    }

    public String getShare_sub_title() {
        if (TextUtils.isEmpty(share_sub_title)) {
            share_sub_title = "";
        }
        return share_sub_title;
    }

    public String getShare_image_url() {
        if (TextUtils.isEmpty(share_image_url)) {
            share_image_url = "";
        }
        return share_image_url;
    }

    public String getShare_url() {
        return share_url;
    }
}
