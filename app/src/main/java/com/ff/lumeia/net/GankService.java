package com.ff.lumeia.net;

import com.ff.lumeia.model.BatteryData;
import com.ff.lumeia.model.GankData;
import com.ff.lumeia.model.MeiziData;
import com.ff.lumeia.model.RestingVideoData;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public interface GankService {
    int MEIZI_SIZE = 20;
    int GANK_SIZE = 20;

    @GET("data/福利/" + MEIZI_SIZE + "/{page}")
    Flowable<MeiziData> getMeiziData(@Path("page") int page);

    @GET("data/休息视频/" + MEIZI_SIZE + "/{page}")
    Flowable<RestingVideoData> getRestingVideoData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Flowable<GankData> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/{type}/" + GANK_SIZE + "/{page}")
    Flowable<BatteryData> getBatteryData(@Path("type") String type, @Path("page") int page);
}
