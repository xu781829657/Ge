package com.android.ge.controller.adapter.Itemviewdelegate;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.app.AppApplication;
import com.android.ge.model.CourseBean;
import com.android.ge.model.learning.BaseLearningItem;
import com.android.ge.utils.image.ImageLoader;
import com.android.ge.utils.image.ImageRequest;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by xudengwang on 17/3/23.
 */

public class CourseItemViewDelegate implements ItemViewDelegate<BaseLearningItem> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_for_course;
    }

    @Override
    public boolean isForViewType(BaseLearningItem baseItemInfo, int position) {
        return baseItemInfo.isCourse();
    }

    @Override
    public void convert(ViewHolder holder, BaseLearningItem baseItemInfo, int position) {
        CourseBean courseBean = baseItemInfo.getCourseBean();

        StaggeredGridLayoutManager.LayoutParams itemParams = (StaggeredGridLayoutManager.LayoutParams) holder
                .itemView.getLayoutParams();
        itemParams.width = StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT;
        itemParams.height = StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT;
        if (baseItemInfo.getOriginalPosition() % 2 == 0) {
            itemParams.setMargins((int) (ScreenUtils.getScreenDensity(Base.getContext()) * 12), 0, (int) (ScreenUtils
                            .getScreenDensity(Base.getContext()) * 5),
                    (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 10));
        } else {
            itemParams.setMargins((int) (ScreenUtils.getScreenDensity(Base.getContext()) * 5), 0, (int) (ScreenUtils
                            .getScreenDensity(Base.getContext()) * 12),
                    (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 10));
        }
        holder.itemView.setLayoutParams(itemParams);


//        LinearLayout.LayoutParams itemParams = (LinearLayout.LayoutParams) holder.getView(R.id.lin_item)
// .getLayoutParams();
//        itemParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        itemParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        holder.getView(R.id.lin_item).setLayoutParams(itemParams);


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(Base.getContext()) - 34 * ScreenUtils.getScreenDensity(Base
                .getContext())) / 2;
        params.height = (int) params.width * 86 / 171;

        holder.getView(R.id.rel_cover).setLayoutParams(params);

        //标题
        holder.setText(R.id.tv_course_title, courseBean.getTitle());
        //进度
        if (courseBean.getProgress() == 0) {
            holder.getView(R.id.view_bg_cover).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_course_progress, Base.string(R.string.un_start));
        } else {
            holder.getView(R.id.view_bg_cover).setVisibility(View.GONE);
            holder.setText(R.id.tv_course_progress, String.format(Base.string(R.string.format_total_progress),
                    courseBean.getProgress() + "%"));
        };
        //作者
        if (!TextUtils.isEmpty(courseBean.getTeacher_name())) {
            holder.setText(R.id.tv_course_auth, String.format(Base.getContext().getString(R.string.format_auth), courseBean.getTeacher_name()));
            holder.getView(R.id.tv_course_auth).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_course_auth).setVisibility(View.GONE);
        }
        LogUtils.d(getClass(), "title:" + courseBean.getTitle() + ",---cover:" + courseBean.getCover());
        if (!TextUtils.isEmpty(courseBean.getCover())) {
            setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), courseBean.getCover(), R.drawable
                    .demo_course_loading_icon);
        } else {
            holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_banner_loading_icon);
        }
    }

    /**
     * 加载网络上的图片
     *
     * @param
     * @param url
     */
    public void setImageFromInternet(ImageView iv, String url) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).url(url).create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }

    public void setImageFromInternet(ImageView iv, String url, int defaultDrawableId) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).placeHolder(defaultDrawableId).url(url)
                .create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }
}
