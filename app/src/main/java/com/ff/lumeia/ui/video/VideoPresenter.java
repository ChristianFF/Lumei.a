package com.ff.lumeia.ui.video;

import android.media.MediaPlayer;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.common.base.Preconditions;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class VideoPresenter implements VideoContarct.Presenter {
    private VideoContarct.View mView;

    VideoPresenter(VideoContarct.View view) {
        mView = Preconditions.checkNotNull(view, "videoview cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void loadWebVideo(WebView webView, String url) {
        webView.setWebChromeClient(new ChromeClient());
        webView.loadUrl(url);
    }

    private class ChromeClient extends WebChromeClient
            implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer player) {
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                }
                player.reset();
                player.release();
            }
        }

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

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
