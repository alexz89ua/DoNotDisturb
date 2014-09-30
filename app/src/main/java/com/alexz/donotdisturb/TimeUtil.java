package com.alexz.donotdisturb;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AlexZ on 01.07.14.
 */
public class TimeUtil {

    public static  void setTime(Context context, String timeType) {
        //set stop service time

        Intent intent = new Intent(context, QuietReceiver.class);
        intent.setAction(timeType);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        String storedTimeTo;
        if (timeType.equals(ExtraUtils.TIME_FROM)) {
            storedTimeTo = QuietApp.getInstans().getTimeFrom();
        } else {
            storedTimeTo = QuietApp.getInstans().getTimeTo();
        }
        Log.i("Loger", "Time " + storedTimeTo);
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(storedTimeTo.substring(0, storedTimeTo.lastIndexOf(":"))));
        cal.set(Calendar.MINUTE, Integer.valueOf(storedTimeTo.substring(storedTimeTo.lastIndexOf(":") + 1, storedTimeTo.length())));
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
        Log.i("Loger", "Time " + simpleDateFormat.format(cal.getTime()));


        long time = cal.getTimeInMillis();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                time, AlarmManager.INTERVAL_DAY, sender);
    }

    public static void disableTime(Context context, String timeType) {
        //set stop service time

        Intent intent = new Intent(context, QuietReceiver.class);
        intent.setAction(timeType);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.cancel(sender);
        }
    }
}
