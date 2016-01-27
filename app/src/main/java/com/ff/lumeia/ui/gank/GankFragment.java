package com.ff.lumeia.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.util.TipsUtils;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class GankFragment extends Fragment implements GankContract.View {
    private static final String EXTRA_TYPE = "extra_type";
    private static final int PRELOAD_SIZE = 6;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Gank> mGankList;
    private GankAdapter mAdapter;
    private String mType;
    private int mPage = 1;

    private GankContract.Presenter mPresenter;

    private boolean mNeedPendingUserVisibileHint = false;
    private boolean mLastUserVisibileHint = false;

    public static GankFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, title);
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(EXTRA_TYPE);
        }
        mPresenter = new GankPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGankList = new ArrayList<>();
        mAdapter = new GankAdapter(mGankList, getContext());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isReachBottom(layoutManager) && dy > 0) {
                    mPage++;
                    mPresenter.requestGankData(mType, mPage, false);
                }
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorAccentLight);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.requestGankData(mType, mPage, true);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mNeedPendingUserVisibileHint) {
            setUserVisible(mLastUserVisibileHint);
            mNeedPendingUserVisibileHint = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getActivity() == null) {
            mNeedPendingUserVisibileHint = true;
            mLastUserVisibileHint = isVisibleToUser;
        } else {
            setUserVisible(isVisibleToUser);
        }
    }

    private void setUserVisible(boolean isVisibleToUser) {
        if (isVisibleToUser && mAdapter != null && mAdapter.getItemCount() == 0) {
            mPresenter.requestGankData(mType, mPage, true);
        }
    }

    private boolean isReachBottom(LinearLayoutManager layoutManager) {
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = mAdapter.getItemCount();
        return lastVisibleItem >= totalItemCount - PRELOAD_SIZE;
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        TipsUtils.showSnackWithAction(
                mRecyclerView,
                getString(R.string.error),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.requestGankData(mType, mPage, true);
                    }
                });
    }

    @Override
    public void showNoMoreData() {
        TipsUtils.showSnack(mRecyclerView, "木有更多数据了~", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showGankList(List<Gank> ganks, boolean clean) {
        if (clean) {
            mGankList.clear();
            mGankList.addAll(ganks);
            mAdapter.notifyDataSetChanged();
        } else {
            mGankList.addAll(ganks);
            mAdapter.notifyDataSetChanged();
        }
    }
}
