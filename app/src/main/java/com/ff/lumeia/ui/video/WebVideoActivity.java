package com.ff.lumeia.ui.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ff.lumeia.R;
import com.ff.lumeia.ui.BaseActivity;
import com.google.common.base.Preconditions;

public class WebVideoActivity extends BaseActivity implements VideoContarct.View {
    private static final String WEB_TITLE = "web_title";
    private static final String WEB_URL = "web_url";

    WebVideoView webVideoView;
    NumberProgressBar numberProgressBar;

    private VideoContarct.Presenter mPresenter;

    public static void start(Context context, String title, String url) {
        Intent starter = new Intent(context, WebVideoActivity.class);
        starter.putExtra(WEB_TITLE, title);
        starter.putExtra(WEB_URL, url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new VideoPresenter(this);

        webVideoView = (WebVideoView) findViewById(R.id.web_video_view);
        numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        mAppBarLayout.setBackgroundColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(0);
        }

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        if (TextUtils.isEmpty(intent.getStringExtra(WEB_TITLE))) {
            setTitle(getString(R.string.app_name));
        } else {
            setTitle(getIntent().getStringExtra(WEB_TITLE));
        }
        if (TextUtils.isEmpty(intent.getStringExtra(WEB_URL))) {
            finish();
        } else {
            mPresenter.loadWebVideo(webVideoView, getIntent().getStringExtra(WEB_URL));
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_video;
    }

    @Override
    public void showProgressBar(int progress) {
        if (numberProgressBar == null) return;
        numberProgressBar.setProgress(progress);
        if (progress == 100) {
            numberProgressBar.setVisibility(View.GONE);
        } else {
            numberProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setWebTitle(String title) {
        setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webVideoView != null) {
            webVideoView.resumeTimers();
            webVideoView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webVideoView != null) {
            webVideoView.onPause();
            webVideoView.pauseTimers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webVideoView != null) {
            webVideoView.setWebViewClient(null);
            webVideoView.setWebChromeClient(null);
            webVideoView.destroy();
        }
    }

    @Override
    public void setPresenter(VideoContarct.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }
}
