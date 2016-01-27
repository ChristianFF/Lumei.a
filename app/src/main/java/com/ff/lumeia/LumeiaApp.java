package com.ff.lumeia;

import android.app.Application;
import android.content.Context;

import com.ff.lumeia.util.SharedPreferenceUtils;

/**
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public class LumeiaApp extends Application {
    private static LumeiaApp sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtils.init(this);
    }

    public static LumeiaApp getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }
}
