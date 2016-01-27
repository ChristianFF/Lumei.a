package com.ff.lumeia.ui.about;

import com.google.common.base.Preconditions;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

class AboutPresenter implements AboutContract.Presenter {
    private AboutContract.View mView;

    AboutPresenter(AboutContract.View view) {
        mView = Preconditions.checkNotNull(view, "aboutview cannnot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
