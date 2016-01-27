package com.ff.lumeia.ui.setting;

import com.google.common.base.Preconditions;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View mSettingView;

    SettingPresenter(SettingContract.View settingView) {
        mSettingView = Preconditions.checkNotNull(settingView, "settingView cannot be null!");
        mSettingView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
