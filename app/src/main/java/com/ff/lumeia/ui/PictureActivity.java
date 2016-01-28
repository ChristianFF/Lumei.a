package com.ff.lumeia.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ff.lumeia.LumeiaApp;
import com.ff.lumeia.LumeiaConfig;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.presenter.PicturePresenter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.util.DateUtils;
import com.ff.lumeia.util.TipsUtils;
import com.ff.lumeia.view.IPictureView;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends ToolbarActivity<PicturePresenter> implements IPictureView {

    @Bind(R.id.img_meizi_full)
    ImageView imgMeiziFull;

    private Context context;

    private Meizi meizi;
    private PhotoViewAttacher photoViewAttacher;
    private Bitmap girl;

    private PicturePresenter picturePresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initPresenter() {
        picturePresenter = new PicturePresenter(this, this);
        picturePresenter.init();
    }

    @Override
    public void init() {
        context = this;
        appBar.setAlpha(0.5f);
        getIntentData();
        initMeiziView();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        meizi = (Meizi) intent.getSerializableExtra(LumeiaConfig.MEIZI);
        setTitle(meizi.desc);
    }

    private void initMeiziView() {
        imgMeiziFull.setImageDrawable(LumeiaApp.meiziDeliverDrawable);
        ViewCompat.setTransitionName(imgMeiziFull, LumeiaConfig.IMG_TRANSITION_NAME);
        photoViewAttacher = new PhotoViewAttacher(imgMeiziFull);
        Glide.with(this).load(meizi.url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imgMeiziFull.setImageBitmap(resource);
                photoViewAttacher.update();
                girl = resource;
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                imgMeiziFull.setImageDrawable(errorDrawable);
            }
        });
        photoViewAttacher.setOnPhotoTapListener((view, x, y) ->
                hideOrShowToolBar());
        photoViewAttacher.setOnLongClickListener(view -> {
            showSaveDialog();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                picturePresenter.saveMeiziPicture(girl,
                        DateUtils.convertDateToEnString(meizi.publishedAt));
                return true;
            }
            case R.id.share: {
                picturePresenter.shareMeiziPictureToFriends(girl,
                        DateUtils.convertDateToEnString(meizi.publishedAt));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSaveDialog() {
        new AlertDialog.Builder(this)
                .setMessage("确定捕获这只野生的妹纸么?")
                .setTitle("发现妹子")
                .setNegativeButton("放生~", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("投出大师球!", (dialogInterface, i) -> {
                    picturePresenter.saveMeiziPicture(girl,
                            DateUtils.convertDateToEnString(meizi.publishedAt));
                    dialogInterface.dismiss();
                }).show();
    }

    @Override
    public void showSaveResult(boolean isSucceed) {
        if (isSucceed) {
            TipsUtils.showSnack(imgMeiziFull,
                    context.getString(R.string.save_succeed),
                    Snackbar.LENGTH_LONG);
        } else {
            TipsUtils.showSnack(imgMeiziFull,
                    context.getString(R.string.save_failed),
                    Snackbar.LENGTH_LONG);
        }
    }
}
