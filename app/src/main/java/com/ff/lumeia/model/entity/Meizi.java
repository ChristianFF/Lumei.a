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

package com.ff.lumeia.model.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * 妹子数据模型
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
@Table("Meizis")
public class Meizi extends Foundation {

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

    @Column("Author")
    public String who;
    @Column("Description")
    public String desc;
    @Column("Type")
    public String type;
    @Column("URL")
    public String url;
    @Column("Used")
    public boolean used;
    @Column("CreateDate")
    public Date createdAt;
    @Column("UpdateDate")
    public Date updatedAt;
    @Column("PublishedDate")
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
