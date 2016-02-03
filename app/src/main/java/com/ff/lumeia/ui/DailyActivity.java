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
        initRecyclerView();
        initImg();
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(LumeiaConfig.MEIZI);

    }

    private void initGankData() {
        gankList = new ArrayList<>();
        calendar = Calendar.getInstance();
        calendar.setTime(meizi.publishedAt);
        dailyPresenter.requestDailyGankData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        collapsingLayout.setTitle(meizi.desc);
    }

    private void initRecyclerView() {
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
