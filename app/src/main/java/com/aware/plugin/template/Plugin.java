package com.aware.plugin.template;

import android.content.Intent;
import android.util.Log;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;

public class Plugin extends Aware_Plugin {

    @Override
    public void onCreate() {
        super.onCreate();

        TAG = "Template";
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

        if( DEBUG ) Log.d(TAG, "Template plugin running");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //This function gets called every 5 minutes by AWARE to make sure its still running.

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if( DEBUG ) Log.d(TAG, "Template plugin terminated");
    }
}
