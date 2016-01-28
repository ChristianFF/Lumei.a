package com.ff.lumeia.ui;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ff.lumeia.LumeiaApp;
import com.ff.lumeia.LumeiaConfig;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.presenter.DailyPresenter;
import com.ff.lumeia.ui.adapter.DailyGankAdapter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.ui.widget.VideoImageView;
import com.ff.lumeia.util.TipsUtils;
import com.ff.lumeia.view.IDailyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

public class DailyActivity extends ToolbarActivity<DailyPresenter> implements IDailyView {

    @Bind(R.id.img_video)
    VideoImageView imgVideo;
    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.recycler_view_daily)
    RecyclerView recyclerViewDaily;
    @Bind(R.id.fab_play)
    FloatingActionButton fabPlay;

    private DailyPresenter dailyPresenter;

    private List<Gank> gankList;
    private DailyGankAdapter dailyGankAdapter;

    private Calendar calendar;
    private Meizi meizi;

    private Context context;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initPresenter() {
        dailyPresenter = new DailyPresenter(this, this);
        dailyPresenter.init();
    }

    @Override
    public void init() {
        context = this;
        fabPlay.setClickable(false);
        getIntentData();
        initGankData();
        initImg();
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(LumeiaConfig.MEIZI);
        calendar = Calendar.getInstance();
        calendar.setTime(meizi.publishedAt);
    }

    private void initGankData() {
        gankList = new ArrayList<>();
        dailyPresenter.requestDailyGankData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,
                false);

        dailyGankAdapter = new DailyGankAdapter(context, gankList);
        recyclerViewDaily.setLayoutManager(layoutManager);
        recyclerViewDaily.setAdapter(dailyGankAdapter);
    }

    private void initImg() {
        imgVideo.setImageDrawable(LumeiaApp.meiziDeliverDrawable);
        ViewCompat.setTransitionName(imgVideo, LumeiaConfig.IMG_TRANSITION_NAME);
    }

    @Override
    public void showGankList(List<Gank> list) {
        gankList.addAll(list);
        dailyGankAdapter.notifyDataSetChanged();
        fabPlay.setClickable(true);
    }

    @Override
    public void showErrorView() {
        TipsUtils.showSnackWithAction(fabPlay,
                getString(R.string.error),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.retry),
                view -> dailyPresenter.requestDailyGankData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)));
    }
}
