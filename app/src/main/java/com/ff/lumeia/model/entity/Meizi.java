package com.ff.lumeia.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * 妹子数据模型
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class Meizi implements Parcelable {

    /**
     * who : 张涵宇
     * publishedAt : 2016-01-26T04:02:34.316Z
     * desc : 1.26
     * type : 福利
     * url : http://ww2.sinaimg.cn/large/7a8aed7bjw1f0buzmnacoj20f00liwi2.jpg
     * used : true
     * objectId : 56a5e769816dfa005aa27c38
     * createdAt : 2016-01-25T09:14:17.609Z
     * updatedAt : 2016-01-26T04:02:34.897Z
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
        return "Meizi{" +
                "who='" + who + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", used=" + used +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
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

    public Meizi() {
    }

    protected Meizi(Parcel in) {
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

    public static final Creator<Meizi> CREATOR = new Creator<Meizi>() {
        @Override
        public Meizi createFromParcel(Parcel source) {
            return new Meizi(source);
        }

        @Override
        public Meizi[] newArray(int size) {
            return new Meizi[size];
        }
    };
}
