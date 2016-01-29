/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 * Copyright (C) 2016 Panl <panlei106@gmail.com>
 * CopyRight (C) 2016 ChristianFF <feifan0322@gmail.com>
 *
 * Lumei.a is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lumei.a is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Lumei.a.  If not, see <http://www.gnu.org/licenses/>.
 */

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
