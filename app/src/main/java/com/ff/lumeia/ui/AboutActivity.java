package com.ff.lumeia.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;

import com.ff.lumeia.R;
import com.ff.lumeia.presenter.AboutPresenter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.view.IAboutView;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends ToolbarActivity<AboutPresenter> implements IAboutView {

    @Bind(R.id.img_profile)
    CircleImageView imgProfile;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.fab_thumb_up)
    FloatingActionButton fabThumbUp;

    private AboutPresenter aboutPresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initPresenter() {
        aboutPresenter = new AboutPresenter(this, this);
        aboutPresenter.init();
    }

    @Override
    public void init() {

    }
}
