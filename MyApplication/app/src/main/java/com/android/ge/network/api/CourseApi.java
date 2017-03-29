

package com.android.ge.network.api;


import com.android.ge.model.login.LoginResultInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * retrofit api的方式进行网络请求
 */
public interface CourseApi {
    @FormUrlEncoded
    @POST("/api/login")
    Observable<LoginResultInfo> postLoginData(@FieldMap Map<String, String> map);
//
//    //修改个人资料
//    @FormUrlEncoded
//    @PUT("/api/v1/memberships/{organization_id}")
//    Observable<MenberShipsBean> putPersonalData(@Path("organization_id") int organization_id, @FieldMap Map<String, String> map);
//
//    //修改密码
//    @FormUrlEncoded
//    @PUT("/api/v1/account/change_password")
//    Observable<String> putChangePassword(@FieldMap Map<String, String> map);
//
//    //重置密码
//    @FormUrlEncoded
//    @PUT("/api/v1/account/reset_password")
//    Observable<String> putSetPassword(@FieldMap Map<String, String> map);
//
//
//    //发送验证码
//    @FormUrlEncoded
//    @PUT("/api/v1/account/send_code_via_sms")
//    Observable<String> putSendSms(@FieldMap Map<String, String> map);
//
//    //检查验证码
//
//    @GET("/api/v1/account/check_validation_code")
//    Observable<ResultBean> getCheckSms(@QueryMap Map<String, String> options);
//
//    //获取更新信息
//    @GET("/api/v1/app_versions")
//    Observable<AppUpdateResultInfo> getAppUpdate();
//
//    //个人资料
//    @GET("api/v1/memberships")
//    Observable<ArrayList<MenberShipsBean>> getMemberships();
//
//    // 列出我参与的课程
//    @GET("/api/v1/organizations/{id}/assignments")
//    Observable<List<OrganizationListItemBean>> getMyCourses(@Path("id") int id);
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
