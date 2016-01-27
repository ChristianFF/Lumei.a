package com.ff.lumeia.ui.gank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ff.lumeia.R;
import com.ff.lumeia.ui.BaseActivity;

public class GankActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private GankPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new GankPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager, false);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, GankActivity.class);
    }
}
