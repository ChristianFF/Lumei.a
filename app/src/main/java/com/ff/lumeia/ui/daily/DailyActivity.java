package com.ff.lumeia.ui.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.ui.BaseActivity;
import com.ff.lumeia.ui.picture.PictureActivity;
import com.ff.lumeia.ui.video.WebVideoActivity;
import com.ff.lumeia.util.TipsUtils;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyActivity extends BaseActivity implements DailyContract.View {
    private static final String MEIZI_EXTRA = "meizi_extra";

    private VideoImageView mImgVideo;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFabPlay;

    private List<Gank> mGankList;
    private DailyGankAdapter mDailyGankAdapter;
    private Calendar mCalendar;
    private Meizi mMeizi;

    private DailyContract.Presenter mPresenter;

    public static Intent createIntent(Context context, Meizi data) {
        Intent intent = new Intent(context, DailyActivity.class);
        intent.putExtra(MEIZI_EXTRA, data);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DailyPresenter(this);

        mImgVideo = (VideoImageView) findViewById(R.id.img_video);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_daily);
        mFabPlay = (FloatingActionButton) findViewById(R.id.fab_play);
        mFabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onFABClick();
            }
        });
        mFabPlay.setClickable(false);
        getIntentData();
        initGankData();
        initRecyclerView();
        initImg();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_daily;
    }

    private void getIntentData() {
        mMeizi = getIntent().getParcelableExtra(MEIZI_EXTRA);
    }

    private void initGankData() {
        mGankList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mMeizi.publishedAt);
        mPresenter.requestDailyGankData(mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH) + 1,
                mCalendar.get(Calendar.DAY_OF_MONTH));
        mCollapsingToolbarLayout.setTitle(mMeizi.desc);
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        mDailyGankAdapter = new DailyGankAdapter(this, mGankList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mDailyGankAdapter);
    }

    private void initImg() {
        if (mMeizi != null && !TextUtils.isEmpty(mMeizi.url)) {
            Glide.with(this).load(mMeizi.url).asBitmap().into(mImgVideo);
        }
        ViewCompat.setTransitionName(mImgVideo, PictureActivity.IMG_TRANSITION_NAME);
    }

    @Override
    public void setPresenter(DailyContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showGankList(List<Gank> list) {
        mGankList.addAll(list);
        mDailyGankAdapter.notifyDataSetChanged();
        mFabPlay.setClickable(true);
    }

    @Override
    public void showErrorView() {
        TipsUtils.showSnackWithAction(mFabPlay,
                getString(R.string.error),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.requestDailyGankData(mCalendar.get(Calendar.YEAR),
                                mCalendar.get(Calendar.MONTH) + 1,
                                mCalendar.get(Calendar.DAY_OF_MONTH));
                    }
                });
    }

    @Override
    public void goVideoActivity() {
        if (mGankList.size() > 0 && mGankList.get(0).type.equals("休息视频")) {
            WebVideoActivity.start(this, mGankList.get(0).desc, mGankList.get(0).url);
        } else {
            TipsUtils.showSnack(mRecyclerView, getString(R.string.video_error), Snackbar.LENGTH_SHORT);
        }
    }
}
