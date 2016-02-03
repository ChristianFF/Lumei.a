package com.ff.lumeia.presenter;

import android.content.Context;

import com.ff.lumeia.view.IAboutView;

/**
 * Created by feifan on 16/2/3.
 * Contacts me:404619986@qq.com
 */
public class AboutPresenter extends BasePresenter<IAboutView> {

    public AboutPresenter(IAboutView iView, Context context) {
        super(iView, context);
    }

    @Override
    public void release() {

    }
}
