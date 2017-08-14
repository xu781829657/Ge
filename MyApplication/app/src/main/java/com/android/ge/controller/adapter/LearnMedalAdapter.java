package com.android.ge.controller.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.ge.R;
import com.android.ge.model.user.MedalBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * Created by xudengwang on 17/8/13.
 */

public class LearnMedalAdapter extends BaseCommonAdapter<MedalBean> {
    public LearnMedalAdapter(Context context, List<MedalBean> datas) {
        super(context, R.layout.item_for_learn_medal, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MedalBean medalBean, int position) {
//        setImageFromInternet(holder,R.id.aiv_test_medal_photo, medalBean.image_url, R.drawable
//                .achievement_default_icon);
        setImageFromInternet((ImageView) holder.getView(R.id.aiv_learn_medal_photo), medalBean.image_url, R.drawable
                .achievement_default_icon);
        holder.setText(R.id.tv_learn_medal_name, medalBean.name);
        if (!TextUtils.isEmpty(medalBean.descrip)) {
            holder.setText(R.id.tv_learn_medal_decrip, medalBean.descrip);
        } else {
            holder.getView(R.id.tv_learn_medal_decrip).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_learn_medal_num, medalBean.count + "");

    }
}
