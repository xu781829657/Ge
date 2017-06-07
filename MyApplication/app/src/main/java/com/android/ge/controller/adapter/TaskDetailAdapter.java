package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.CourseBean;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.model.task.TaskDetailBean;
import com.android.ge.model.task.TaskQuestionnaireBean;
import com.android.ge.model.task.TaskQuizBean;
import com.android.ge.ui.customview.TaskContentInfoView;
import com.android.ge.ui.webview.CourseWebActivity;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class TaskDetailAdapter extends BaseCommonAdapter<TaskDetailBean> implements
        MultiItemTypeAdapter.OnItemClickListener {
    public TaskDetailAdapter(Context context, List<TaskDetailBean> datas) {
        super(context, R.layout.item_for_task_detail, datas);
        setOnItemClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, TaskDetailBean taskDetailBean, int position) {
//        ((RoundProgressBar) holder.getView(R.id.rd_progressbar)).setProgress(80);
//        holder.setText(R.id.tv_task_name, taskBean.getTitle());
        // ((TaskContentInfoView) holder.getView(R.id.view_task_content_info)).setTaskBean
        // (taskBean);

        holder.setText(R.id.tv_progress, String.format(Base.string(R.string
                .format_progress_complete), taskDetailBean.getProgress()) + "%");
        LogUtils.d("123taskDetailBean.getDetail_type:" + taskDetailBean.getDetail_type());
        if (CommonConstant.TASK_COURSE_TYPE_COURSE.equalsIgnoreCase(taskDetailBean.getDetail_type())) {
            holder.setText(R.id.tv_task_course_type, "类型: 课件");
            TaskCourseBean courseBean = taskDetailBean.courses;
            if (courseBean != null) {
                if (TextUtils.isEmpty(courseBean.getTitle())) {
                    courseBean.setTitle("未知");
                }
                holder.setText(R.id.tv_course_title, courseBean.getTitle());
                if (!TextUtils.isEmpty(courseBean.getCover())) {
                    setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), courseBean
                            .getCover(), R.drawable.demo_course_loading_icon);
                } else {
                    holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_course_loading_icon);
                }

            } else {
                holder.setText(R.id.tv_course_title, "未知");
                holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_course_loading_icon);
            }
        } else if (CommonConstant.TASK_COURSE_TYPE_QUIZ.equalsIgnoreCase(taskDetailBean.getDetail_type())) {
            holder.setText(R.id.tv_task_course_type, "类型: 考试");
            TaskQuizBean quizBean = taskDetailBean.examinations;
            if (quizBean != null) {
                if (TextUtils.isEmpty(quizBean.getTitle())) {
                    quizBean.setTitle("未知");
                }
                holder.setText(R.id.tv_course_title, quizBean.getTitle());
                if (!TextUtils.isEmpty(quizBean.getCover())) {
                    setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), quizBean
                            .getCover(), R.drawable.demo_course_loading_icon);
                } else {
                    holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_course_loading_icon);
                }
            } else {
                holder.setText(R.id.tv_course_title, "未知");
                holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_course_loading_icon);
            }

        } else if (CommonConstant.TASK_COURSE_TYPE_SURVEY.equalsIgnoreCase(taskDetailBean.getDetail_type())) {
            holder.setText(R.id.tv_task_course_type, "类型: 问卷");
            TaskQuestionnaireBean questionnaireBean = taskDetailBean.questionnaire;
            if (questionnaireBean != null) {
                if (TextUtils.isEmpty(questionnaireBean.getTitle())) {
                    questionnaireBean.setTitle("未知");
                }
                holder.setText(R.id.tv_course_title, questionnaireBean.getTitle());
                if (!TextUtils.isEmpty(questionnaireBean.getCover())) {
                    setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), questionnaireBean
                            .getCover(), R.drawable.demo_course_loading_icon);
                } else {
                    holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_course_loading_icon);
                }
            } else {
                holder.setText(R.id.tv_course_title, "未知");
                holder.setImageResource(R.id.iv_course_cover, R.drawable.demo_course_loading_icon);
            }
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        TaskDetailBean taskDetailBean = mDatas.get(position);
        if (CommonConstant.TASK_COURSE_TYPE_COURSE.equalsIgnoreCase(taskDetailBean.getDetail_type())) {
            Bundle bundle = new Bundle();
            bundle.putString(CommonConstant.PARAM_COURSE_ID, taskDetailBean.courses.getId() + "");
            bundle.putString(CommonConstant.PARAM_TYPE,CommonConstant.TYPE_MISSIONS);
            bundle.putString(CommonConstant.PARAM_TYPE_ID,taskDetailBean.courses.getId() + "");
            gotoActivity(CourseWebActivity.class, bundle);
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
