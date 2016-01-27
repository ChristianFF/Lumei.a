package com.ff.lumeia.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ff.lumeia.R;
import com.ff.lumeia.presenter.MainPresenter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.view.IMainView;

import butterknife.Bind;

/**
 * 主界面
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public class MainActivity extends ToolbarActivity<MainPresenter> implements IMainView {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private MainPresenter mainPresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        mainPresenter = new MainPresenter(this, this);
        presenter.init();
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    public void init() {


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewScrollToTop();
            }
        });
    }

    private void recyclerViewScrollToTop() {
        recyclerView.scrollToPosition(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
