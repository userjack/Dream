package com.denny.dream.data;

import com.denny.dream.bean.LiveModal;
import com.denny.dream.bean.TitleModal;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/5/31.
 */
public interface DataInterface {
    //直播数据请求
    @GET("/json/play/list{page}.json?")
    Call<LiveModal>  getData(@Path("page") String page,@Query("date_time") String date_time);
    //首页数据请求
    @GET("json/page/app-index/info.json?")
    Call reQuestRecommand(@Query("date_time") String date_time);
    //栏目数据请求
    @GET("json/categories/list.json?")
    Call<List<TitleModal>> reQuestTitle(@Query("date_time") String date_time);

}
