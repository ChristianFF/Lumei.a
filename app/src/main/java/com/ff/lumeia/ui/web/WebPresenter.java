package com.ff.lumeia.ui.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ff.lumeia.util.FileUtils;
import com.google.common.base.Preconditions;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

class WebPresenter implements WebContract.Presenter {
    private WebContract.View mView;
    private Context mContext;

    WebPresenter(WebContract.View view) {
        mView = Preconditions.checkNotNull(view, "webview cannot be null!");
        mView.setPresenter(this);
        mContext = (Context) mView;
    }

    @Override
    public void setWebViewSetting(WebView webView, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new GankClient());
        webView.loadUrl(url);
    }

    @Override
    public void refresh(WebView webView) {
        webView.reload();
    }

    @Override
    public void copyUrl(String text) {
        mView.showCopyResult(FileUtils.copyToClipBoard(mContext, text));
    }

    @Override
    public void openInBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        } else {
            mView.showOpenResult(false);
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mView.showProgressBar(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mView.setWebTitle(title);
        }
    }

    private class GankClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                view.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
