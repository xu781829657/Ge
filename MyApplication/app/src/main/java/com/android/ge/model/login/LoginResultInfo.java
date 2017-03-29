package com.android.ge.model.login;

import com.android.ge.model.BaseResultInfo;

/**
 * Created by xudengwang on 17/3/29.
 *
 * 登录接口
 请求URL：

 http://{{host}}/api/account/login
 */

public class LoginResultInfo extends BaseResultInfo {
    private TokenBean data;

    public TokenBean getData() {
        return data;
    }

    public void setData(TokenBean data) {
        this.data = data;
    }

    public static class TokenBean {
        private String token;//获取到token后，每次请求时Header中带上 Authorization:bearer {获取到的token}

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
