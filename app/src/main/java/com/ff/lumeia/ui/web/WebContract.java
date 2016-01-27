package com.ff.lumeia.ui.web;

import android.webkit.WebView;

import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class WebContract {
    interface View extends BaseView<Presenter>{
        void showProgressBar(int progress);

        void setWebTitle(String title);

        void showOpenResult(boolean isSucceed);

        void showCopyResult(boolean isSucceed);
    }

    interface Presenter extends BasePresenter{
        void setWebViewSetting(WebView webView, String url);

        void refresh(WebView webView);

        void copyUrl(String text);

        void openInBrowser(String url);
    }
}
