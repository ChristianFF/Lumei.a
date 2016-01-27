package com.ff.lumeia.ui.main;

import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

import java.util.List;

/**
 * Created by feifan on 2017/2/27.
 * Contacts me:404619986@qq.com
 */

interface MainContract {
    interface View extends BaseView<Presenter> {
        void goSettingActivity();

        void goAboutActivity();

        void goGankActivity();

        void showLoading();

        void hideLoading();

        void showErrorView();

        void showNoMoreData();

        void showMeiziList(List<Meizi> meiziList, boolean clean);

        void showExitDialog();

        void showFloatActionButton();

        void hideFloatActionButton();
    }

    interface Presenter extends BasePresenter {
        void onFABClick();

        void registerAlarmService();

        void requestMeiziData(int page, boolean clean);
    }
}
