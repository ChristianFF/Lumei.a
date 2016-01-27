package com.ff.lumeia.ui.gank;

import com.ff.lumeia.model.BatteryData;
import com.ff.lumeia.net.MyRetrofitClient;
import com.google.common.base.Preconditions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

class GankPresenter implements GankContract.Presenter {
    private GankContract.View mView;

    GankPresenter(GankContract.View gankView) {
        mView = Preconditions.checkNotNull(gankView, "gankView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void requestGankData(String type, int page, final boolean clean) {
        if (mView == null) {
            return;
        }
        mView.showProgressBar();
        MyRetrofitClient.getGankServiceInstance().getBatteryData(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BatteryData>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(BatteryData batteryData) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideProgressBar();
                        if (batteryData.results.size() == 0) {
                            mView.showNoMoreData();
                        } else {
                            mView.showGankList(batteryData.results, clean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideProgressBar();
                        mView.showErrorView();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
