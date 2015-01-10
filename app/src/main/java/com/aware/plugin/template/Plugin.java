package com.aware.plugin.template;

import android.util.Log;

import com.aware.utils.Aware_Plugin;

/**
 * Created by denzil on 10/1/15.
 */
public class Plugin extends Aware_Plugin {

    @Override
    public void onCreate() {
        super.onCreate();

        TAG = "Template";

        Log.d(TAG, "Template plugin running");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Template plugin terminated");
    }
}
