package com.ff.lumeia.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class MyRetrofitClient {
    private static final String HOST = "http://gank.io/api/";
    private static GankService gankService;
    private static Retrofit retrofit;

    static {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public static GankService getGankServiceInstance() {
        if (gankService == null) {
            synchronized (MyRetrofitClient.class) {
                if (gankService == null) {
                    gankService = retrofit.create(GankService.class);
                }
            }
        }
        return gankService;
    }

}
