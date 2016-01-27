package com.ff.lumeia.model;

import com.ff.lumeia.model.entity.Meizi;

import java.util.List;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class MeiziData extends BaseData {
    public List<Meizi> results;

    @Override
    public String toString() {
        return "MeiziData{" +
                "results=" + results +
                '}';
    }
}
