package com.ff.lumeia.model.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class Foundation implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("ID")
    public long id;

    @NotNull
    @Unique
    @Column("ObjectId")
    public String objectId;
}
