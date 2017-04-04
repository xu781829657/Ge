package com.android.ge.model;

import java.util.List;

/**
 * Created by xudengwang on 17/3/20.
 */

public class HomePageResultInfo extends BaseResultInfo {
    public HomePageInfo data;

    public static class HomePageInfo {
        public List<GalleryBean> gallery;//banner
        public List<NewsBean> news;//最新资讯
        public RequiredInfo required;//必修课
        public RecommandInfo recommand;//推荐课
    }

}
