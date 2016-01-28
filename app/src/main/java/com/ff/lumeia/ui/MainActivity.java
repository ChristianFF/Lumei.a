package com.ff.lumeia.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.presenter.MainPresenter;
import com.ff.lumeia.ui.adapter.MeiziAdapter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.util.TipsUtils;
import com.ff.lumeia.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


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

    @OnClick(R.id.fab)
    void onFabClick() {
        //TODO go to gank activity
    }

    private static final int PRELOAD_SIZE = 4;

    private MainPresenter mainPresenter;

    private List<Meizi> meiziList;

    private int page = 1;
    private MeiziAdapter meiziAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    protected void initPresenter() {
        mainPresenter = new MainPresenter(this, this);
        mainPresenter.init();
    }

    @Override
    public void init() {
        meiziList = new ArrayList<>();
        mainPresenter.loadMeiziDataFromDB(meiziList);

        setUpRecyclerView();

        setUpSwipeRefreshLayout();

        setUpToolbar();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        meiziAdapter = new MeiziAdapter(this, meiziList);
        recyclerView.setAdapter(meiziAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isReachBottom((LinearLayoutManager) layoutManager) && dy > 0) {
                    page++;
                    mainPresenter.requestMeiziData(page, false);
                }
            }
        });
    }

    private boolean isReachBottom(LinearLayoutManager layoutManager) {
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = meiziAdapter.getItemCount();
        return lastVisibleItem >= totalItemCount - PRELOAD_SIZE;
    }

    private void setUpSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorAccentLight);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            mainPresenter.requestMeiziData(page, true);
        });
        swipeRefreshLayout.postDelayed(() -> mainPresenter.requestMeiziData(page, true), 256);
    }

    private void setUpToolbar() {
        toolbar.setOnClickListener(view -> recyclerViewScrollToTop());
    }

    private void recyclerViewScrollToTop() {
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        TipsUtils.showSnackWithAction(
                fab,
                getString(R.string.error),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.retry),
                view -> mainPresenter.requestMeiziData(page, true));
    }

    @Override
    public void showNoMoreData() {
        TipsUtils.showSnack(fab, "木有更多数据了~", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showMeiziList(List<Meizi> meizis, boolean clean) {
        if (clean) {
            meiziList.clear();
            meiziList.addAll(meizis);
            meiziAdapter.notifyDataSetChanged();
        } else {
            meiziList.addAll(meizis);
            meiziAdapter.notifyDataSetChanged();
        }
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
