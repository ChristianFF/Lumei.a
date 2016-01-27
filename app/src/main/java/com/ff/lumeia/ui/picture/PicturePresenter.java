package com.ff.lumeia.ui.picture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.ff.lumeia.LumeiaApp;
import com.ff.lumeia.R;
import com.ff.lumeia.util.FileUtils;
import com.ff.lumeia.util.ShareUtils;
import com.google.common.base.Preconditions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

class PicturePresenter implements PictureContract.Presenter {
    private static final int PERMISSION_REQUEST_CODE = 0x2233;
    private PictureContract.View mView;

    PicturePresenter(PictureContract.View pictureView) {
        mView = Preconditions.checkNotNull(pictureView, "pictureView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public boolean grantExternalPermission(Context context) {
        if (context instanceof Activity) {
            String[] permissions = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE"};
            if (!checkSelfPermissions(context, permissions)) {
                if (shouldShowRequestPermissionRationale((Activity) context, permissions)) {
                    showPermissionRationale((Activity) context, new Runnable() {
                        @Override
                        public void run() {
                            ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
                }
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resolveExternalPermissionResult(Context context, int requestCode, String[] permissions, int[] grantResults,
                                                Bitmap bitmap, String title) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveMeiziPicture(bitmap, title);
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkSelfPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private boolean shouldShowRequestPermissionRationale(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return true;
        }
        return false;
    }

    private static void showPermissionRationale(Activity host, final Runnable action) {
        new AlertDialog.Builder(host)
                .setMessage("为了把妹子装进手机，我们需要获得你的王之力量")
                .setCancelable(false)
                .setPositiveButton("yeah，I know!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        action.run();
                    }
                }).show();
    }

    @Override
    public void saveMeiziPicture(final Bitmap bitmap, final String title) {
        if (mView == null) {
            return;
        }
        Flowable.create(new FlowableOnSubscribe<Uri>() {
            @Override
            public void subscribe(FlowableEmitter<Uri> e) throws Exception {
                if (!FileUtils.isSDCardEnable()) {
                    e.onError(new Exception(LumeiaApp.getInstance().getString(R.string.save_failed)));
                }
                Uri uri = FileUtils.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    e.onError(new Exception(LumeiaApp.getInstance().getString(R.string.save_failed)));
                } else {
                    e.onNext(uri);
                }
            }
        }, FlowableEmitter.BackpressureMode.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Uri uri) {
                        if (mView == null) {
                            return;
                        }
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        ((Activity) mView).sendBroadcast(scannerIntent);
                        mView.showSaveResult(true);
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mView == null) {
                            return;
                        }
                        mView.showSaveResult(false);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void shareMeiziPictureToFriends(final Bitmap bitmap, final String title) {
        if (mView == null) {
            return;
        }
        Flowable.create(new FlowableOnSubscribe<Uri>() {
            @Override
            public void subscribe(FlowableEmitter<Uri> e) throws Exception {
                Uri uri = FileUtils.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    e.onError(new Exception(((Activity) mView).getString(R.string.save_failed)));
                } else {
                    e.onNext(uri);
                }
            }
        }, FlowableEmitter.BackpressureMode.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Uri uri) {
                        if (mView == null) {
                            return;
                        }
                        ShareUtils.shareImage(((Activity) mView), uri);
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mView == null) {
                            return;
                        }
                        mView.showSaveResult(false);
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
