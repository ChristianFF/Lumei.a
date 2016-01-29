package com.ff.lumeia.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ff.lumeia.LumeiaConfig;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.presenter.WebPresenter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.util.TipsUtils;
import com.ff.lumeia.view.IWebView;

import butterknife.Bind;

public class WebActivity extends ToolbarActivity<WebPresenter> implements IWebView {

    @Bind(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.layout_web)
    LinearLayout layoutWeb;

    private WebPresenter webPresenter;
    private Gank gank;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initPresenter() {
        webPresenter = new WebPresenter(this, this);
        webPresenter.init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        gank = (Gank) intent.getSerializableExtra(LumeiaConfig.GANK);
        toolbar.setTitle(gank.desc);
        webPresenter.setWebViewSetting(webView, gank.url);
    }

    @Override
    protected void onResume() {
        if (webView != null) {
            webView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            layoutWeb.removeView(webView);
            webView.removeAllViews();
            webView.destroy();
        }
        webPresenter.release();
    }

    @Override
    public void showProgressBar(int progress) {
        if (numberProgressBar == null) {
            return;
        }
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
    public void showOpenResult(boolean isSucceed) {
        if (!isSucceed) {
            TipsUtils.showSnack(webView,
                    getString(R.string.browser_open_failed),
                    Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void showCopyResult(boolean isSucceed) {
        if (isSucceed) {
            TipsUtils.showSnack(webView,
                    getString(R.string.copy_success),
                    Snackbar.LENGTH_SHORT);
        } else {
            TipsUtils.showSnack(webView,
                    getString(R.string.copy_failed),
                    Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        onBackPressed();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                webPresenter.refresh(webView);
                break;
            case R.id.copy_url:
                webPresenter.copyUrl(gank.url);
                break;
            case R.id.open_in_browser:
                webPresenter.openInBrowser(gank.url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
