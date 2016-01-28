package com.ff.lumeia.view;

import com.ff.lumeia.model.entity.Meizi;

import java.util.List;

/**
 * 主界面接口
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public interface IMainView extends IBaseView {
    void goAboutActivity();

    void goGankActivity();

    void showProgress();

    void hideProgress();

    void showErrorView();

    void showNoMoreData();

    void showMeiziList(List<Meizi> meiziList, boolean clean);
}
