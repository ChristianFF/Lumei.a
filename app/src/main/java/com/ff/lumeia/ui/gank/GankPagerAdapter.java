package com.ff.lumeia.ui.gank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class GankPagerAdapter extends FragmentPagerAdapter {

    private static final String[] type = {"Android", "iOS", "前端", "瞎推荐", "拓展资源"};

    public GankPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return type.length;
    }

    @Override
    public Fragment getItem(int position) {
        return GankFragment.newInstance(type[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return type[position].toUpperCase();
    }
}
