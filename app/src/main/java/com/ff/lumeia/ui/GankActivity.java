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
