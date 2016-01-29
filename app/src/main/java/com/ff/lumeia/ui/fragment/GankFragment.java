package com.ff.lumeia.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ff.lumeia.LumeiaConfig;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.presenter.GankFragmentPresenter;
import com.ff.lumeia.ui.adapter.GankAdapter;
import com.ff.lumeia.ui.base.BaseFragment;
import com.ff.lumeia.util.TipsUtils;
import com.ff.lumeia.view.IGankFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class GankFragment extends BaseFragment<GankFragmentPresenter> implements IGankFragmentView {


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static final int PRELOAD_SIZE = 6;

    private GankFragmentPresenter gankFragmentPresenter;

    private List<Gank> gankList;
    private GankAdapter gankAdapter;
    private String type;
    private int page = 1;

    public GankFragment() {

    }

    public static GankFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(LumeiaConfig.TYPE, title);
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(LumeiaConfig.TYPE);
        }
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initPresenter() {
        gankFragmentPresenter = new GankFragmentPresenter(this, getContext());
        gankFragmentPresenter.init();
    }

    @Override
    public void init() {
        gankList = new ArrayList<>();
        gankAdapter = new GankAdapter(gankList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gankAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isReachBottom((LinearLayoutManager) layoutManager) && dy > 0) {
                    page++;
                    gankFragmentPresenter.requestGankData(type, page, false);
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorAccentLight);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            gankFragmentPresenter.requestGankData(type, page, true);
        });
        swipeRefreshLayout.postDelayed(() -> gankFragmentPresenter.requestGankData(type, page, true), 256);
    }

    private boolean isReachBottom(LinearLayoutManager layoutManager) {
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = gankAdapter.getItemCount();
        return lastVisibleItem >= totalItemCount - PRELOAD_SIZE;
    }

    @Override
    public void showProgressBar() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        TipsUtils.showSnackWithAction(
                recyclerView,
                getString(R.string.error),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.retry),
                view -> gankFragmentPresenter.requestGankData(type, page, true));
    }

    @Override
    public void showNoMoreData() {
        TipsUtils.showSnack(recyclerView, "木有更多数据了~", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showGankList(List<Gank> ganks, boolean clean) {
        if (clean) {
            gankList.clear();
            gankList.addAll(ganks);
            gankAdapter.notifyDataSetChanged();
        } else {
            gankList.addAll(ganks);
            gankAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gankFragmentPresenter.release();
    }
}
