package com.ff.lumeia.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * 干货model
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class Gank implements Parcelable {

    /**
     * who : 小梁
     * publishedAt : 2016-01-26T04:02:34.334Z
     * desc : 支持百分比的 ProgressBar
     * type : Android  (Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
     * url : https://github.com/natasam/Android-PercentProgressBar-lib
     * used : true
     * objectId : 56a6cf902e958a0051837a2c
     * createdAt : 2016-01-26T01:44:48.750Z
     * updatedAt : 2016-01-26T04:02:35.872Z
     */
    public String who;

    public String desc;

    public String type;

    public String url;

    public boolean used;

    public Date createdAt;

    public Date updatedAt;

    public Date publishedAt;

    @Override
    public String toString() {
        return "Gank{" +
                "used=" + used +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", desc='" + desc + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.who);
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
    }

    public Gank() {
    }

    protected Gank(Parcel in) {
        this.who = in.readString();
        this.desc = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
    }

    public static final Creator<Gank> CREATOR = new Creator<Gank>() {
        @Override
        public Gank createFromParcel(Parcel source) {
            return new Gank(source);
        }

        @Override
        public Gank[] newArray(int size) {
            return new Gank[size];
        }
    };
}
