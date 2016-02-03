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
