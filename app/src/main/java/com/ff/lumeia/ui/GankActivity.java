package com.ff.lumeia.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ff.lumeia.R;
import com.ff.lumeia.presenter.GankPresenter;
import com.ff.lumeia.ui.adapter.GankPagerAdapter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.view.IGankView;

import butterknife.Bind;

public class GankActivity extends ToolbarActivity<GankPresenter> implements IGankView {

    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private GankPresenter gankPresenter;
    private GankPagerAdapter pagerAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void initPresenter() {
        gankPresenter = new GankPresenter(this, this);
        gankPresenter.init();
    }

    @Override
    public void init() {
        toolbar.setTitle(R.string.gank_io);
        pagerAdapter = new GankPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(viewPager);
        tab.setTabsFromPagerAdapter(pagerAdapter);
    }
}
