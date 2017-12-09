package com.laoodao.smartagri.api.service;

import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.bean.AnswerReply;
import com.laoodao.smartagri.bean.AskSuccess;
import com.laoodao.smartagri.bean.Crop;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.MyAnswer;
import com.laoodao.smartagri.bean.Ask;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.ReplySuccess;
import com.laoodao.smartagri.bean.WonderAnswer;
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
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by WORK on 2017/4/24.
 */

public interface QAService {

    /**
     * 我的提问
     *
     * @param page 页数
     * @return
     */
    @GET("ask/my")
    Observable<Page<Ask>> getMyAskList(@Query("page") int page);

    /**
     * 我的回答
     *
     * @param page
     * @return
     */
    @GET("ask/myAnswer")
    Observable<Page<MyAnswer>> showMyAnswerList(@Query("page") int page);

    /**
     * 关注问题
     *
     * @param id
     * @return
     */
    @GET("ask/addWonder")
    Observable<Result<Map<String, String>>> addWonder(@Query("ask_id") int id);

    /**
     * 取消关注
     *
     * @param id
     * @return
     */
    @GET("ask/delWonder")
    Observable<Result<Map<String, String>>> delWonder(@Query("ask_id") int id);

    /**
     * 想知道答案列表
     *
     * @param page
     * @return
     */
    @GET("ask/wonderList")
    Observable<Page<WonderAnswer>> wonderList(@Query("page") int page);


    /**
     * 问题列表
     *
     * @param page
     * @return
     */
    @GET("ask/lists")
    Observable<Page<Question>> getQuestList(@Query("page") int page, @Query("keywords") String keyword);

    /**
     * 发布问题
     */
    @POST("ask/add")
    Observable<Result<AskSuccess>> addQA(@Body RequestBody body);

    /**
     * 关注内容
     */
    @GET("ask/lists")
    Observable<Page<Question>> getQAByType(@Query("type") String type, @Query("page") int page);

    /**
     * 评论及回复
     *
     * @param id
     * @return
     */
    @GET("ask/answerReply")
    Observable<Result<AnswerReply>> getReply(@Query("answer_id") int id);

    /**
     * 回答列表
     *
     * @param id
     * @return
     */
    @GET("ask/detailReplay")
    Observable<Page<Answer>> getAnswerList(@Query("ask_id") int id, @Query("page") int page);

    /**
     * 问题详情
     */
    @GET("ask/detail")
    Observable<Result<Question>> getQuestionDetail(@Query("ask_id") int askId);


    /**
     * 添加问题收藏
     *
     * @return
     */
    @GET("ask/addCollect")
    Observable<Response> collect(@Query("ask_id") int id);


    /**
     * 回复问题
     *
     * @param id      type是1的时候传问题id，回复传评论id
     * @param content 内容
     * @return
     */
    @FormUrlEncoded
    @POST("ask/addAnswer")
    Observable<Result<ReplySuccess>> reply(@Field("id") int id, @Field("content") String content);

    /**
     * 回答问题
     *
     * @param type
     * @param id
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("ask/addAnswer")
    Observable<Result<Answer>> answer(@Field("type") int type, @Field("id") int id, @Field("content") String content);

    /**
     * 点赞
     *
     * @param answerId
     * @return
     */
    @GET("ask/praise")
    Observable<Result<Map<String, String>>> praise(@Query("id") int answerId);

    /**
     * 作物分类
     *
     * @return
     */
    @GET("ask/plantCategory")
    Observable<Result<List<Crop>>> getCropList();

    /**
     * 热门搜索
     *
     * @return
     */
    @GET("ask/hostWords")
    Observable<Result<List<Keyword>>> getHotKeyword();

    /**
     * 搜索历史
     *
     * @return
     */
    @GET("ask/searchHistory")
    Observable<Result<List<Keyword>>> getHistorySearch();

    /**
     * 删除历史
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("ask/delSearch")
    Observable<Response> deleteHistory(@Field("id") int id);

    /**
     * 关注作物
     *
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST("ask/followPlant")
    Observable<Response> followPlant(@Field("ids") String ids);

    @FormUrlEncoded
    @POST("ask/addSearch")
    Observable<Response> addSearch(@Field("keywords") String keyword);

    /**
     * 作物搜索
     *
     * @param page
     * @return
     */
    @GET("ask/searchPlant")
    Observable<Page<Plant>> searchPlant(@Query("page") int page, @Query("searchPlant") String keyword);
}
