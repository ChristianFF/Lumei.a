package com.ff.lumeia.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ff.lumeia.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(provideViewLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract int provideViewLayoutId();

    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
