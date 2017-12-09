package com.laoodao.smartagri.api.service;

import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.bean.BaikeAllMenu;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.bean.CottonfieldComment;
import com.laoodao.smartagri.bean.CottonfieldDetail;
import com.laoodao.smartagri.bean.Discover;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.bean.Fertilizer;
import com.laoodao.smartagri.bean.FertilizerDetail;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.MicrobialFertilizer;
import com.laoodao.smartagri.bean.MicrobialFertilizerDetail;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.bean.NearbyShopDetail;
import com.laoodao.smartagri.bean.NewDrug;
import com.laoodao.smartagri.bean.NewDrugTab;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.NewsDetail;
import com.laoodao.smartagri.bean.Pesticide;
import com.laoodao.smartagri.bean.PesticideDetail;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.PriceWonder;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.Seed;
import com.laoodao.smartagri.bean.SeedDetail;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.TruthQuery;
import com.laoodao.smartagri.bean.Unipue;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by WORK on 2017/5/2.
 */

public interface DiscoverService {

    /**
     * 附近的农资店详情
     *
     * @return
     */
    @GET("store/info")
    Observable<Result<NearbyShopDetail>> getStoreInfo(@Query("id") String id, @Query("lat") String lat, @Query("lng") String lng);


    /**
     * 附近的农资店
     *
     * @param lat 经度
     * @param lng 纬度
     * @return
     */
    @GET("store/nearby")
    Observable<Page<NearbyShop>> nearbyShop(@Query("page") int page, @Query("lat") String lat, @Query("lng") String lng);


    /**
     * 添加绝活
     *
     * @param cropName
     * @param disease
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("baike/addMedicament")
    Observable<Response> releaseFormula(@Field("crop_name") String cropName, @Field("disease") String disease, @Field("content") String content, @Field("id") int id);

    /**
     * 作物详情描述
     *
     * @param id
     * @return
     */
    @GET("Baikea/baikeDetail")
    Observable<Result<NewDrugTab>> getNewDrugTab(@Query("id") int id);

    /**
     * 新闻列表
     *
     * @param page 页码
     * @param gcId 分类id
     * @param kw   搜搜关键字
     * @return
     */
    @GET("news/newsListApp")
    Observable<Page<News>> getNewsList(@Query("page") int page, @Query("gc_id") int gcId, @Query("kw") String kw);


    /**
     * 新闻详情
     *
     * @param id
     * @return
     */
    @GET("news/newsDetailApp")
    Observable<Result<NewsDetail>> getNewsDetail(@Query("id") int id);

    /**
     * 最新药剂 收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST("baikea/collect")
    Observable<Response> drugCollect(@Field("id") int id);


    /**
     * 新闻分类
     *
     * @return
     */
    @GET("menu/newsMenuApp")
    Observable<Page<MechanicalType>> getNewsMenu();

    /**
     * 上报新闻分类
     *
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST("news/setMemberNewsMenu")
    Observable<Response> postNewsMenu(@Field("gc_paths") String ids);


    /**
     * 百科详情-我有绝活
     *
     * @param id
     * @param type
     * @return
     */
    @GET("Baikea/getFormula")
    Observable<Page<Unipue>> getUnipue(@Query("id") int id, @Query("type") int type);

    /**
     * 百科详情-最新药剂
     *
     * @param id
     * @param type
     * @return
     */
    @GET("Baikea/getFormula")
    Observable<Result<List<NewDrug>>> getNewDrug(@Query("id") int id, @Query("type") int type);


    /*
         * 真伪-种子搜索
         *
         * @param number
         * @param category
         * @param variety
         * @return
         */
    @GET("findinfo/seed")
    Observable<Page<TruthQuery>> infoSeed(@Query("page") int page, @Query("number") String number, @Query("category") String category, @Query("variety") String variety);

    /**
     * 真伪-化肥搜索
     *
     * @param page
     * @param number
     * @param commonName
     * @param company
     * @return
     */
    @GET("findinfo/fertilizer")
    Observable<Page<TruthQuery>> infoFertilizer(@Query("page") int page, @Query("number") String number, @Query("common_name") String commonName, @Query("company") String company);

    /**
     * 真伪-微生物肥搜索
     *
     * @param page
     * @param number
     * @param commonName
     * @param company
     * @return
     */
    @GET("findinfo/microbialFertilizer")
    Observable<Page<TruthQuery>> infoMicrobialFertilizer(@Query("page") int page, @Query("number") String number, @Query("common_name") String commonName, @Query("company") String company);

    /**
     * 真伪-农药搜索
     *
     * @param number
     * @param activePrinciple
     * @param dose
     * @param manufacturer
     * @return
     */
    @GET("findinfo/pesticide")
    Observable<Page<TruthQuery>> infoPesticide(@Query("page") int page, @Query("number") String number, @Query("active_principle") String activePrinciple, @Query("dose") String dose, @Query("manufacturer") String manufacturer);

    /**
     * 真伪-微生物肥详情
     *
     * @param id
     * @return
     */
    @GET("findinfo/microbialFertilizerDetail")
    Observable<Result<MicrobialFertilizerDetail>> getMicrobialFertilizerDetail(@Query("id") int id);

    /**
     * 真伪-种子详情
     *
     * @param id
     * @return
     */
    @GET("findinfo/seeddetail")
    Observable<Result<SeedDetail>> infoSeeddetail(@Query("id") int id);

    /**
     * 真伪-农药详情
     *
     * @param id
     * @return
     */
    @GET("findinfo/pesticideDetail")
    Observable<Result<PesticideDetail>> infoPesticideDetail(@Query("id") int id);

    /**
     * 真伪-化肥详情
     *
     * @param id
     * @return
     */
    @GET("findinfo/fertilizerDetail")
    Observable<Result<FertilizerDetail>> infoFertilizerDetail(@Query("id") int id);

    /**
     * 百科分类
     *
     * @return
     */
    @GET("baikea/cateList")
    Observable<Result<CateList>> baikeCateList();

    /**
     * 百科文章列表
     *
     * @param page
     * @param cropId
     * @param categoryId
     * @return
     */
    @GET("baikea/index")
    Observable<Page<Baike>> baikeIndex(@Query("page") int page, @Query("crop_id") int cropId, @Query("category_id") int categoryId);

    @GET("baikea/index")
    Observable<Page<Baike>> baikeIndex(@Query("page") int page, @Query("kw") String kw);

    /**
     * 百科菜单
     *
     * @return
     */
    @GET("menu/baikeMenuApp")
    Observable<Result<BaikeMenu>> getBaikeMenu(@Query("type") int type);

    /**
     * 百科菜单所有
     *
     * @param type
     * @return
     */
    @GET("menu/baikeMenuApp")
    Observable<Result<BaikeAllMenu>> getBaikeAllMenu(@Query("type") int type);

    /**
     * 自定义百科分类
     *
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST("baikea/setMemberBaikeMunu")
    Observable<Response> setMemberBaikeMunu(@Field("gc_paths") String ids);

    /**
     * 真伪-全局搜索
     *
     * @param page
     * @param tag
     * @return
     */
    @GET("findinfo/search")
    Observable<Page<TruthQuery>> infoSearch(@Query("page") int page, @Query("tag") String tag);

    /**
     * 发现菜单
     *
     * @return
     */
    @GET("baikea/discovercat")
    Observable<Result<List<Discovercat>>> discoverMenu(@Query("type") int type);

    /**
     * 后台接口 棉花账单
     *
     * @return
     */
    @FormUrlEncoded
    @POST("cottonfield/cottonFieldStat")
    Observable<Result<CottonBill>> cottonBill(@Field("card_no") String cardNo, @Field("info") String info);

    /**
     * 账单首页
     *
     * @return
     */
    @GET("cottonfield/index")
    Observable<Result<String>> cottonIndex();

    /**
     * 棉田分享回调
     *
     * @return
     */
    @GET("share/cottonfieldback")
    Observable<Result<String>> cottonFieldBack();

    /**
     * 价格行情列表
     *
     * @param city     城市id
     * @param cateId   分类id
     * @param page     页码
     * @param typeTime 时间分类id
     * @return
     */
    @GET("cottonfield/lists")
    Observable<Page<PriceQuotation>> getPriceQuotation(@Query("category_id") int cateId, @Query("page") int page, @Query("time_type") int typeTime, @Query("keyword") String keyword, @Query("pos") String pos, @Query("area_id") int areadId);

    /**
     * 作物分类
     *
     * @return
     */
    @GET("cottonfield/category")
    Observable<Result<List<SupplyMenu>>> cropCategory();

    /**
     * 棉田系统推荐关注
     *
     * @return
     */
    @GET("cottonfield/recommendWonder")
    Observable<Result<List<PriceWonder>>> recommendWonder();

    /**
     * 添加/取消棉田关注
     *
     * @param id
     * @return
     */
    @GET("cottonfield/wonder")
    Observable<Response> addCottonFieldWonder(@Query("id") int id);

    /**
     * 我的关注
     *
     * @return
     */
    @GET("cottonfield/myWonder")
    Observable<Result<List<PriceWonder>>> CottonMyWonder();

    /**
     * 价格查询时间分类
     *
     * @return
     */
    @GET("menu/priceDate")
    Observable<Result<List<SupplyMenu>>> priceDate();


    /**
     * 价格行情详情
     *
     * @param id
     * @return
     */
    @GET("cottonfield/detail")
    Observable<Result<CottonfieldDetail>> priceDetail(@Query("id") int id);

    /**
     * 价格详情评论列表
     *
     * @param id
     * @return
     */
    @GET("cottonfield/comment")
    Observable<Page<CottonfieldComment>> getComment(@Query("id") int id, @Query("page") int page);

    /**
     * 价格详情评论
     *
     * @param id
     * @param content
     * @return
     */
    @GET("cottonfield/addComment")
    Observable<Result<CottonfieldComment>> addComment(@Query("id") int id, @Query("content") String content);


    /**
     * 是否有消息
     *
     * @return
     */
    @GET("cottonfield/ismessage")
    Observable<Result> isNewMessage();


    /**
     * 价格行情-热搜
     *
     * @return
     */
    @GET("cottonfield/hostWords")
    Observable<Result<List<Keyword>>> hotKeyword();

    /**
     * 添加/取消农资店关注
     *
     * @return
     */
    @GET("store/addwonder")
    Observable<Response> addStroeFollow(@Query("store_id") String id);


}
