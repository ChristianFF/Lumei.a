package com.ff.lumeia.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by feifan on 16/2/17.
 * Contacts me:404619986@qq.com
 */
public class LongRunningReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent starter = new Intent(context, DailyReminderReceiver.class);
        context.sendBroadcast(starter);
    }
}
