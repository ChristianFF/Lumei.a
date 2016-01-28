package com.ff.lumeia.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.ff.lumeia.R;

/**
 * Created by feifan on 16/1/28.
 * Contacts me:404619986@qq.com
 */
public class ShareUtils {
    private ShareUtils() {
        throw new UnsupportedOperationException("can not be instanced!");
    }

    public static void shareImage(Context context, Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)));
    }
}
