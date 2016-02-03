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

package com.ff.lumeia.model;

import com.ff.lumeia.model.entity.Gank;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class GankData extends BaseData {
    public List<String> category;
    public Result results;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> restingVideoList;
        @SerializedName("iOS")
        public List<Gank> IOSList;
        @SerializedName("福利")
        public List<Gank> meiziList;
        @SerializedName("拓展资源")
        public List<Gank> extendedResourceList;
        @SerializedName("瞎推荐")
        public List<Gank> fuckingRecommendationList;
        @SerializedName("App")
        public List<Gank> appList;
        @SerializedName("前端")
        public List<Gank> frontEndList;
    }
}
