package com.laoodao.smartagri.api.service;

import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.Pos;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Response;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.weather.Weather;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by WORK on 2017/4/28.
 */

public interface HomeService {
    /**
     * 首页
     *
     * @return
     */
    @GET("ld/index")
    Observable<Result<Home>> homeMenu(@Query("city") String cityId, @Query("pos") String pos);

    /**
     * 首页资讯
     *
     * @param page
     * @return
     */
    @GET("ld/indexNews")
    Observable<Page<News>> homeNees(@Query("page") int page);


    @GET("ld/winfo")
    Observable<Result<Weather>> getWeather(@Query("city") String city, @Query("lon") String lon, @Query("lat") String lat);


    @GET("ld/pos")
    Observable<Result<Pos>> getPos(@Query("pos") String location);

    /**
     * 初始化新闻分类
     *
     * @return
     */
    @GET("menu/initmenu")
    Observable<Result<Map<String,String>>> getInitMenu();

    /**
     * 刮刮乐分享回调
     *
     * @return
     */
//    @GET("share/gglcallback")
//    Observable<Response> shareBack();

    /**
     * 分享回调
     * @param tag	ask:为问题分享，supply 为求购/供销 分享，farm:农田分享，news:资讯分享,cottonfield:棉田账单：ggl:刮刮乐,invite:注册分享
     * @param id
     * @return
     */
    @GET("share/sharecallback")
    Observable<Result<String>> shareBack(@Query("tag") String tag,@Query("id") int id);

}
