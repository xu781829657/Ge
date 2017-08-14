package com.android.ge.model.user;

import android.text.TextUtils;

/**
 * Created by xudengwang on 2017/8/11.
 * "id": 1,
 * "name": "胡斌",
 * "email": "hubin@winona.cn",
 * "mobile": "18621792843",
 * "remember_token": null,
 * "created_at": "2017-04-24 16:03:02",
 * "updated_at": "2017-07-04 21:50:45"
 */

public class UserInfo {
    public String name;
    public String email;
    public String mobile;
    public String image_url;

    public String getName() {
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        return name;
    }

    public String getEmail() {
        if (TextUtils.isEmpty(email)) {
            email = "";
        }
        return email;
    }

    public String getMobile() {
        if (TextUtils.isEmpty(mobile)) {
            mobile = "";
        }
        return mobile;
    }

    public String getImage_ur() {
        if (TextUtils.isEmpty(image_url)) {
            image_url = "";
        }
        return image_url;
    }
}
