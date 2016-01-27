package com.ff.lumeia.ui.daily;

import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

import java.util.List;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class DailyContract {
    interface View extends BaseView<Presenter> {
        void showGankList(List<Gank> gankList);

        void showErrorView();

        void goVideoActivity();
    }

    interface Presenter extends BasePresenter {
        void onFABClick();

        void requestDailyGankData(int year, int month, int day);
    }
}
