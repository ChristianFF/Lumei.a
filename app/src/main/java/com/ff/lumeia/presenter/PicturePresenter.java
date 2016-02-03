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
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.ff.lumeia.R;
import com.ff.lumeia.util.FileUtils;
import com.ff.lumeia.util.ShareUtils;
import com.ff.lumeia.view.IPictureView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by feifan on 16/1/28.
 * Contacts me:404619986@qq.com
 */
public class PicturePresenter extends BasePresenter<IPictureView> {

    public PicturePresenter(IPictureView iView, Context context) {
        super(iView, context);
    }

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    public void saveMeiziPicture(final Bitmap bitmap, final String title) {
        subscription = Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                if (!FileUtils.isSDCardEnable()) {
                    subscriber.onError(new Exception(context.getString(R.string.save_failed)));
                }
                Uri uri = FileUtils.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception(context.getString(R.string.save_failed)));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    context.sendBroadcast(scannerIntent);
                    iView.showSaveResult(true);
                }, throwable -> {
                    iView.showSaveResult(false);
                });
    }

    public void shareMeiziPictureToFriends(final Bitmap bitmap, final String title) {
        Observable.create(new Observable.OnSubscribe<Uri>() {

            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtils.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception(context.getString(R.string.save_failed)));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    ShareUtils.shareImage(context, uri);
                }, throwable -> {
                    iView.showSaveResult(false);
                });
    }
}
