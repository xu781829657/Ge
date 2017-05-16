package com.android.ge.model.login;

/**
 * Created by xudengwang on 2017/5/16.
 */

public class TokenBean {
    private String access_token;//获取到token后，每次请求时Header中带上 Authorization:bearer {获取到的token}

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
