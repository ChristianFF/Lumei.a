package com.ff.lumeia.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ff.lumeia.LumeiaApp;
import com.ff.lumeia.ui.main.MainActivity;
import com.mingle.headsUp.HeadsUp;
import com.mingle.headsUp.HeadsUpManager;

/**
 * Created by feifan on 16/2/18.
 * Contacts me:404619986@qq.com
 */
public class HeadsUpUtils {
    private HeadsUpUtils() {
        throw new UnsupportedOperationException("can not be instanced!");
    }

    public static void show(String title, String message, int icon) {
        Context context = LumeiaApp.getInstance();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                111,
                new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        HeadsUpManager manage = HeadsUpManager.getInstant(context);
        HeadsUp.Builder builder = new HeadsUp.Builder(context);
        builder.setContentTitle(title).setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, false)
                .setContentText(message);
        HeadsUp headsUp = builder.buildHeadUp();
        headsUp.setSticky(true);
        manage.notify(1, headsUp);
    }
}
