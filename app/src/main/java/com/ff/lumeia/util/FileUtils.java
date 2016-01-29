package com.ff.lumeia.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.ff.lumeia.BuildConfig;
import com.ff.lumeia.LumeiaConfig;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by feifan on 16/1/28.
 * Contacts me:404619986@qq.com
 */
public class FileUtils {
    private FileUtils() {
        throw new UnsupportedOperationException("can not be instanced!");
    }

    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static Uri saveBitmapToSDCard(Bitmap bitmap, String title) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Lumei.a");
        if (!appDir.exists()) {
            boolean isSucceed = appDir.mkdirs();
            if (BuildConfig.DEBUG) {
                Log.v(FileUtils.class.getSimpleName(), String.valueOf(isSucceed));
            }
        }
        String fileName = title.replace("/", "-") + "-girl.jpg";
        File file = new File(appDir, fileName);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(file);
    }

    public static boolean copyToClipBoard(Context context, String text) {
        ClipData clipData = ClipData.newPlainText(LumeiaConfig.URL_COPY, text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        return manager.hasPrimaryClip();
    }
}
