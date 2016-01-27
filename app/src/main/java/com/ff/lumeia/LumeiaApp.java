package com.ff.lumeia;

import android.app.Application;
import android.content.Context;

import com.litesuits.orm.LiteOrm;

/**
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public class LumeiaApp extends Application {
    private static final String DB_NAME = "Lumei.a.db";
    private static Context appContext;
    private static LiteOrm myDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        createDB();
    }

    private void createDB() {
        myDatabase = LiteOrm.newSingleInstance(appContext, DB_NAME);
        if (BuildConfig.DEBUG) {
            myDatabase.setDebugged(true);
        }
    }
}
