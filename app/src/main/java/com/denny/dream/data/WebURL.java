package com.denny.dream.data;

/**
 * Created by Administrator on 2016/5/31.
 */
public class WebURL {
    //    JSON基础目录
    public static final String BASE_JSON_URL = "http://www.quanmin.tv/";
    //    public static final String BASE_JSON_URL = "http://test.quanmin.tv";
    //    平台所有直播列表数据
    public static final String LIVE_LIST = BASE_JSON_URL + "json/play/list";
    //    平台所有频道列表
    public static final String CATEGORY_LIST = BASE_JSON_URL + "json/categories/list.json?";
    //    平台某频道所属直播
    public static final String CATEGORY_LIVE_LIST = BASE_JSON_URL + "json/categories/";
    //    首页所需数据
    public static final String RECOMNEND_LIST = BASE_JSON_URL + "json/page/app-index/info.json?";
    //获取用户信息
    public static final String m_myinfo = "user.myInfo";
}
