package com.ff.lumeia.ui.main;

import android.support.annotation.NonNull;

import com.ff.lumeia.model.MeiziData;
import com.ff.lumeia.model.RestingVideoData;
import com.ff.lumeia.net.MyRetrofitClient;
import com.ff.lumeia.util.DailyReminderUtils;
import com.ff.lumeia.util.SharedPreferenceUtils;
import com.google.common.base.Preconditions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by feifan on 2017/2/27.
 * Contacts me:404619986@qq.com
 */

class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    MainPresenter(@NonNull MainContract.View mainView) {
        mView = Preconditions.checkNotNull(mainView, "mainView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void onFABClick() {
        if (mView == null) {
            return;
        }
        mView.goGankActivity();
    }

    @Override
    public void registerAlarmService() {
        if (mView == null) {
            return;
        }
        SharedPreferenceUtils.putBoolean("daily_reminder",
                SharedPreferenceUtils.getBoolean("daily_reminder", true));
        DailyReminderUtils.register();
    }

    @Override
    public void requestMeiziData(int page, final boolean clean) {
        if (mView == null) {
            return;
        }
        mView.showLoading();
        Flowable.zip(MyRetrofitClient.getGankServiceInstance().getMeiziData(page),
                MyRetrofitClient.getGankServiceInstance().getRestingVideoData(page),
                new BiFunction<MeiziData, RestingVideoData, MeiziData>() {
                    @Override
                    public MeiziData apply(MeiziData meiziData, RestingVideoData restingVideoData) throws Exception {
                        return createMeiziDataWithRestingVideoDesc(meiziData, restingVideoData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MeiziData>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(MeiziData meiziData) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        mView.showFloatActionButton();
                        if (meiziData.results.size() == 0) {
                            mView.showNoMoreData();
                        } else {
                            mView.showMeiziList(meiziData.results, clean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        mView.showErrorView();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private MeiziData createMeiziDataWithRestingVideoDesc(MeiziData meiziData, RestingVideoData restingVideoData) {
        int size = Math.min(meiziData.results.size(), restingVideoData.results.size());
        for (int i = 0; i < size; i++) {
            meiziData.results.get(i).desc = meiziData.results.get(i).desc + "，"
                    + restingVideoData.results.get(i).desc;
            meiziData.results.get(i).who = restingVideoData.results.get(i).who;
        }
        return meiziData;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
