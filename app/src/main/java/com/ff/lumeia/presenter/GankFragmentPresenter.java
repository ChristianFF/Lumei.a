package com.ff.lumeia.presenter;

import android.content.Context;

import com.ff.lumeia.net.MyRetrofitClient;
import com.ff.lumeia.view.IGankFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class GankFragmentPresenter extends BasePresenter<IGankFragmentView> {

    public GankFragmentPresenter(IGankFragmentView iView, Context context) {
        super(iView, context);
    }

    @Override
    public void release() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void requestGankData(String type, int page, boolean clean) {
        iView.showProgressBar();
        subscription = MyRetrofitClient.getGankServiceInstance().getBatteryData(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankData -> {
                    iView.hideProgressBar();
                    if (gankData.results.size() == 0) {
                        iView.showNoMoreData();
                    } else {
                        iView.showGankList(gankData.results, clean);
                    }
                }, throwable -> {
                    iView.hideProgressBar();
                    iView.showErrorView();
                });
    }
}
