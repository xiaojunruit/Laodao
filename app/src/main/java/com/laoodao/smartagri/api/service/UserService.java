package com.laoodao.smartagri.api.service;

import com.google.gson.annotations.SerializedName;
import com.laoodao.smartagri.bean.AskWonderList;
import com.laoodao.smartagri.bean.CerifyInfo;
import com.laoodao.smartagri.bean.CerifyMenu;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.bean.HistoryRecord;
import com.laoodao.smartagri.bean.IntegralRule;
import com.laoodao.smartagri.bean.IntegralTask;
import com.laoodao.smartagri.bean.InviteInfo;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.InviteList;
import com.laoodao.smartagri.bean.LoanRecord;
import com.laoodao.smartagri.bean.MsgDetail;
import com.laoodao.smartagri.bean.MyLoan;
import com.laoodao.smartagri.bean.MyOrder;
import com.laoodao.smartagri.bean.MyOrderDetail;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.bean.PaymentHistory;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.RepaymentOrder;
import com.laoodao.smartagri.bean.ReturnGoods;
import com.laoodao.smartagri.bean.ReturnGoodsDetail;
import com.laoodao.smartagri.bean.SignIn;
import com.laoodao.smartagri.bean.Suggestion;
import com.laoodao.smartagri.bean.TradeRecord;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.UserHomePageData;
import com.laoodao.smartagri.bean.UserHomePageHead;
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.bean.UserSupplyCollection;
import com.laoodao.smartagri.bean.WonderStore;
import com.laoodao.smartagri.bean.WonderUser;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface UserService {

    /**
     * 登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("login/index")
    Observable<Result<User>> login(@Field("username") String username, @Field("password") String password, @Field("ClientID") String pushId);

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @param rule   规则 reg:注册，fpw:找回密码
     * @return
     */
    @FormUrlEncoded
    @POST("login/getcode")
    Observable<Response> sendCode(@Field("mobile") String mobile, @Field("rule") String rule);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @return
     */
    @FormUrlEncoded
    @POST("login/register")
    Observable<Result<User>> register(@Field("username") String username, @Field("password") String password, @Field("code") String code);

    /**
     * 修改密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @param rePassword  确认密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/changePassword")
    Observable<Response> updatePassword(@Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword, @Field("re_newPassword") String rePassword);


    /**
     * 找回密码-验证
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return
     */
    @FormUrlEncoded
    @POST("login/checkFindCode")
    Observable<Result<Map<String, String>>> validateCode(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 找回密码-找回
     *
     * @param token      token
     * @param password   新密码
     * @param rePassword 重复新密码
     * @return
     */
    @FormUrlEncoded
    @POST("login/setPassword")
    Observable<Result<User>> findPassword(@Field("token") String token, @Field("password") String password, @Field("re_password") String rePassword);

    /**
     * 退出登录
     *
     * @return
     */
    @GET("login/logout")
    Observable<Response> logout();

    //意见反馈->意见类型

    /**
     * 设置头像
     *
     * @param avatar
     * @return
     */
    @Multipart
    @POST("user/modify")
    Observable<Result<Map<String, String>>> updateAvatar(@Part("avatar\";filename=\"avatar.jpeg") RequestBody avatar);

    /**
     * 设置是否推送
     *
     * @param isPush 0 不推送 1 推送
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> settingPush(@Field("is_receive_push") int isPush);

    /**
     * 修改用户所在地
     *
     * @param areaId
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> updateArea(@Field("area_id") String areaId);

    /**
     * 修改个性签名
     *
     * @param signature
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> updateSignature(@Field("signature") String signature);

    /**
     * 意见反馈 标签
     *
     * @return
     */
    @GET("menu/getSuggestion")
    Observable<Result<List<Suggestion>>> suggestionList();


    /**
     * 个人中心->用户菜单
     *
     * @return
     */
    @GET("menu/my")
    Observable<Result<List<UserMenu>>> getUserMenu();

    /**
     * 意见反馈-添加意见
     */
    @POST("user/addSuggest")
    Observable<Response> addSuggest(@Body RequestBody body);

    /**
     * 附近的农资店反馈
     */
    @FormUrlEncoded
    @POST("user/addSuggest")
    Observable<Response> addSuggest(@Field("suggestion") String content, @Field("type") int type, @Field("store_id") int id, @Field("mobile") String phone);

    /**
     * 个人中心
     */
    @GET("user/index")
    Observable<Result<User>> getUserInfo();

    /**
     * 修改昵称
     *
     * @param nickname 昵称
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> updateNickName(@Field("nickname") String nickname);

    /**
     * 性别
     *
     * @param sex 0 保密 1 男 2 女
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> updateSex(@Field("sex") int sex);

    /**
     * 邀请信息
     *
     * @return
     */
    @GET("user/InviteInfo")
    Observable<Result<InviteInfo>> inviteInfo();

    /**
     * 邀请列表
     *
     * @param page
     * @return
     */
    @GET("user/invitelist")
    Observable<Page<InviteList>> inviteList(@Query("page") int page);

    /**
     * 积分明细
     *
     * @param page
     * @return
     */
    @GET("user/pointList")
    Observable<Page<IntegralDetail>> getIntegralDetail(@Query("page") int page);


    /**
     * 我的贷款
     *
     * @return
     */
    @GET("loan/index")
    Observable<Result<MyLoan>> myLoan();


    /**
     * 贷款记录
     *
     * @return
     */
    @GET("loan/myLoan")
    Observable<Result<LoanRecord>> loanRecord();

    /**
     * 交易记录
     *
     * @return
     */
    @GET("loan/tradeList")
    Observable<Page<TradeRecord>> transactionRecordList(@Query("page") int page);

    /**
     * 交易记录详情
     *
     * @return
     */
    @GET("loan/tradeDetail")
    Observable<Result<TradeRecord>> tradeRecordDetial(@Query("id") int id);

    /**
     * 签到
     *
     * @return
     */
    @GET("sign/index")
    Observable<Response> signIn();

    /**
     * 签到信息
     *
     * @return
     */
    @GET("sign/signInfo")
    Observable<Result<SignIn>> signInInfo(@Query("search_year") int year, @Query("search_month") int month);

    /**
     * 第三方登录接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("login/loginThird")
    Observable<Result<User>> login3rd(@Field("platform") String platform, @Field("openId") String openId,
                                      @Field("token") String token, @Field("ClientID") String clientId);

    /**
     * 第三方的注册
     *
     * @param platform
     * @param openId
     * @param token
     * @param clientId
     * @param type     类型1：注册新账号 2：关联已有账号
     * @param mobile   //手机号码
     * @param code     //验证码
     * @param pwd      //密码
     * @return
     */
    @FormUrlEncoded
    @POST("login/loginThird")
    Observable<Result<User>> login3rd(@Field("platform") String platform, @Field("openId") String openId,
                                      @Field("token") String token, @Field("ClientID") String clientId,
                                      @Field("type") int type, @Field("mobile") String mobile, @Field("code") String code,
                                      @Field("pwd") String pwd);

    /**
     * 交易发送验证码
     *
     * @param id 交易记录id
     * @return
     */
    @FormUrlEncoded
    @POST("loan/sendCode")
    Observable<Response> tradeCode(@Field("id") int id);


    /**
     * 交易付款
     *
     * @param id   交易记录id
     * @param code 验证码
     * @return
     */
    @FormUrlEncoded
    @POST("loan/payOrder")
    Observable<Response> tradePay(@Field("id") int id, @Field("code") String code);


    /**
     * 修改手机号码
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> updatePhone(@Field("mobile") String phone, @Field("code") String code);


    /**
     * 消息列表
     *
     * @param page
     * @param type
     * @return
     */
    @GET("message/myMessage")
    Observable<Page<Notice>> getMyMessage(@Query("page") int page, @Query("type") int type);

    /**
     * 是否有新消息
     *
     * @return
     */
    @GET("message/index")
    Observable<Result<NewMessage>> isNewMessage();


    /**
     * 我的订单列表
     *
     * @return
     */
    @GET("purchase/index")
    Observable<Page<MyOrder>> myOrder(@Query("page") int page, @Query("type") int type);


    /**
     * 我的订单详情
     *
     * @param id
     * @return
     */
    @GET("purchase/orderDetail")
    Observable<Result<MyOrderDetail>> myOrderDetail(@Query("id") int id);

    /**
     * 历史记录
     *
     * @param id
     * @return
     */
    @GET("purchase/orderOperRecord")
    Observable<Result<HistoryRecord>> historyRecord(@Query("id") int id);

    /**
     * 获取最新积分数据
     *
     * @return
     */
    @GET("ld/points")
    Observable<Result<String>> getPoints();

    /**
     * 第三方绑定
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/thirdBind")
    Observable<Result<Map<String, String>>> bindThird(@Field("platform") String platform, @Field("openId") String openId, @Field("bind_token") String token, @Field("type") int type);

    /**
     * 分享成功加积分
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("share/shareSucc")
    Observable<Response> shareSuccess(@Field("id") int id);


    /**
     * 积分规则
     *
     * @return
     */
    @GET("Sign/pointsRules")
    Observable<Result<List<IntegralRule>>> pointsRule();


    /**
     * 赚积分
     *
     * @return
     */
    @GET("Sign/earnPoints")
    Observable<Result<List<IntegralTask>>> earnPoints();


    /**
     * 消息详情
     *
     * @return
     */
    @GET("message/systemNoticeDetail")
    Observable<Result<MsgDetail>> getMsgDetail(@Query("id") int id, @Query("obj_type") String obj_type);

    /**
     * 清空消息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("message/flushall")
    Observable<Response> clearMsg(@Field("message_type") int msgType);

    /**
     * 读消息接口
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("message/readMessage")
    Observable<Result> readMsg(@Field("id") int id);

    /**
     * 我的订单列表
     *
     * @return
     */
    @GET("purchase/myOrder")
    Observable<Page<MyOrder>> getMyOrder(@Query("page") int page, @Query("type") int type);

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @GET("purchase/orderDetails")
    Observable<Result<MyOrderDetail>> getOrderDetail(@Query("id") int id);


    /**
     * 还款记录
     *
     * @param page
     * @return
     */
    @GET("purchase/repayRecord")
    Observable<Page<PaymentHistory>> requestPaymentHistory(@Query("page") int page);

    /**
     * 退货退款记录
     *
     * @param page
     * @return
     */
    @GET("purchase/salesRetList")
    Observable<Page<ReturnGoods>> returnGoodsList(@Query("page") int page);

    /**
     * 还款订单
     *
     * @param id
     * @return
     */
    @GET("purchase/repayOrder")
    Observable<Result<RepaymentOrder>> requestRepaymentOrder(@Query("id") int id);

    /**
     * 退款详情
     *
     * @param id
     * @return
     */
    @GET("purchase/salesRetDetail")
    Observable<Result<ReturnGoodsDetail>> requestReturnGoodsDetail(@Query("id") int id);


    /**
     * 添加用户关注
     *
     * @param memberId
     * @return
     */
    @GET("usercenter/addWonder")
    Observable<Response> addWonfer(@Query("member_id") int memberId);

    /**
     * 用户关注列表
     *
     * @param memberId
     * @return
     */
    @GET("usercenter/wonderUser")
    Observable<Page<WonderUser>> wonderUser(@Query("member_id") int memberId, @Query("page") int page, @Query("type") int type);

    /**
     * 个人主页头部
     *
     * @param id
     * @return
     */

    @GET("usercenter/index")
    Observable<Result<UserHomePageHead>> userHomePageHeader(@Query("member_id") int id);

    /**
     * 个人主页列表
     *
     * @param id
     * @param type
     * @return
     */
    @GET("usercenter/dynamicList")
    Observable<Page<UserHomePageData>> userHomePageData(@Query("page") int page, @Query("member_id") int id, @Query("type") int type);

    /**
     * 发送消息上报
     *
     * @return
     */
    @POST("emchat/send")
//    Observable<Response> setSendContent(@Field("tag") String tag,@Field("type") int type,@Field("to_member_name") String toName,@Field("content") String content,@Field("image") String image,@Field("voice") String voice);
    Observable<Response> setSendContent(@Body RequestBody body);

    /**
     * 认证菜单
     *
     * @return
     */
    @GET("menu/cerifyMenu")
    Observable<Result<CerifyMenu>> getCerifyMenu();

    /**
     * 关注的农资店
     *
     * @return
     */
    @GET("usercenter/wonderStore")
    Observable<Page<WonderStore>> getWonderStore(@Query("page") int pages, @Query("member_id") String memberId);

    /**
     * 添加或取消关注农资店
     *
     * @param store_id
     * @return
     */
    @GET("store/addwonder")
    Observable<Response> addOrDelStore(@Query("store_id") String store_id);

    /**
     * 获取认证信息
     *
     * @param memberId
     * @return
     */
    @GET("usercenter/getCerifyInfo")
    Observable<Result<CerifyInfo>> getCerifyInfo(@Query("member_id") String memberId);

    /**
     * 添加用户认证
     *
     * @param body
     * @return
     */
    @POST("usercenter/addCerify")
    Observable<Response> addCerify(@Body RequestBody body);

    /**
     * 获取头像
     *
     * @param uids
     * @return
     */
    @GET("emchat/getAvatar")
    Observable<Result<List<ChatAvatar>>> getAvatar(@Query("uids") String uids);

    /**
     * 问题收藏
     *
     * @param memberId
     * @param page
     * @return
     */
    @GET("usercenter/askWonderList")
    Observable<Page<AskWonderList>> askWonderList(@Query("member_id") int memberId, @Query("page") int page);

    /**
     * 供销求购收藏
     *
     * @param memberId
     * @param type
     * @param page
     * @return
     */
    @GET("usercenter/sdCollect")
    Observable<Page<UserSupplyCollection>> sdCollect(@Query("member_id") int memberId, @Query("type") int type, @Query("page") int page);

    /**
     * 修改真实姓名
     *
     * @param trueName
     * @return
     */
    @FormUrlEncoded
    @POST("user/modify")
    Observable<Response> updateTrueName(@Field("true_name") String trueName);

    /**
     * 关注的价格行情
     *
     * @param memberId
     * @return
     */
    @GET(" usercenter/wonderPrice")
    Observable<Result<List<PriceWonder>>> wonderPrice(@Query("member_id") int memberId);

}
