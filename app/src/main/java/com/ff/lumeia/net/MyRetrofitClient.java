package com.ff.lumeia.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class MyRetrofitClient {
    public static final String HOST = "http://gank.avosapps.com/api/";
    private static GankService gankService;
    protected static final Object lock = new Object();

    private static Retrofit retrofit;

    static {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static GankService getGankServiceInstance() {
        synchronized (lock) {
            if (gankService == null) {
                gankService = retrofit.create(GankService.class);
            }
            return gankService;
        }
    }

}
