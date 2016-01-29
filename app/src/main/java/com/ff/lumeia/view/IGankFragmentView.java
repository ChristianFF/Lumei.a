package com.ff.lumeia.view;

import com.ff.lumeia.model.entity.Gank;

import java.util.List;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public interface IGankFragmentView extends IBaseView {
    void showProgressBar();

    void hideProgressBar();

    void showErrorView();

    void showNoMoreData();

    void showGankList(List<Gank> gankList, boolean clean);
}
