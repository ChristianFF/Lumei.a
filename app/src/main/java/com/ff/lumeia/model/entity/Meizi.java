package com.ff.lumeia.model.entity;

import java.util.Date;

/**
 * 妹子数据模型
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class Meizi extends Fundation {

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
}
