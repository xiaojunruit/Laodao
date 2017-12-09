package com.laoodao.smartagri.api.service;

import com.laoodao.smartagri.bean.ChatGoodsInit;
import com.laoodao.smartagri.bean.Collection;
import com.laoodao.smartagri.bean.Enterprise.Enterprise;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddress;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyArea;
import com.laoodao.smartagri.bean.SupplyDetail;
import com.laoodao.smartagri.bean.SupplyMy;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.cotton.Cotton;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface MarketService {


    /**
     * 供求列表
     * type 类型 1供销 2求购
     * keywords 关键字 catagory 1农副产品 2农机 3转让土地
     * area_id 区域id
     * publishtime 发布时间 0不限 1最近三天 2最近七天 3最近三十天 99 三十天以上
     *
     * @return
     */
//    @GET("supply/lists")
//    Observable<Page<Supplylists>> getSupplylists(@Query("page") int page, @Query("type") int type
//            , @Query("keywords") String keywords, @Query("catagory") int catagory, @Query("area_id") int area_id
//            , @Query("area_id1") int area_id1, @Query("area_id2") int area_id2
//            , @Query("area_id3") int area_id3, @Query("area_id4") int area_id4
//            , @Query("publishtime") int publishtime);
    @GET("supply/lists")
    Observable<Page<Supplylists>> getSupplylists(@Query("page") int page, @Query("type") int type, @Query("keywords") String keyword, @Query("topcategory") String category,
                                                 @Query("area_id") String areaid, @Query("addtime") String timeId);

    @GET("supply/lists")
    Observable<Page<Supplylists>> getSupplylists(@Query("page") int page, @Query("keywords") String keywords);

    /**
     * 添加搜索历史
     *
     * @param body
     * @return
     */
    @POST("Supply/addSearch")
    Observable<Response> addSearch(@Body RequestBody body);

    /**
     * 删除搜索历史
     *
     * @param
     * @return
     */
    @GET("Supply/delSearch")
    Observable<Response> delSearch(@Query("id") int id);

    /**
     * 供求热搜
     *
     * @return
     */
    @GET("Supply/hostWords")
    Observable<Result<List<Keyword>>> getHostWords();

    /**
     * 我的搜索历史
     *
     * @return
     */
    @GET("Supply/searchHistory")
    Observable<Result<List<Keyword>>> getMySearchWords();


    /**
     * 选择地址
     *
     * @return
     */
    @GET("supply/getArea")
    Observable<Result<List<SupplyArea>>> supplyArea(@Query("level") int level, @Query("con") String con);


    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GET("Supply/detail")
    Observable<Result<SupplyDetail>> getSupplyDetail(@Query("id") int id);

    /**
     * 添加收藏
     *
     * @param itemId
     * @return
     */
    @GET("Supply/Collect")
    Observable<Response> addCollect(@Query("item_id") int itemId);

    /**
     * 取消收藏
     *
     * @param itemId
     * @return
     */
    @GET("Supply/delCollect")
    Observable<Response> delCollect(@Query("item_id") int itemId);

    /**
     * 我的收藏
     *
     * @param page
     * @param type
     * @return
     */
    @GET("Supply/myCollect")
    Observable<Page<Collection>> getMyCollect(@Query("page") int page, @Query("type") int type);

    /**
     * 我的浏览
     *
     * @param page
     * @param type
     * @return
     */
    @GET("Supply/viewList")
    Observable<Page<Collection>> getView(@Query("page") int page, @Query("type") int type);


    /**
     * 发布供求
     *
     * @param body
     * @return
     */
    @POST("Supply/add")
    Observable<Response> addSupply(@Body RequestBody body);

    /**
     * 编辑供求
     *
     * @param body
     * @return
     */
    @POST("Supply/edit")
    Observable<Response> editSupply(@Body RequestBody body);


    /**
     * 我的供销
     *
     * @param pgae
     * @param type
     * @return
     */
    @GET("Supply/my")
    Observable<Page<SupplyMy>> getSupplyMy(@Query("page") int pgae, @Query("type") int type);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GET("Supply/del")
    Observable<Response> delSupply(@Query("id") int id);

    /**
     * 供求分类
     *
     * @return
     */
    @GET("menu/supply")
    Observable<Result<List<SupplyMenu>>> getMenu(@Query("type") int id);


    /**
     * 时间分类
     *
     * @return
     */
    @GET("menu/supplyDate")
    Observable<Result<List<SupplyMenu>>> supplyDate();

    /**
     * 棉花查询
     *
     * @param url
     * @param searchCode
     * @param validateCode
     * @param srarchYear
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<Cotton> cottonSelect(@Url String url, @Field("searchCode") String searchCode, @Field("validateCode") String validateCode, @Field("searchYear") String srarchYear);

    /**
     * 企业查询
     *
     * @param url
     * @param enterprisecode 企业代码
     * @param validateCode   验证码
     * @param enterprisename 企业名称
     * @param ASCRIPTIONA    所属地州
     * @param ASCRIPTIONB    所属市县
     * @param searchYear     年份
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<Enterprise> enterpriseSelect(@Url String url, @Field("enterprisecode") String enterprisecode, @Field("validateCode") String validateCode, @Field("enterprisename") String enterprisename, @Field("ASCRIPTIONA") String ASCRIPTIONA, @Field("ASCRIPTIONB") String ASCRIPTIONB, @Field("searchYear") String searchYear);

    /**
     * 企业地址查询
     *
     * @return
     */
    @GET("findinfo/getCottonArea")
    Observable<Result<List<EnterpriseAddress>>> getCottonArea();


    /**
     * 拨打电话添加日志
     *
     * @return
     */
    @FormUrlEncoded
    @POST("supply/addtellog")
    Observable<Response> addTellog(@Field("id") int id);

    /**
     * 初始化商品信息
     *
     * @return
     */
    @GET("emchat/chatInit")
    Observable<Result<ChatGoodsInit>> getChatGoodsInit(@Query("id") String id, @Query("tag") String tag);
}
