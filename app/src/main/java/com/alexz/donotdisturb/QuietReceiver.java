package com.alexz.donotdisturb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by AlexZ on 01.07.14.
 */
public class QuietReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Loger", "onReceive " + intent.getAction());

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();

            if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                String name = wifiInfo.getSSID().replaceAll("\"", "");
                Log.d("WifiReceiver", "Have Wifi Connection: " + name);
                if (QuietApp.getInstans().getTrigersWiFi().contains(name)) {
                    //For Vibrate mode
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }

            } else {
                Log.d("WifiReceiver", "Don't have Wifi Connection");
                //For Vibrate mode
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }

        if (intent.getAction().equals(ExtraUtils.TIME_FROM)) {
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }

        if (intent.getAction().equals(ExtraUtils.TIME_TO)) {
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
}
