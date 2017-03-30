package com.android.ge.model.login;

import com.android.ge.model.BaseResultInfo;

import java.util.ArrayList;

/**
 * Created by xudengwang on 2017/3/30.
 *
 http://{{host}}/api/organization/self

 请求方式：

 GET
 {
 "code": "0",
 "data": [{机构结构体a},{机构结构体b} ...]
 }
 */

public class OrganResultInfo extends BaseResultInfo{

    public ArrayList<OrganBean> data;

    /**
     *     {
     "org_id" : "12",
     "name" : "机构A",
     "fullname" : "机构全称",
     "logo" : "logo的url路径"
     }
     */
    public static class OrganBean{
        private String org_id;
        private String name;
        private String fullname;
        private String logo;

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
