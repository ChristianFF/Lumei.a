package com.ff.lumeia.presenter;

import android.content.Context;

import com.ff.lumeia.model.GankData;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.net.MyRetrofitClient;
import com.ff.lumeia.view.IDailyView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by feifan on 16/1/28.
 * Contacts me:404619986@qq.com
 */
public class DailyPresenter extends BasePresenter<IDailyView> {

    public DailyPresenter(IDailyView iView, Context context) {
        super(iView, context);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    public void requestDailyGankData(int year, int month, int day) {
        subscription = MyRetrofitClient.getGankServiceInstance().getDailyData(year, month, day)
                .map(gankData -> addAllResults(gankData.results))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iView::showGankList, throwable -> {
                    iView.showErrorView();
                });
    }

    private List<Gank> addAllResults(GankData.Result results) {
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
}
