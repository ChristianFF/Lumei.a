/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 * Copyright (C) 2016 Panl <panlei106@gmail.com>
 * CopyRight (C) 2016 ChristianFF <feifan0322@gmail.com>
 *
 * Lumei.a is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lumei.a is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Lumei.a.  If not, see <http://www.gnu.org/licenses/>.
 */

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
