package com.laoodao.smartagri.api.service;

import com.laoodao.smartagri.bean.AccountList;
import com.laoodao.smartagri.bean.Farmland;
import com.laoodao.smartagri.bean.FarmlandDetailInfo;
import com.laoodao.smartagri.bean.FarmlandDetail;
import com.laoodao.smartagri.bean.LedgerStatistics;

import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.OperationDetail;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.base.AccountDetail;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.base.Response;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by WORK on 2017/4/24.
 */

public interface FarmlandService {
    /**
     * 我的农田
     *
     * @param page
     * @return
     */
    @GET("farm/index")
    Observable<Page<Farmland>> getFarmlandList(@Query("page") int page);


    /**
     * 添加农田
     *
     * @param body
     * @return
     */
    @POST("farm/addFarm")
    Observable<Response> addFarmland(@Body RequestBody body);


    /**
     * 记一笔分类
     */
    @GET("menu/accType")
    Observable<Result<List<RecordClassification>>> getClassification(@Query("type") int type);

    /**
     * 农田详情头部信息
     *
     * @return
     */
    @GET("farm/info")
    Observable<Result<FarmlandDetailInfo>> farmlandDetail(@Query("id") int id);

    /**
     * 农田详情列表
     *
     * @param page
     * @return
     */
    @GET("farm/operateList")
    Observable<Page<FarmlandDetail>> farmlandDetailList(@Query("page") int page, @Query("id") int id);


    /**
     * 新增/编辑 操作
     *
     * @return
     */
    @POST("farm/addOperate")
    Observable<Response> addOperation(@Body RequestBody body);

    /**
     * 账本统计
     */
    @GET("farm/myAccount")
    Observable<Result<LedgerStatistics>> getLedgerStatistics();

    /**
     * 账本列表
     */
    @GET("farm/accountList")
    Observable<Page<AccountList>> getAccountList(@Query("page") int page);

    /**
     * 账单详情
     */
    @GET("farm/accountDetail")
    Observable<Result<AccountDetail>> getAccountDetail(@Query("id") int id);

    /**
     * 删除账本记录
     */
    @GET("farm/accountDel")
    Observable<Response> deleteAccount(@Query("id") int id);

    /**
     * 机械种类分类
     *
     * @return
     */
    @GET("farm/macType")
    Observable<Result<List<MechanicalType>>> mechanicalType();


    /**
     * 农田详情
     * @return
     */
    @GET("farm/operateDetail")
    Observable<Result<OperationDetail>> operationDetail(@Query("id") int id);

    /**
     * 记一笔 记账
     * @param body
     * @return
     */
    @POST("farm/addAccount")
    Observable<Response> accounting(@Body RequestBody body);

}
