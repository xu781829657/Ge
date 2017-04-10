package com.android.ge.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.adapter.Itemviewdelegate.CourseItemViewDelegate;
import com.android.ge.controller.adapter.Itemviewdelegate.CourseTypeItemViewDelegate;
import com.android.ge.model.BaseCourseTypeInfo;
import com.android.ge.model.CourseBean;
import com.android.ge.model.learning.BaseLearningItem;
import com.android.ge.model.learning.TitleItemInfo;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.course.ClassifyCourseListActivity;
import com.android.ge.ui.webview.CommonWebActivity;
import com.android.ge.ui.webview.CourseWebActivity;
import com.android.ge.utils.image.ImageLoader;
import com.android.ge.utils.image.ImageRequest;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xudengwang on 17/3/21.
 */

public class LearningCourseTypeAdapter extends MultiItemTypeAdapter<BaseLearningItem> implements MultiItemTypeAdapter.OnItemClickListener {

    //private List<BaseLearningItem> mDatas = new ArrayList<>();

    public LearningCourseTypeAdapter(Context context, List<BaseLearningItem> datas) {
        super(context, datas);
        addItemViewDelegate(new CourseItemViewDelegate());
        addItemViewDelegate(new CourseTypeItemViewDelegate());
        setOnItemClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, BaseLearningItem item) {

        super.convert(holder, item);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        BaseLearningItem item = mDatas.get(position);
        if (item.isTitle()) {
            TitleItemInfo titleItemInfo = item.getTitleItemInfo();
            Bundle bundle = new Bundle();
            bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, titleItemInfo.getId());
            bundle.putString(CommonConstant.KEY_TITLE, titleItemInfo.getTitle());
            gotoActivity(ClassifyCourseListActivity.class, bundle);
        } else if (item.isCourse()) {
            CourseBean courseBean = item.getCourseBean();
            Bundle bundle = new Bundle();
            bundle.putString(CommonConstant.PARAM_COURSE_ID,courseBean.getCourse_id());
            gotoActivity(CourseWebActivity.class, bundle);
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;

    }

    /**
     * 加载网络上的图片
     *
     * @param viewId
     * @param url
     */
    public void setImageFromInternet(ImageView iv, String url) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).url(url).create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }

    public void setImageFromInternet(ImageView iv, String url, int defaultDrawableId) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).placeHolder(defaultDrawableId).url(url).create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }


    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null) intent.putExtras(bundle);
        mContext.startActivity(intent);

    }


}
