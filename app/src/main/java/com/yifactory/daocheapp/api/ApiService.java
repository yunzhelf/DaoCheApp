package com.yifactory.daocheapp.api;

import com.yifactory.daocheapp.bean.AddDepositeRecordBean;
import com.yifactory.daocheapp.bean.AddQuestionAnswerAppraiseBean;
import com.yifactory.daocheapp.bean.AddRegisterShareBean;
import com.yifactory.daocheapp.bean.AddRewardBean;
import com.yifactory.daocheapp.bean.AddShowMoodAppraiseBean;
import com.yifactory.daocheapp.bean.AddShowMoodBean;
import com.yifactory.daocheapp.bean.AddShowMoodCommentBean;
import com.yifactory.daocheapp.bean.AddTeacherRegistBean;
import com.yifactory.daocheapp.bean.AddUserBalanceBean;
import com.yifactory.daocheapp.bean.AddUserBalanceBean2;
import com.yifactory.daocheapp.bean.AddUserQuestionAnswerBean;
import com.yifactory.daocheapp.bean.AddUserQuestionBean;
import com.yifactory.daocheapp.bean.BankCardBean;
import com.yifactory.daocheapp.bean.BaseBean;
import com.yifactory.daocheapp.bean.CouponListBean;
import com.yifactory.daocheapp.bean.DeleteShowMoodBean;
import com.yifactory.daocheapp.bean.DeleteUserAttentionBean;
import com.yifactory.daocheapp.bean.DeleteUserQuestionBean;
import com.yifactory.daocheapp.bean.GetAttentionListBean;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.bean.GetLevalDataBean;
import com.yifactory.daocheapp.bean.GetShowMoodCommentBean;
import com.yifactory.daocheapp.bean.GetShowMoodListBean;
import com.yifactory.daocheapp.bean.GetStudyDateBean;
import com.yifactory.daocheapp.bean.GetStudyReocrdBean;
import com.yifactory.daocheapp.bean.GetSysArmyAnasBean;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.bean.GetUserBalanceRecordBean;
import com.yifactory.daocheapp.bean.GetUserBuyRecordBean;
import com.yifactory.daocheapp.bean.GetUserBuyedBean;
import com.yifactory.daocheapp.bean.GetUserQuestionAnswerBean;
import com.yifactory.daocheapp.bean.GetUserQuestionListBean;
import com.yifactory.daocheapp.bean.LevelListBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.MsgBean;
import com.yifactory.daocheapp.bean.PasswordBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.RegisterBean;
import com.yifactory.daocheapp.bean.StsToken;
import com.yifactory.daocheapp.bean.StudyDateBean;
import com.yifactory.daocheapp.bean.TwoVideoListBean;
import com.yifactory.daocheapp.bean.TeacherBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.bean.VerifyCodeBean;
import com.yifactory.daocheapp.bean.VideoListBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiService {

    @FormUrlEncoded
    @POST("askcar/user/login.do")
    Observable<LoginBean> login(@Field("mobile") String mobile, @Field("password") String password, @Field("wxId") String wxId);

    @FormUrlEncoded
    @POST("sns/oauth2/access_token")
    Observable<String> getWeChatAccessToken(@Field("appid") String appid, @Field("secret") String secret, @Field("code") String code, @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST("sns/userinfo")
    Observable<String> getUserWeChatInfo(@Field("access_token") String access_token, @Field("openid") String openid);

    @FormUrlEncoded
    @POST("askcar/user/doSendCode.do")
    Observable<VerifyCodeBean> doSendCode(@Field("phone") String phone, @Field("type") int type);

    @FormUrlEncoded
    @POST("askcar/user/addUser.do")
    Observable<RegisterBean> addUser(@Field("mobile") String mobile, @Field("verifyCode") String verifyCode,
                                     @Field("password") String possword, @Field("wxId") String wxId,
                                     @Field("companyName") String companyName, @Field("jobName") String jobName,
                                     @Field("jobTime") int jobTime, @Field("jobArea") String jobArea,
                                     @Field("trainAttention") String trainAttention, @Field("nowArea") String nowArea,
                                     @Field("sex") String sex, @Field("nickName") String nickName, @Field("headImg") String headImg);

    @FormUrlEncoded
    @POST("askcar/user/getCoupon.do")
    Observable<CouponListBean> getCoupon(@Field("uId") String uId, @Field("state") int state);


    @FormUrlEncoded
    @POST("askcar/user/getUserById.do")
    Observable<UserBean> getUserById(@Field("uId") String uId, @Field("wxId") String wxId);

    @Multipart
    @POST("askcar/user/updateUser.do")
    Observable<UserBean> updateUser(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("askcar/user/updateUserPassword.do")
    Observable<PasswordBean> updateUserPassword(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("askcar/user/getUserBankRecord.do")
    Observable<BankCardBean> getUserBankRecord(@Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/user/deleteBankRecord.do")
    Observable<BankCardBean> deleteBankRecord(@Field("subId") String subId);

    @Multipart
    @POST("askcar/user/addUserBankRecord.do")
    Observable<BankCardBean> addUserBankRecord(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("askcar/user/getAttentionList.do")
    Observable<GetAttentionListBean> getAttentionList(@Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/user/addTeacherRegist.do")
    Observable<AddTeacherRegistBean> addTeacherRegist(@Field("uId") String uId, @Field("file") String file,
                                                      @Field("name") String name, @Field("sex") String sex,
                                                      @Field("age") int age, @Field("address") String address,
                                                      @Field("telphone") String telphone, @Field("nowCompany") String nowCompany,
                                                      @Field("jobTime") int jobTime, @Field("jobUbdergo") String jobUbdergo,
                                                      @Field("trainUbdergo") String trainUbdergo, @Field("oldHonor") String oldHonor,
                                                      @Field("goodAtArea") String goodAtArea, @Field("selfAssessment") String selfAssessment,
                                                      @Field("bankName") String bankName, @Field("bankAddress") String bankAddress,
                                                      @Field("bankNum") String bankNum, @Field("userName") String userName,
                                                      @Field("userMobile") String userMobile);

    @FormUrlEncoded
    @POST("askcar/user/addTeacherRegist.do")
    Observable<AddRegisterShareBean> addRegisterShare(@Field("uId") String uId, @Field("name") String name, @Field("photo") String photo,
                                                      @Field("sex") String sex, @Field("age") int age, @Field("address") String address,
                                                      @Field("telphone") String telphone, @Field("nowCompany") String nowCompany,
                                                      @Field("jobTime") int jobTime, @Field("jobUbdergo") String jobUbdergo,
                                                      @Field("trainUbdergo") String trainUbdergo, @Field("honor") String honor,
                                                      @Field("goodAtArea") String goodAtArea, @Field("selfAssessment") String selfAssessment,
                                                      @Field("bankName") String bankName, @Field("bankAddress") String bankAddress,
                                                      @Field("bankNum") String bankNum, @Field("userName") String userName,
                                                      @Field("userMobile") String userMobile);

    @FormUrlEncoded
    @POST("askcar/user/addUserAttention.do")
    Observable<PlayVideoBean.DataBean.ResourceBean.CreatorBean> addUserAttention(@Field("uId") String uId, @Field("beAttentionId") String beAttentionId);

    @FormUrlEncoded
    @POST("askcar/user/deleteUserAttention.do")
    Observable<PlayVideoBean.DataBean.ResourceBean.CreatorBean> deleteUserAttention(@Field("uId") String uId, @Field("beAttentionId") String beAttentionId);

    @FormUrlEncoded
    @POST("askcar/user/deleteUserAttention.do")
    Observable<DeleteUserAttentionBean> deleteUserAttention2(@Field("uId") String uId, @Field("beAttentionId") String beAttentionId);

    @POST("askcar/userLevel/levelList.do")
    Observable<LevelListBean> levelList();

    @POST("askcar/system/getSystemInfo.do")
    Observable<GetSystemInfoBean> getSystemInfo();

    @POST("askcar/common/getCategoryList.do")
    Observable<GetCategoryListBean> getCategoryList();

    @FormUrlEncoded
    @POST("/askcar/resource/getHotList.do")
    Observable<TwoVideoListBean> getHotList(@Field("scId") String scId, @Field("pageSize") String pageSize, @Field("page") String page);

    @FormUrlEncoded
    @POST("askcar/resource/playVideo.do")
    Observable<PlayVideoBean> playVideo(@Field("rId") String rId, @Field("uId") String uId);

    @POST("askcar/appUpload/getSTSToken.do")
    Observable<StsToken> getSTSToken();

    @Multipart
    @POST("askcar/user/addTeachReason.do")
    Observable<TwoVideoListBean> addTeachReason(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("askcar/resource/getUserUpload.do")
    Observable<TwoVideoListBean> getUserUpload(@Field("uId") String uId, @Field("page") int page, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("/askcar/common/getSysArmyAnas.do")
    Observable<GetSysArmyAnasBean> getSysArmyAnas(@Field("page") String page);

    @FormUrlEncoded
    @POST("askcar/discovery/getUserQuestionList.do")
    Observable<GetUserQuestionListBean> getUserQuestionList(@Field("uId") String uId, @Field("page") int page, @Field("cId") String cId);

    @FormUrlEncoded
    @POST("askcar/discovery/getUserAnswerList.do")
    Observable<GetUserQuestionListBean> getUserAnswersList(@Field("uId") String uId, @Field("page") int page);

    @FormUrlEncoded
    @POST("askcar/discovery/getShowMoodList.do")
    Observable<GetShowMoodListBean> getShowMoodList(@Field("uId") String uId, @Field("page") int page);

    @FormUrlEncoded
    @POST("askcar/discovery/getUserQuestionAnswer.do")
    Observable<GetUserQuestionAnswerBean> getUserQuestionAnswer(@Field("qId") String qId, @Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/discovery/getShowMoodComment.do")
    Observable<GetShowMoodCommentBean> getShowMoodComment(@Field("smId") String smId);

    @Multipart
    @POST("askcar/discovery/addUserQuestion.do")
    Observable<AddUserQuestionBean> addUserQuestion(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("askcar/discovery/addShowMood.do")
    Observable<AddShowMoodBean> addShowMood(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("askcar/discovery/addUserQuestionAnswer.do")
    Observable<AddUserQuestionAnswerBean> addUserQuestionAnswer(@Field("qId") String qId, @Field("uId") String uId, @Field("rId") String rId, @Field("answerBody") String answerBody);

    @FormUrlEncoded
    @POST("askcar/discovery/addShowMoodComment.do")
    Observable<AddShowMoodCommentBean> addShowMoodComment(@Field("uId") String uId, @Field("rId") String rId, @Field("smId") String smId, @Field("cotentBody") String cotentBody);

    @FormUrlEncoded
    @POST("askcar/discovery/addQuestionAnswerAppraise.do")
    Observable<AddQuestionAnswerAppraiseBean> addQuestionAnswerAppraise(@Field("uId") String uId, @Field("uaId") String uaId);

    @FormUrlEncoded
    @POST("askcar/discovery/deleteQuestionAnswerAppraise.do")
    Observable<AddQuestionAnswerAppraiseBean> deleteQuestionAnswerAppraise(@Field("uId") String uId, @Field("uaId") String uaId);

    @FormUrlEncoded
    @POST("askcar/common/deleteUserQuestion.do")
    Observable<DeleteUserQuestionBean> deleteUserQuestion(@Field("qId") String qId);

    @FormUrlEncoded
    @POST("askcar/common/deleteShowMood.do")
    Observable<DeleteShowMoodBean> deleteShowMood(@Field("smId") String smId);

    @FormUrlEncoded
    @POST("askcar/discovery/addShowMoodAppraise.do")
    Observable<AddShowMoodAppraiseBean> addShowMoodAppraise(@Field("smId") String smId, @Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/discovery/deleteShowMoodAppraise.do")
    Observable<AddShowMoodAppraiseBean> deleteShowMoodAppraise(@Field("smId") String smId, @Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/resource/getUserBuyRecord.do")
    Observable<GetUserBuyRecordBean> getUserBuyRecord(@Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/resource/getUserBuyed.do")
    Observable<GetUserBuyedBean> getUserBuyed(@Field("uId") String uId, @Field("fcId") String fcId, @Field("page") int page);

    @FormUrlEncoded
    @POST("askcar/common/getStudyReocrd.do")
    Observable<GetStudyReocrdBean> getStudyReocrd(@Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/common/getStudyDate.do")
    Observable<StudyDateBean> getStudyDate(@Field("uId") String uId, @Field("time") String time);

    @FormUrlEncoded
    @POST("askcar/user/addDepositeRecord.do")
    Observable<AddDepositeRecordBean> addDepositeRecord(@Field("uId") String uId, @Field("total") String total, @Field("subId") String subId);

    @FormUrlEncoded
    @POST("askcar/user/getUserBalanceRecord.do")
    Observable<GetUserBalanceRecordBean> getUserBalanceRecord(@Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/pay/addUserBalance.do")
    Observable<AddUserBalanceBean> addUserBalance(@Field("payType") String payType, @Field("uId") String uId, @Field("goldCount") String goldCount, @Field("rmb") String rmb);

    @FormUrlEncoded
    @POST("askcar/pay/addUserBalance.do")
    Observable<AddUserBalanceBean2> addUserBalance2(@Field("payType") String payType, @Field("uId") String uId, @Field("goldCount") String goldCount, @Field("rmb") String rmb);

    @FormUrlEncoded
    @POST("askcar/common/getStudyDate.do")
    Observable<GetStudyDateBean> getStudyDate2(@Field("uId") String uId, @Field("time") String time);

    @FormUrlEncoded
    @POST("askcar/common/getLevalData.do")
    Observable<GetLevalDataBean> getLevalData(@Field("uId") String uId, @Field("time") String time);

    @FormUrlEncoded
    @POST("askcar/discovery/addReward.do")
    Observable<AddRewardBean> addReward(@Field("uId") String uId, @Field("uqaId") String uqaId, @Field("rewardCounts") float rewardCounts);
    @FormUrlEncoded
    @POST("askcar/resource/getTeachResource.do")
    Observable<VideoListBean> getHomeROMVideoList(@Field("scId") String scId,@Field("page") String page,@Field("endTime") String endTime
            ,@Field("orderBy") String orderBy,@Field("minTotalMinute") String minTotalMinute,@Field("maxTotalMinute") String maxTotalMinute);

    @FormUrlEncoded
    @POST("askcar/resource/getTeachResourceByfcId.do")
    Observable<TwoVideoListBean> getOneVideoList(@Field("fcId") String fcId,@Field("loginUserId") String loginUserId,@Field("page") String page);

    @FormUrlEncoded
    @POST("askcar/system/getSharedTeacherResource.do")
    Observable<VideoListBean> getShareVideoList(@Field("page") String page,@Field("endTime") String endTime,@Field("scId") String scId
    ,@Field("loginuId") String loginuId);

    @FormUrlEncoded
    @POST("askcar/common/searchByTitle.do")
    Observable<VideoListBean> search(@Field("title") String title,@Field("loginUserId") String loginUserId,@Field("page") String page);

    @FormUrlEncoded
    @POST("askcar/user/getSysUserMessage.do")
    Observable<MsgBean> getMsgList(@Field("uId") String uId, @Field("page") String page);

    @FormUrlEncoded
    @POST("askcar/user/deleteUserMessage.do")
    Observable<BaseBean> removeMsg(@Field("sumIds") String sumIds, @Field("uId") String uId);

    @FormUrlEncoded
    @POST("askcar/resource/addUserBuyRecord.do")
    Observable<BaseBean> buyVideo(@Field("uId") String uId, @Field("rId") String rId, @Field("ucId") String ucId);


    @FormUrlEncoded
    @POST("askcar/user/getRegistTeacherInfo.do")
    Observable<TeacherBean> getRegistTeacherInfo(@Field("uId") String uId);

    @Multipart
    @POST("askcar/user/updateRegistTeacher.do")
    Observable<TeacherBean> updateRegistTeacher(@PartMap Map<String,RequestBody> param);
}
