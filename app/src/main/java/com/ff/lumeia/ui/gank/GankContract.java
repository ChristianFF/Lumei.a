package com.ff.lumeia.ui.gank;

import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

import java.util.List;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class GankContract {
    interface View extends BaseView<Presenter> {
        void showProgressBar();

        void hideProgressBar();

        void showErrorView();

        void showNoMoreData();

        void showGankList(List<Gank> gankList, boolean clean);
    }

    interface Presenter extends BasePresenter {
        void requestGankData(String type, int page, final boolean clean);
    }
}
