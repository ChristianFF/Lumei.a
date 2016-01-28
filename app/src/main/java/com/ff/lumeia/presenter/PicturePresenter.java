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
