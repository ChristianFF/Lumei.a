package com.ff.lumeia.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.ff.lumeia.R;
import com.ff.lumeia.util.HeadsUpUtils;
import com.ff.lumeia.util.SharedPreferenceUtils;

/**
 * Created by feifan on 16/2/17.
 * Contacts me:404619986@qq.com
 */
public class DailyReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SharedPreferenceUtils.getBoolean("daily_reminder", false)) {
            HeadsUpUtils.show(context.getString(R.string.app_name),
                    context.getString(R.string.meizi_notification_message),
                    R.drawable.ic_books_white);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            long oneDay = 24 * 60 * 60 * 1000;
            long triggerAtTime = SystemClock.elapsedRealtime() + oneDay;
            Intent i = new Intent(context, LongRunningReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
    }
}
