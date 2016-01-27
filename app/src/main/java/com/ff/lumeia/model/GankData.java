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

    public static class Result {
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
