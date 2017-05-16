package com.android.ge.model.organ;

/**
 * Created by xudengwang on 2017/5/16.
 * "org_id": 1,
 "code": "liexue100001",
 "name": "上海猎学信息技术有限公司",
 "brief": "猎学信息",
 "logo": "",
 "contact_name": "",
 "contact_mobile": "",
 "contact_qq": "",
 "contact_email": "",
 "created_at": null,
 "updated_at": null,
 "pivot": {
 "user_id": 1,
 "org_id": 1
 }
 */

public class OrganBean {
    private String org_id;
    private String name;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
