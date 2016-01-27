package com.ff.lumeia.presenter;

import android.content.Context;

import com.ff.lumeia.view.IBaseView;

import rx.Subscription;

/**
 * 基础presenter
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public abstract class BasePresenter<T extends IBaseView> {
    protected Subscription subscription;
    protected Context context;
    protected T iView;

    public BasePresenter(T iView, Context context) {
        this.iView = iView;
        this.context = context;
    }

    public void init() {
        iView.init();
    }

    public abstract void release();
}
