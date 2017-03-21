package com.android.ge.model;

import java.util.List;

/**
 * Created by xudengwang on 17/3/20.
 */

public class HomePageResultInfo extends BaseResultInfo {
    private HomePageInfo data;

    public static class HomePageInfo {
        private List<GalleryBean> gallery;//banner
        private List<NewsBean> messages;//最新资讯
        private RequiredInfo required;//必修课
        private RecommandInfo recommand;//推荐课
    }
}
