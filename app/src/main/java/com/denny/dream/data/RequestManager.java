package com.denny.dream.data;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2016/6/3.
 */
public class RequestManager {
    private static  volatile RequestManager rm = null;
    public static RequestManager getInstance(){
        if (rm == null){
            synchronized (RequestManager.class){
                RequestManager manager = rm;
                if (manager == null){
                    manager = new RequestManager();
                    rm = manager;
                }
            }
        }
        return rm;
    }
    public  DataInterface requestUrlData(String baseUrl){
            return requestUrlData(baseUrl);
    }
    private DataInterface requestData(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        DataInterface apiService = retrofit.create(DataInterface.class);
        return apiService;

    }


}
