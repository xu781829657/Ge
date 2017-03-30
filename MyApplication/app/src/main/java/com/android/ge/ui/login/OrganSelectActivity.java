package com.android.ge.ui.login;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.OrganListAdapter;
import com.android.ge.controller.diliver.RecycleViewDivider;
import com.android.ge.model.login.OrganResultInfo;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.tabmain.MaintabActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by xudengwang on 2017/1/17.
 */

public class OrganSelectActivity extends CommonBaseActivity {

    @Bind(R.id.recyclerview_organ)
    RecyclerView mRvOrgan;

    private OrganListAdapter mAdapter;
    private ArrayList<OrganResultInfo.OrganBean> mOrgans;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_organ_select;
    }

    @Override
    protected void initData() {
        mContext = this;
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mOrgans = (ArrayList<OrganResultInfo.OrganBean>) bundle.getSerializable(CommonConstant.KEY_ORGAN_LIST);
            }
        }

        refreshAdapter();

    }

    private void refreshAdapter() {
        if (mAdapter == null) {
            mRvOrgan.addItemDecoration(new RecycleViewDivider(
                    mContext, LinearLayoutManager.VERTICAL, (int) ScreenUtils.getScreenDensity(mContext) * 12, getResources().getColor(R.color.transparent)));
            mAdapter = new OrganListAdapter(mContext, mOrgans, new OrganListAdapter.IOrganSelectListener() {
                @Override
                public void select(OrganResultInfo.OrganBean bean) {
//                    Store.storeOrgan(mContext,bean);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(CommonConstant.KEY_ORGAN, bean);
//                    gotoActivity(HomeActivity.class, bundle, true);
                    gotoActivity(MaintabActivity.class, true);
                }
            });
            LinearLayoutManager LayoutManager = new LinearLayoutManager(mContext);
            mRvOrgan.setAdapter(mAdapter);
            mRvOrgan.setLayoutManager(LayoutManager);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
