package com.ff.lumeia.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.ff.lumeia.R;

/**
 * 基础Activity
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ActionBar mActionBar;
    protected Toolbar mToolbar;
    protected boolean isToolBarHiding = false;
    protected AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        initToolbar();
    }

    @LayoutRes
    protected abstract int provideContentViewId();

    protected int provideMenuResId() {
        return -1;
    }

    protected boolean canBack() {
        return true;
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        if (mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar == null) {
            throw new IllegalStateException("Toolbar is null!");
        }
        mActionBar.setDisplayHomeAsUpEnabled(canBack());
    }

    public void hideOrShowToolBar() {
        mAppBarLayout.animate()
                .translationY(isToolBarHiding ? 0 : -mAppBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (provideMenuResId() < 0) {
            return true;
        }
        getMenuInflater().inflate(provideMenuResId(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
