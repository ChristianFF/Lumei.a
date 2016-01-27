package com.ff.lumeia.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.ui.BaseActivity;
import com.ff.lumeia.ui.about.AboutActivity;
import com.ff.lumeia.ui.gank.GankActivity;
import com.ff.lumeia.ui.setting.SettingActivity;
import com.ff.lumeia.util.TipsUtils;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;


/**
 * 主界面
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public class MainActivity extends BaseActivity implements MainContract.View {
    private static final int PRELOAD_SIZE = 5;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFab;

    private List<Meizi> mMeiziList;
    private int mPage = 1;
    private MeiziAdapter mAdapter;
    private boolean isCanLoading = true;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new MainPresenter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onFABClick();
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.scrollToPosition(0);
            }
        });

        setUpRecyclerView();
        setUpSwipeRefreshLayout();
        hideFloatActionButton();
        mPresenter.registerAlarmService();
        mPresenter.requestMeiziData(1, false);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    private void setUpRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mMeiziList = new ArrayList<>();
        mAdapter = new MeiziAdapter(mMeiziList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isReachBottom(layoutManager) && dy > 0 && canLoading()) {
                    isCanLoading = false;
                    mPresenter.requestMeiziData(mPage, false);
                }
            }
        });
    }

    private boolean canLoading() {
        return isCanLoading;
    }

    private boolean isReachBottom(LinearLayoutManager layoutManager) {
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = mAdapter.getItemCount();
        return lastVisibleItem >= totalItemCount - PRELOAD_SIZE;
    }

    private void setUpSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorAccentLight);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.requestMeiziData(mPage, true);
            }
        });
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void goSettingActivity() {
        startActivity(SettingActivity.createIntent(this));
    }

    @Override
    public void goAboutActivity() {
        startActivity(AboutActivity.createIntent(this));
    }

    @Override
    public void goGankActivity() {
        startActivity(GankActivity.createIntent(this));
    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        isCanLoading = true;
        TipsUtils.showSnackWithAction(
                mFab,
                getString(R.string.error),
                Snackbar.LENGTH_LONG,
                getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.requestMeiziData(mPage, true);
                    }
                });
    }

    @Override
    public void showNoMoreData() {
        isCanLoading = true;
        TipsUtils.showSnack(mFab, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showMeiziList(List<Meizi> meizis, boolean clean) {
        isCanLoading = true;
        if (clean) {
            mMeiziList.clear();
        }
        mMeiziList.addAll(meizis);
        mAdapter.notifyDataSetChanged();
        mPage++;
    }

    @Override
    public void showExitDialog() {
        new AlertDialog.Builder(this)
                .setPositiveButton(getString(R.string.quit_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.quit_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setTitle(getString(R.string.quit))
                .setMessage(getString(R.string.want_quit))
                .show();
    }

    @Override
    public void showFloatActionButton() {
        mFab.animate()
                .setInterpolator(new OvershootInterpolator(1f))
                .translationY(0)
                .setStartDelay(500)
                .setDuration(500)
                .start();
    }

    @Override
    public void hideFloatActionButton() {
        mFab.setTranslationY(getResources().getDimensionPixelOffset(R.dimen.fab_translate_y));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int provideMenuResId() {
        return R.menu.menu_main;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                goAboutActivity();
                return true;
            case R.id.setting:
                goSettingActivity();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
