package com.ff.lumeia.view;

import com.ff.lumeia.model.entity.Gank;

import java.util.List;

/**
 * Created by feifan on 16/1/28.
 * Contacts me:404619986@qq.com
 */
public interface IDailyView extends IBaseView {
    void showGankList(List<Gank> gankList);

    void showErrorView();
}
