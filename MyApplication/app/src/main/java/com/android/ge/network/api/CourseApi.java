

package com.android.ge.network.api;

import com.android.ge.model.CourseClassifyInfo;
import com.android.ge.model.CourseTypeDetailInfo;
import com.android.ge.model.HomePageInfo;
import com.android.ge.model.LearningInfo;
import com.android.ge.model.NewsBean;
import com.android.ge.model.NewsResultInfo;
import com.android.ge.model.base.BaseResponse;
import com.android.ge.model.login.TokenBean;
import com.android.ge.model.organ.OrganBean;
import com.android.ge.model.path.PathListInfo;
import com.android.ge.model.task.TaskDetailInfo;
import com.android.ge.model.task.TaskListInfo;
import com.android.ge.model.user.AvatarUploadInfo;
import com.android.ge.model.user.HonorResultInfo;
import com.android.ge.model.user.UserInfo;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * retrofit api的方式进行网络请求
 */
public interface CourseApi {
    //登录
//    @FormUrlEncoded
//    @POST("/api/login")
//    Observable<BaseResponse<LoginResultInfo.TokenBean>> postLoginData(@FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST("/api/account/login")
    Observable<BaseResponse<TokenBean>> postLoginData(@FieldMap Map<String, String> map);

    //组织机构列表
//    @GET("/api/organization/self")
//    Observable<BaseResponse<ArrayList<OrganResultInfo.OrganBean>>> getOrgans();
    @GET("/api/organization/list")
    Observable<BaseResponse<ArrayList<OrganBean>>> getOrgans();

    //获取首页配置
//    @GET("/api/home/config")
//    Observable<HomePageResultInfo> getHomePageConfig(@QueryMap Map<String, String> map);
    @GET("/api/home/index")
    Observable<BaseResponse<HomePageInfo>> getHomePageConfig(@QueryMap Map<String, String> map);

    //获取课程分类接口
//    @GET("/api/course_type/list")
//    Observable<LearningResultInfo> getCourseTypeList(@QueryMap Map<String, String> map);
    @GET("/api/courses/list")
    Observable<BaseResponse<LearningInfo>> getCourseTypeList(@QueryMap Map<String, String> map);

    //获取单个课程分类
//    @GET("/api/course_type/get")
//    Observable<CourseClassifyResultInfo> getCourseClassify(@QueryMap Map<String, String> map);
    @GET("/api/coursescat/get")
    Observable<BaseResponse<CourseClassifyInfo>> getCourseClassify(@QueryMap Map<String, String> map);

    //获取任务列表
//    @GET("/api/task/self")
//    Observable<TaskListResultInfo> getTaskList(@QueryMap Map<String, String> map);
    @GET("/api/missions/list")
    Observable<BaseResponse<TaskListInfo>> getTaskList(@QueryMap Map<String, String> map);

    //获取学习路径
//    @GET("/api/path/self")
//    Observable<PathResultInfo> getLearningPath(@QueryMap Map<String, String> map);
    @GET("/api/learningpath/list")
    Observable<BaseResponse<PathListInfo>> getLearningPath(@QueryMap Map<String, String> map);

    //任务详情
    @GET("/api/missions/index")
    Observable<BaseResponse<TaskDetailInfo>> getTaskDetailInfo(@QueryMap Map<String, String> map);


    //课程分类
    @GET("/api/coursescat/index")
    Observable<BaseResponse<ArrayList<CourseTypeDetailInfo>>> getCourseTypeAll(@QueryMap Map<String, String> map);


    //资讯更多news/list
    @GET("/api/news/list")
    Observable<BaseResponse<NewsResultInfo>> getNewslist(@QueryMap Map<String, String> map);

    //上传头像
    @Multipart
    @POST("/api/account/modifyportrait")
    Observable<BaseResponse<AvatarUploadInfo>> uploadFile(@Part("portrait\"; filename=\"avatar.jpg") RequestBody file);

    //个人资料
    @GET("/api/account/edit")
    Observable<BaseResponse<UserInfo>> getUserInfo(@QueryMap Map<String, String> map);

    //修改个人资料
    @FormUrlEncoded
    @POST("/api/account/saveEdit")
    Observable<BaseResponse<Object>> postEditUserInfo(@FieldMap Map<String, String> map);

    //修改密码
    @FormUrlEncoded
    @POST("/api/account/modifypassword")
    Observable<BaseResponse<Object>> postChangePassword(@FieldMap Map<String, String> map);

    //找回密码发送验证码
    @FormUrlEncoded
    @POST("/api/account/verifyCode")
    Observable<BaseResponse<Object>> postSendVerifyCode(@FieldMap Map<String, String> map);

    //检查验证验证码输入
    @FormUrlEncoded
    @POST("api/account/checkVerifyCode")
    Observable<BaseResponse<Object>> postCheckVerifyCode(@FieldMap Map<String, String> map);


    //设置新密码
    @FormUrlEncoded
    @POST("/api/account/resetpassword")
    Observable<BaseResponse<Object>> postSetNewPassword(@FieldMap Map<String, String> map);

    //我的荣誉
    @GET("api/personal/honor")
    Observable<BaseResponse<HonorResultInfo>> getMyHonor(@QueryMap Map<String, String> map);


//
//    //获取课程比拼列表
//    @GET("/api/v1/organizations/{id}/competitions")
//    Observable<List<CompetitionBean>> getCompetions(@Path("id") int id);
//
//    //获取公司榜单
//    @GET("/api/v1/organizations/{organization_id}/trainee/ranking")
//    Observable<CompanyRankResultInfo> getCompanyRank(@Path("organization_id") int organization_id);
//
//    @GET("/api/v1/organizations/{organization_id}/trainee/new_ranking")
//    Observable<CompanyRankResultInfoNew> getCompanyRankNew(@Path("organization_id") int organization_id);
//
//    //获取学习统计
//    @GET("/api/v1/organizations/{organization_id}/statistics")
//    Observable<StatisticBean> getMyLearnData(@Path("organization_id") int organization_id);
//
//    //获取我的成就
//    @GET("/api/v1/organizations/{organization_id}/trainee/achievements")
//    Observable<AchieveResultInfo> getMyAchieve(@Path("organization_id") int organization_id);
//
//    //积分规则
//    @GET("/api/v1/organizations/{organization_id}/medals")
//    Observable<List<AchieveResultInfo.MedalBean>> getPointsRule(@Path("organization_id") int organization_id);
//
//    //测试提交
//    @FormUrlEncoded
//    @POST("/api/v2/organizations/{organization_id}/courses/{course_id}/pages/{page_id}")
//    Observable<ReturnBean> postQuizData(@Path("organization_id") int organization_id, @Path("course_id") int course_id, @Path("page_id") int page_id, @FieldMap Map<String, String> map);
//
//    //获取问卷列表
//    @GET("/api/v1/organizations/{organization_id}/trainee/survey_assignments")
//    Observable<List<QuestionnaireBean>> getQuestionnaireList(@Path("organization_id") int organization_id);
//
//    @Multipart
//    @PUT("/api/v1/memberships/{organization_id}")
//    Observable<MenberShipsBean> uploadFile(@Path("organization_id") int organization_id, @Part("avatar\"; filename=\"avatar.jpg") RequestBody file);
//
//
//    //课程点赞
//    @FormUrlEncoded
//    @PUT("/api/v1/organizations/{organization_id}/courses/{course_id}/like")
//    Observable<CourseBean> putCourseLike(@Path("organization_id") int organization_id, @Path("course_id") int course_id, @FieldMap Map<String, String> map);
//
//    //课程取消点赞
//    @FormUrlEncoded
//    @PUT("/api/v1/organizations/{organization_id}/courses/{course_id}/unlike")
//    Observable<CourseBean> putCourseUnLike(@Path("organization_id") int organization_id, @Path("course_id") int course_id, @FieldMap Map<String, String> map);
//
//    //获取某课程下的评论
//    @GET("/api/v1/organizations/{organization_id}/trainee/assignments/{assignment_id}/comments")
//    Observable<List<CommentBean>> getCourseComments(@Path("organization_id") int organization_id, @Path("assignment_id") int assignment_id);
//
//    //增加课程评论
//    @FormUrlEncoded
//    @POST("/api/v1/organizations/{organization_id}/trainee/assignments/{assignment_id}/comments")
//    Observable<CommentBean> postCourseComment(@Path("organization_id") int organization_id, @Path("assignment_id") int assignment_id, @FieldMap Map<String, String> map);
//
//    //删除课程的某个评论
//    @DELETE("/api/v1/organizations/{organization_id}/trainee/assignments/{assignment_id}/comments/{id}")
//    Observable<String> deleteCourseComment(@Path("organization_id") int organization_id, @Path("assignment_id") int assignment_id, @Path("id") int id);

}
