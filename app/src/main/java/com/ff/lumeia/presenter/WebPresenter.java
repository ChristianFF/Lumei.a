package com.ff.lumeia.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ff.lumeia.util.FileUtils;
import com.ff.lumeia.view.IWebView;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class WebPresenter extends BasePresenter<IWebView> {

    public WebPresenter(IWebView iView, Context context) {
        super(iView, context);
    }

    @Override
    public void release() {

    }

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

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            iView.showProgressBar(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            iView.setWebTitle(title);
        }
    }

    public void refresh(WebView webView) {
        webView.reload();
    }

    public void copyUrl(String text) {
        iView.showCopyResult(FileUtils.copyToClipBoard(context, text));
    }

    public void openInBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            iView.showOpenResult(false);
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
}
