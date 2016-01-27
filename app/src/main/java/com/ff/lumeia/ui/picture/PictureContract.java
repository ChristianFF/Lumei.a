package com.ff.lumeia.ui.picture;

import android.content.Context;
import android.graphics.Bitmap;

import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class PictureContract {
    interface View extends BaseView<Presenter> {
        void showSaveDialog();

        void showSaveResult(boolean isSucceed);
    }

    interface Presenter extends BasePresenter {
        boolean grantExternalPermission(Context context);

        void resolveExternalPermissionResult(Context context, int requestCode, String[] permissions, int[] grantResults,
                                             Bitmap bitmap, String title);

        void saveMeiziPicture(final Bitmap bitmap, final String title);

        void shareMeiziPictureToFriends(final Bitmap bitmap, final String title);
    }
}
