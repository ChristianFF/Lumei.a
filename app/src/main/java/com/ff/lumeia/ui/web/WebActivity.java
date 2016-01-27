package com.ff.lumeia.ui.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ff.lumeia.R;
import com.ff.lumeia.ui.BaseActivity;
import com.ff.lumeia.util.TipsUtils;
import com.google.common.base.Preconditions;

public class WebActivity extends BaseActivity implements WebContract.View {
    private static final String WEB_TITLE = "web_title";
    private static final String WEB_URL = "web_url";

    NumberProgressBar numberProgressBar;
    WebView webView;
    LinearLayout layoutWeb;

    private String webTitle;
    private String webUrl;

    private WebContract.Presenter mPresenter;

    public static Intent createIntent(Context context, String webTitle, String webUrl) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WEB_TITLE, webTitle);
        intent.putExtra(WEB_URL, webUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new WebPresenter(this);

        numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        webView = (WebView) findViewById(R.id.web_view);
        layoutWeb = (LinearLayout) findViewById(R.id.layout_web);

        Intent intent = getIntent();
        webTitle = intent.getStringExtra(WEB_TITLE);
        webUrl = intent.getStringExtra(WEB_URL);
        mToolbar.setTitle(webTitle);

        mPresenter.setWebViewSetting(webView, webUrl);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
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
    }

    @Override
    public void setPresenter(WebContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
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
    protected int provideMenuResId() {
        return R.menu.menu_web;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                mPresenter.refresh(webView);
                return true;

            case R.id.copy_url:
                mPresenter.copyUrl(webUrl);
                return true;

            case R.id.open_in_browser:
                mPresenter.openInBrowser(webUrl);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
