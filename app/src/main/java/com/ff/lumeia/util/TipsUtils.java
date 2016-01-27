package com.ff.lumeia.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 提示工具类
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class TipsUtils {
    private TipsUtils() {
        throw new UnsupportedOperationException("can not be instanced!");
    }


    public static void showSnackWithAction(View view, String message, int duration, String tip, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, duration)
                .setAction(tip, onClickListener)
                .show();
    }

    public static void showSnack(View view, String message, int duration) {
        Snackbar.make(view, message, duration)
                .show();
    }
}
