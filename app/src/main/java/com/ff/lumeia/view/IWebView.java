package com.ff.lumeia.view;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public interface IWebView extends IBaseView {
    void showProgressBar(int progress);

    void setWebTitle(String title);

    void showOpenResult(boolean isSucceed);

    void showCopyResult(boolean isSucceed);
}
