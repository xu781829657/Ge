package com.android.ge.ui.setting;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.AppManager;
import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.LearnMedalAdapter;
import com.android.ge.controller.adapter.TestMedalAdapter;
import com.android.ge.model.user.HonorResultInfo;
import com.android.ge.model.user.MedalBean;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.login.LoginActivity;
import com.android.ge.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/8/11.
 * 我的-我的荣誉
 */

public class MyHonorActivity extends CommonBaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rv_test_medal)
    RecyclerView mRvTestMedal;
    @Bind(R.id.rv_learn_medal)
    RecyclerView mRvLearnMedal;

    @Bind(R.id.lin_test_medal)
    LinearLayout mLinTestMedal;
    @Bind(R.id.lin_learn_medal)
    LinearLayout mLinLearnMedal;

    @Bind(R.id.tv_test_medal_num)
    TextView mTvTestMedalNum;
    @Bind(R.id.tv_learn_medal_num)
    TextView mTvLearnMedalNum;

    private ArrayList<MedalBean> mTestMedalList = new ArrayList<>();
    private ArrayList<MedalBean> mLearnMedalList = new ArrayList<>();
    private int mTestMedalNum;
    private int mLearnMedalNum;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_honor;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTvTitle.setText(Base.string(R.string.title_my_honor));
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getNetDataMyHonor();
    }


    private void refreshMedalAdapterData() {
        if (mTestMedalList.size() > 0) {
            final GridLayoutManager layoutManager = new GridLayoutManager(mContext, mTestMedalList.size());
            mRvTestMedal.setLayoutManager(layoutManager);
            TestMedalAdapter testMedalAdapter = new TestMedalAdapter(mContext, mTestMedalList);
            mRvTestMedal.setAdapter(testMedalAdapter);
        }

        if (mLearnMedalList.size() > 0) {
            LinearLayoutManager LayoutManager = new LinearLayoutManager(mContext);
            mRvLearnMedal.setLayoutManager(LayoutManager);
            mRvLearnMedal.setHasFixedSize(true);
            LearnMedalAdapter testMedalAdapter = new LearnMedalAdapter(mContext, mLearnMedalList);
            mRvLearnMedal.setAdapter(testMedalAdapter);
        }
    }

    private void refreshView() {
        mTvTestMedalNum.setText(String.format(Base.string(R.string.format_medal_num), mTestMedalNum));
        mTvLearnMedalNum.setText(String.format(Base.string(R.string.format_badges_num), mLearnMedalNum));
        refreshMedalAdapterData();
    }

    //我的荣誉
    private void getNetDataMyHonor() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("org_id", Store.getOrganId());

        showLoadingDialog(null);
        Network.getCourseApi("我的荣誉").getMyHonor(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<HonorResultInfo>())
                .subscribe(new Observer<HonorResultInfo>() {
                    @Override
                    public void onCompleted() {
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
                        e.printStackTrace();
                        dismissLoadingDialog();
                        Base.showToast(ExceptionEngine.handleException(e).message);
                    }

                    @Override
                    public void onNext(HonorResultInfo info) {
                        if (info == null) {
                            Base.showToast(R.string.errmsg_data_error);
                            return;
                        }
                        if (info.medals != null && info.medals.size() > 0) {
                            LogUtils.d(" info.medals:" + info.medals.size());
                            mTestMedalList.clear();
                            mTestMedalList.addAll(info.medals);
                            refreshTestMedalCount();
                        }
                        if (info.rewards != null && info.rewards.size() > 0) {
                            LogUtils.d(" info.rewards:" + info.rewards.size());
                            mLearnMedalList.clear();
                            mLearnMedalList.addAll(info.rewards);
                            refreshLearnMedalCount();
                        }
                        refreshView();
                    }
                });

    }


    private void refreshTestMedalCount() {
        mTestMedalNum = 0;
        for (int i = 0; i < mTestMedalList.size(); i++) {
            mTestMedalNum += mTestMedalList.get(i).count;
        }
    }

    private void refreshLearnMedalCount() {
        mLearnMedalNum = 0;
        for (int i = 0; i < mLearnMedalList.size(); i++) {
            mLearnMedalNum += mLearnMedalList.get(i).count;
        }
    }
}
