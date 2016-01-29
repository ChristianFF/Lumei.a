package com.ff.lumeia.presenter;

import android.content.Context;

import com.ff.lumeia.view.IGankView;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class GankPresenter extends BasePresenter<IGankView> {

    public GankPresenter(IGankView iView, Context context) {
        super(iView, context);
    }

    @Override
    public void release() {

    }
}
