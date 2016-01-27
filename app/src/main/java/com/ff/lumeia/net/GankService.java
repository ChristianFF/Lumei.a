package com.ff.lumeia.net;

import com.ff.lumeia.LumeiaConfig;
import com.ff.lumeia.model.BatteryData;
import com.ff.lumeia.model.GankData;
import com.ff.lumeia.model.MeiziData;
import com.ff.lumeia.model.RestingVideoData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public interface GankService {
    @GET("data/福利/" + LumeiaConfig.MEIZHI_SIZE + "/{page}")
    Observable<MeiziData> getMeiziData(@Path("page") int page);

    @GET("data/休息视频/" + LumeiaConfig.MEIZHI_SIZE + "/{page}")
    Observable<RestingVideoData> getRestingVideoData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/{type}/" + LumeiaConfig.GANK_SIZE + "/{page}")
    Observable<BatteryData> getBatteryData(@Path("type") String type, @Path("page") int page);
}
