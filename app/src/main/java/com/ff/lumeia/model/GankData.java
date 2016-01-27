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
        public List<Gank> MeiziList;
        @SerializedName("拓展资源")
        public List<Gank> ExtendedResourceList;
        @SerializedName("瞎推荐")
        public List<Gank> FuckingRecommandationList;
        @SerializedName("App")
        public List<Gank> appList;
        @SerializedName("前端")
        public List<Gank> FrontEndList;
    }
}
