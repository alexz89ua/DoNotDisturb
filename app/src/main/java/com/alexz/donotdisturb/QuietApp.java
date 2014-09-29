package com.alexz.donotdisturb;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AlexZ on 01.07.14.
 */
public class QuietApp extends Application {

    private static QuietApp self;
    private SharedPreferences sharedPreferences;
    public static final String event_update = "com.alexz.donotdisturb.event_update";



    synchronized public static QuietApp getInstans(){
        return self;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (QuietApp.class) {
            self = this;
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    public Set<String> getTrigersWiFi(){
        HashSet<String> empty = new HashSet<String>();
        empty.add("No trigers");
        return sharedPreferences.getStringSet(ExtraUtils.TRIGER_WIFI, empty);
    }


    public void addTrigersWiFi(Set<String> wifiNames){
        sharedPreferences.edit().putStringSet(ExtraUtils.TRIGER_WIFI, wifiNames).commit();
    }


    public void setTimeFrom(String timeFrom){
        sharedPreferences.edit().putString(ExtraUtils.TIME_FROM, timeFrom).commit();
    }


    public void setTimeTo(String timeTo){
        sharedPreferences.edit().putString(ExtraUtils.TIME_TO, timeTo).commit();
    }


    public String getTimeFrom(){
       return sharedPreferences.getString(ExtraUtils.TIME_FROM, "22:00");
    }


    public String getTimeTo(){
       return sharedPreferences.getString(ExtraUtils.TIME_TO, "07:00");
    }


    public void setTimeEnabled(Boolean enabled){
        sharedPreferences.edit().putBoolean(ExtraUtils.TIME_ENABLED, enabled).commit();
    }


    public  boolean getTimeEnabled(){
        return sharedPreferences.getBoolean(ExtraUtils.TIME_ENABLED, false);
    }

}
