package com.laoodao.smartagri.common;

import com.laoodao.smartagri.BuildConfig;

/**
 * Created by Administrator on 2017/4/6.
 */

public class Constant {
    public static final String WEB_SHOP_SITE = BuildConfig.APP_HTML_URL;

    public static final String BASE_URL = "";

    //    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";
    public static final String COMMIT_COTTON = "http://xinjiang.cottech.com/am/queryPersonalReportPublic";//棉花查询
    public static final String ENTERPRISE="http://xinjiang.cottech.com/am/queryEnterprisePublic";//企业查询
    public static final String COTTON_VERIFICATION_CODE = "http://xinjiang.cottech.com/am/createValidateCode";//获取棉花查询验证码

    public static final String HUAN_XIN_PWD="huan_xin_pwd";
    public static final String SCRATCH_CARD="/wap/scratch_card/scratch_card";
    public static final String PRICE_QUOTATION="price_quotation";
    public static final String TAB = "tab";
    public static final String WECHAT = "wechat";
    public static final String QQ_SMALL = "qq";
    public static final String TYPE = "type";
    public static final String REG = "reg";           //发送验证码类型 reg=注册
    public static final String FPW = "fpw";     //发送验证码类型 fpw=找回密码
    public static final String CHM = "chm";       //修改手机号码
    public static final String USER_INFO = "userInfo";
    public static final String COTTON_BILL="cottonBill";
    public static final String SELECTED_CITY = "selected_location";
    public static final String CURRENT_LOCATION = "current_location";
    public static final String COTTON = "cotton";
    public static final int NOT_ACCEPT_PUSH = 0;      //不接收推送
    public static final int ACCEPT_PUSH = 1;          //接受推送
    public static final int EXPENDITURE = 1;           //支出
    public static final int INCOME = 2;                //收入
    public static final int PAY_SU = 1;                //已付款
    public static final int PAY_UN = 2;                 //未付款
    public static final int MENU_TYPE = 0;                //分类
    public static final int ADDRESS_TYPE = 1;             //地区分类
    public static final int TIME_TYPE = 2;                //日期分类
    public static final int WANT_BUY = 2;                 //求购
    public static final int SUPPLY = 1;                   //供销
    public static final int REGISTER_ACCOUNT = 1;         //注册新账号
    public static final int JOINT_ACCOUNT = 2;               //关联账号
    public static final int ALL = 1;                          //全部订单
    public static final int PENDING_CONFIRMATION = 2;       //待确认
    public static final int PENDING_REPAYMENT = 3;           //待还款
    public static final int ALREADY_COMPLETED = 4;            //已完成
    public static final int NO_SETTLE = 0;                    //待付款
    public static final int IN_SETTLE = 1;                    //正在付款
    public static final int SU_SETTLE = 2;                    //完成付款
    public static final int UNIPUE = 2;                       //我有绝活
    public static final int NEW_DRUG = 1;                     //最新药剂
    public static final int ARREARS = 2;                       //欠款
    public static final int REPAYMENT = 3;                     //还款中



    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CROP_BIG_PICTURE = 0xa2;
    public static final int CODE_NICKNAME = 0xa3;
    public static final int CODE_PHONE = 0xa4;

    public static final String TOKEN = "token";

    public static final String LAST_VERSION_CODE = "lastVersionCode";

    public static final int SU_BIND = 1;
    public static final int UN_BIND = 2;

    public static final String SUPPLY_TAG="supply";//求购/供销


    public static final String HOME_NEWS_LIST_PAGE_ = "home_news_list_page_";//首页新闻列表缓存key
    public static final String MY_ANSWER_LIST_ = "my_answer_list_"; //我的回答列表缓存key
    public static final String NEW_MY_ASK_ = "new_my_ask_";//我的提问列表缓存key
    public static final String NEW_QUESTION_LIST_ = "new_question_list_";//最新问题列表缓存key
    public static final String NEW_FOLLOW_CONTENT_ = "new_follow_content_";//问题关注列表缓存key
    public static final String WONDER_ANSWER_LIST_ = "wonder_answer_list_";//想知道答案列表缓存key
    public static final String BUY_DATE_TYPE = "buy_date_type";//供求时间分类缓存key
    public static final String BUY_MENU_TYPE = "buy_menu_type";//供求分类菜单缓存key
    public static final String BUY_LIST = "buy_list";//求购列表缓存key
    public static final String SALES_LIST = "sales_list";//供销列表缓存key
    public static final String SALES_DATA_TYPE = "sales_data_type";//供销时间分类缓存key
    public static final String SALES_MENU_TYPE = "sales_menu_type";//供销分类菜单缓存key
    public static final String RELEASE_BUY = "release_buy";//发布求购缓存key
    public static final String RELEASE_SUPPLY = "release_supply";//发布供销缓存key
    public static final String COLLECTION_BUY = "collection_buy";//我的求购收藏缓存key
    public static final String COLLECTION_SALES = "collection_sales";//我的供销收藏缓存key
    public static final String BROWSE_BUY = "browse_buy";//求购浏览缓存key
    public static final String BROWSE_SALES = "browse_sales";//供销浏览缓存key
    public static final String DISCOVER_LIST_ = "discover_list_";// 发现列表缓存key
    public static final String USER_MENU = "user_menu";// 个人中心菜单缓存key
    public static final String USER_INFO_LIST = "user_info_list";//用户信息缓存key
    public static final String INTEGRAL_DETAILED = "integral_detailed";//积分明细缓存key
    public static final String MY_LOAN = "my_loan";//我的贷款缓存key
    public static final String TRANSACTION_RECORD = "transaction_record";//贷款记录缓存key
    public static final String LOAD_RECORD = "load_record";//交易记录缓存key
    public static final String IDEA_FEEDBACK = "idea_feedback";//意见反馈缓存key
    public static final String LAOODAO_SHARE = "laoodao_share";//劳道分享缓存key
    public static final String LAOODAO_SHARE_LIST = "laoodao_share_list";//劳道分享列表缓存key
    public static final String DISCOVER_MENU = "discover_menu"; // 发现列表缓存key
    public static final String COTTON_BILL_IMG = "cotton_bill_img"; // 棉田账单首页图片缓存key
}
