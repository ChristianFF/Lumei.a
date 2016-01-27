package com.ff.lumeia.ui.video;

import android.webkit.WebView;

import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class VideoContarct {
    interface View extends BaseView<Presenter> {
        void showProgressBar(int progress);

        void setWebTitle(String title);
    }

    interface Presenter extends BasePresenter {
        void loadWebVideo(WebView webView, String url);
    }
}
