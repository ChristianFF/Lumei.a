package com.ff.lumeia.ui.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;

import com.ff.lumeia.R;
import com.ff.lumeia.presenter.BasePresenter;

import butterknife.Bind;

/**
 * 带有toolbar的基础Activity
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public abstract class ToolbarActivity<T extends BasePresenter> extends BaseActivity {
    protected ActionBar actionBar;
    protected T presenter;
    protected boolean isToolBarHiding = false;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.app_bar)
    protected AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    protected void initToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar == null) {
            throw new IllegalStateException("Toolbar is null!");
        }
        actionBar.setDisplayHomeAsUpEnabled(canBack());
    }

    protected boolean canBack() {
        return true;
    }

    protected void hideOrShowToolBar() {
        appBar.animate()
                .translationY(isToolBarHiding ? 0 : -appBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }

}
