package com.android.ge.ui.setting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ge.R;
import com.android.ge.controller.adapter.RankAdapter;
import com.android.ge.model.RankUserBean;
import com.android.ge.ui.base.CommonBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/4/10.
 */

public class RankListFragment extends CommonBaseFragment {

    @Bind(R.id.rv_rank)
    RecyclerView mRvRank;
    RankAdapter mAdapter;
    List<RankUserBean> mRankMenbers = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fm_rank_list;
    }

    @Override
    protected void initData() {
        initFalseData();
    }

    private void initFalseData() {
        for (int i = 0; i < 5; i++) {
            RankUserBean bean = new RankUserBean();
            bean.setRank(i + 1);
            bean.setName("人物" + i);
            bean.setScore(999);
            if (i == 1) {
                bean.setName("Yang Cao");
                bean.setScore(888);
            } else if (i == 2) {
                bean.setName("Bernie");
                bean.setScore(666);
            } else if (i == 3) {
                bean.setName("Rose");
                bean.setScore(333);
            } else if (i == 4) {
                bean.setName("Jingjing");
                bean.setScore(111);
            }

            mRankMenbers.add(bean);
        }
        refreshAdapter();
    }

    private void refreshAdapter() {
        if (mAdapter == null) {
            mAdapter = new RankAdapter(getMContext(), mRankMenbers);
            LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
            mRvRank.setAdapter(mAdapter);
            mRvRank.setLayoutManager(LayoutManager);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
