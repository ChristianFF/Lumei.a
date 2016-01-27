package com.ff.lumeia.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by feifan on 16/2/17.
 * Contacts me:404619986@qq.com
 */
public class SharedPreferenceUtils {
    private static SharedPreferenceUtils INSTANCE;
    private static final String TAG = "SPUtils";
    private ConcurrentMap<String, SoftReference<Object>> mCache;
    private String mPrefFileName = "com.ff.sp";
    private Context mContext;

    private SharedPreferenceUtils(Context context, String prefFileName) {
        mContext = context.getApplicationContext();
        mCache = new ConcurrentHashMap<>();
        initPrefName(prefFileName);
    }

    /**
     * 初始化
     */
    public static SharedPreferenceUtils init(Context context, String prefFileName) {
        if (INSTANCE == null) {
            synchronized (SharedPreferenceUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SharedPreferenceUtils(context, prefFileName);
                }
            }
        }
        return INSTANCE;
    }

    public static SharedPreferenceUtils init(Context context) {
        return init(context, null);
    }

    private static SharedPreferenceUtils getInstance() {
        if (INSTANCE == null)
            throw new NullPointerException("you show invoke SharedPreferencesUtils.init() before you use it ");

        return INSTANCE;
    }

    /**
     * 保存数据的方法
     */
    public static SharedPreferenceUtils putInt(String key, int val) {
        return getInstance().put(key, val);
    }

    public static SharedPreferenceUtils putLong(String key, long val) {
        return getInstance().put(key, val);
    }

    public static SharedPreferenceUtils putString(String key, String val) {
        return getInstance().put(key, val);
    }

    public static SharedPreferenceUtils putBoolean(String key, boolean val) {
        return getInstance().put(key, val);
    }

    public static SharedPreferenceUtils putFloat(String key, float val) {
        return getInstance().put(key, val);
    }

    /**
     * 得到保存数据的方法
     */
    public static int getInt(String key, int defaultVal) {
        return (int) (getInstance().get(key, defaultVal));
    }

    public static long getLong(String key, long defaultVal) {
        return (long) (getInstance().get(key, defaultVal));
    }

    public static String getString(String key, String defaultVal) {
        return (String) (getInstance().get(key, defaultVal));
    }

    public static boolean getBoolean(String key, boolean defaultVal) {
        return (boolean) (getInstance().get(key, defaultVal));
    }

    public static float getFloat(String key, float defaultVal) {
        return (float) (getInstance().get(key, defaultVal));
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static SharedPreferenceUtils remove(String key) {
        return INSTANCE._remove(key);
    }

    /**
     * 清除所有数据
     */
    public static SharedPreferenceUtils clear() {
        return INSTANCE._clear();
    }

    private void initPrefName(String prefFileName) {
        if (null != prefFileName && prefFileName.trim().length() > 0) {
            mPrefFileName = prefFileName;
        } else {
            Log.d(TAG, "prefFileName is invalid , we will use default value ");
        }
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(String key) {
        return mCache.get(key).get() != null || getSharedPreferences().contains(key);
    }

    private SharedPreferenceUtils _remove(String key) {
        mCache.remove(key);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    private SharedPreferenceUtils _clear() {
        mCache.clear();
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    private <T> SharedPreferenceUtils put(String key, T t) {
        mCache.put(key, new SoftReference<Object>(t));
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (t instanceof String) {
            editor.putString(key, (String) t);
        } else if (t instanceof Integer) {
            editor.putInt(key, (Integer) t);
        } else if (t instanceof Boolean) {
            editor.putBoolean(key, (Boolean) t);
        } else if (t instanceof Float) {
            editor.putFloat(key, (Float) t);
        } else if (t instanceof Long) {
            editor.putLong(key, (Long) t);
        } else {
            Log.d(TAG, "you may be put a invalid object :" + t);
            editor.putString(key, t.toString());
        }

        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }


    private Object readDisk(String key, Object defaultObject) {
        SharedPreferences sp = getSharedPreferences();

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        Log.e(TAG, "you can not read object , which class is " + defaultObject.getClass().getSimpleName());
        return null;

    }

    private Object get(String key, Object defaultVal) {
        SoftReference reference = mCache.get(key);
        Object val = null;
        if (null == reference || null == reference.get()) {
            val = readDisk(key, defaultVal);
            mCache.put(key, new SoftReference<>(val));
        }
        val = mCache.get(key).get();
        return val;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException ignored) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(final SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ignored) {
            }

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    editor.commit();
                    return null;
                }
            };
        }
    }
}
