package com.aware.plugin.template;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;

public class Plugin extends Aware_Plugin {

    @Override
    public void onCreate() {
        super.onCreate();

        TAG = "AWARE::"+getResources().getString(R.string.app_name);
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

        //Initialize our plugin's settings
        if( Aware.getSetting(this, Settings.STATUS_PLUGIN_TEMPLATE).length() == 0 ) {
            Aware.setSetting(this, Settings.STATUS_PLUGIN_TEMPLATE, true);
        }

        //Activate programmatically any sensors/plugins you need here
        //e.g., Aware.setSetting(this, Aware_Preferences.STATUS_ACCELEROMETER,true);

        //Any active plugin/sensor shares its overall context using broadcasts
        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                //Broadcast your context here
            }
        };

        //To sync data to the server, you'll need to set this variables from your ContentProvider
        //DATABASE_TABLES =
        //TABLES_FIELDS =
        //CONTEXT_URIS = new Uri[]{ }

        //Activate plugin
        Aware.startPlugin(this, getPackageName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //This function gets called every 5 minutes by AWARE to make sure this plugin is still running.
        TAG = "AWARE::"+getResources().getString(R.string.app_name);
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Aware.setSetting(this, Settings.STATUS_PLUGIN_TEMPLATE, false);

        //Deactivate any sensors/plugins you activated here
        //e.g., Aware.setSetting(this, Aware_Preferences.STATUS_ACCELEROMETER, false);

        //Stop plugin
        Aware.stopPlugin(this, getPackageName());
    }
}
