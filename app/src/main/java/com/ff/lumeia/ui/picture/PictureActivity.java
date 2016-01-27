package com.ff.lumeia.ui.picture;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.ui.BaseActivity;
import com.ff.lumeia.util.DateUtils;
import com.ff.lumeia.util.TipsUtils;
import com.google.common.base.Preconditions;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends BaseActivity implements PictureContract.View {
    public static final String IMG_TRANSITION_NAME = "transition_img";
    private static final String EXTRA_MEIZI = "extra_meizi";
    ImageView mImageView;

    private Meizi mData;
    private PhotoViewAttacher mPhotoViewAttacher;
    private Bitmap mBitmap;

    private PictureContract.Presenter mPresenter;

    public static Intent createIntent(Context context, Meizi data) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(EXTRA_MEIZI, data);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PicturePresenter(this);

        mAppBarLayout.setAlpha(0.5f);
        getIntentData();
        initMeiziView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mData = intent.getParcelableExtra(EXTRA_MEIZI);
        if (mData == null || TextUtils.isEmpty(mData.desc)) {
            setTitle(getString(R.string.app_name));
        } else {
            setTitle(mData.desc);
        }
    }

    private void initMeiziView() {
        mImageView = (ImageView) findViewById(R.id.img_meizi_full);
        ViewCompat.setTransitionName(mImageView, IMG_TRANSITION_NAME);
        mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
        if (mData != null && !TextUtils.isEmpty(mData.url)) {
            Glide.with(this).load(mData.url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mImageView.setImageBitmap(resource);
                    mPhotoViewAttacher.update();
                    mBitmap = resource;
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    mImageView.setImageDrawable(errorDrawable);
                }
            });
            mPhotoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    hideOrShowToolBar();
                }
            });
            mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showSaveDialog();
                    return true;
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.resolveExternalPermissionResult(this, requestCode, permissions, grantResults,
                mBitmap, DateUtils.convertDateToEnString(mData.publishedAt));
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected int provideMenuResId() {
        return R.menu.menu_picture;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (mPresenter.grantExternalPermission(PictureActivity.this)) {
                    mPresenter.saveMeiziPicture(mBitmap,
                            DateUtils.convertDateToEnString(mData.publishedAt));
                }
                return true;
            case R.id.share:
                mPresenter.shareMeiziPictureToFriends(mBitmap,
                        DateUtils.convertDateToEnString(mData.publishedAt));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(PictureContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showSaveDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.catch_meizi)
                .setTitle(R.string.find_meizi)
                .setNegativeButton(R.string.transfer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.throw_master_ball, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mPresenter.grantExternalPermission(PictureActivity.this)) {
                            mPresenter.saveMeiziPicture(mBitmap,
                                    DateUtils.convertDateToEnString(mData.publishedAt));
                        }
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @Override
    public void showSaveResult(boolean isSucceed) {
        if (isSucceed) {
            TipsUtils.showSnack(mImageView,
                    getString(R.string.save_succeed),
                    Snackbar.LENGTH_LONG);
        } else {
            TipsUtils.showSnack(mImageView,
                    getString(R.string.save_failed),
                    Snackbar.LENGTH_LONG);
        }
    }
}
