package com.ff.lumeia.ui.daily;

import com.ff.lumeia.model.GankData;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.net.MyRetrofitClient;
import com.google.common.base.Preconditions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class DailyPresenter implements DailyContract.Presenter {
    private DailyContract.View mView;

    DailyPresenter(DailyContract.View dailyView) {
        mView = Preconditions.checkNotNull(dailyView, "dailyView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void onFABClick() {
        if (mView == null) {
            return;
        }
        mView.goVideoActivity();
    }

    @Override
    public void requestDailyGankData(int year, int month, int day) {
        if (mView == null) {
            return;
        }
        MyRetrofitClient.getGankServiceInstance().getDailyData(year, month, day)
                .map(new Function<GankData, List<Gank>>() {
                    @Override
                    public List<Gank> apply(GankData gankData) throws Exception {
                        return gankData == null ? null : addAllResults(gankData.results);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<Gank> ganks) {
                        if (mView == null || ganks == null) {
                            return;
                        }
                        mView.showGankList(ganks);
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mView == null) {
                            return;
                        }
                        mView.showErrorView();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private List<Gank> addAllResults(GankData.Result results) {
        if (results == null) {
            return null;
        }
        List<Gank> gankList = new ArrayList<>();
        if (results.androidList != null) {
            gankList.addAll(results.androidList);
        }
        if (results.IOSList != null) {
            gankList.addAll(results.IOSList);
        }
        if (results.frontEndList != null) {
            gankList.addAll(results.frontEndList);
        }
        if (results.appList != null) {
            gankList.addAll(results.appList);
        }
        if (results.extendedResourceList != null) {
            gankList.addAll(results.extendedResourceList);
        }
        if (results.fuckingRecommendationList != null) {
            gankList.addAll(results.fuckingRecommendationList);
        }
        if (results.restingVideoList != null) {
            gankList.addAll(0, results.restingVideoList);
        }
        return gankList;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
