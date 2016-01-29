/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 * Copyright (C) 2016 Panl <panlei106@gmail.com>
 * CopyRight (C) 2016 ChristianFF <feifan0322@gmail.com>
 *
 * Lumei.a is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lumei.a is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Lumei.a.  If not, see <http://www.gnu.org/licenses/>.
 */

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
    @GET("data/福利/" + LumeiaConfig.MEIZI_SIZE + "/{page}")
    Observable<MeiziData> getMeiziData(@Path("page") int page);

    @GET("data/休息视频/" + LumeiaConfig.MEIZI_SIZE + "/{page}")
    Observable<RestingVideoData> getRestingVideoData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/{type}/" + LumeiaConfig.GANK_SIZE + "/{page}")
    Observable<BatteryData> getBatteryData(@Path("type") String type, @Path("page") int page);
}
