package com.android.ge.ui.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.controller.Store;
import com.android.ge.model.user.HonorResultInfo;
import com.android.ge.model.user.SummaryInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/4/9.
 */

public class LearningSummaryActivity extends CommonBaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.tv_complete_course_num)
    TextView mTvCompleteCourse;

    @Bind(R.id.tv_complete_quiz_num)
    TextView mTvCompleteQuiz;
    @Bind(R.id.tv_complete_survey_num)
    TextView mTvCompleteSurvey;

    @Bind(R.id.tv_complete_path_num)
    TextView mTvCompletePath;
    @Bind(R.id.tv_complete_task_num)
    TextView mTvCompleteTask;

    @Bind(R.id.tv_quiz_average)
    TextView mTvQuizAverage;

    @Bind(R.id.tv_total_time)
    TextView mTvTotalTime;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_learning_summary;
    }

    @Override
    protected void initData() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText(Base.string(R.string.title_learning_summary));
        getNetDataMySummary();
    }

    private void refreshView(SummaryInfo info) {
        mTvCompleteCourse.setText(info.course_finished_count + "");
        mTvCompleteQuiz.setText(info.exam_finished_count + "");
        mTvCompleteSurvey.setText(info.quest_finished_count + "");
        mTvCompletePath.setText(info.learnpath_finished_count + "");
        mTvCompleteTask.setText(info.mission_finished_count + "");
        mTvQuizAverage.setText(info.score_avg + "");
        mTvTotalTime.setText(info.study_time + "");
    }

    private void getNetDataMySummary() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("org_id", Store.getOrganId());

        showLoadingDialog(null);
        Network.getCourseApi("学习小结").getMySummary(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<SummaryInfo>())
                .subscribe(new Observer<SummaryInfo>() {
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
                    public void onNext(SummaryInfo info) {
                        if (info == null) {
                            Base.showToast(R.string.errmsg_data_error);
                            return;
                        }
                        refreshView(info);
                    }
                });
    }


}
