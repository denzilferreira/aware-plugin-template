package com.aware.plugin.template;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ui.PermissionsHandler;
import com.aware.utils.Aware_Plugin;

public class Plugin extends Aware_Plugin {

    @Override
    public void onCreate() {
        super.onCreate();

        TAG = "AWARE::"+getResources().getString(R.string.app_name);

        //Any active plugin/sensor shares its overall context using broadcasts
        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                //Broadcast your context here
            }
        };

        //Add permissions you need (Support for Android M) e.g.,
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Intent permissions = new Intent(this, PermissionsHandler.class);
        permissions.putExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
        permissions.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(permissions);

        //To sync data to the server, you'll need to set this variables from your ContentProvider
        DATABASE_TABLES = Provider.DATABASE_TABLES;
        TABLES_FIELDS = Provider.TABLES_FIELDS;
        CONTEXT_URIS = new Uri[]{ Provider.TableOne_Data.CONTENT_URI }; //this syncs dummy TableOne_Data to server

        //Activate plugin -- do this ALWAYS as the last thing (this will restart your own plugin and apply the settings)
        Aware.startPlugin(this, "com.aware.plugin.template");
    }

    //This function gets called every 5 minutes by AWARE to make sure this plugin is still running.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Check if we still have the permissions/are we good to go?
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Check if the user has toggled the debug messages
            DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

            //Initialize our plugin's settings
            Aware.setSetting(this, Settings.STATUS_PLUGIN_TEMPLATE, true);

            //Activate programmatically any sensors/plugins you need here
            //Aware.setSetting(this, Aware_Preferences.FREQUENCY_ACCELEROMETER, 200000);
            //Aware.startAccelerometer(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Aware.setSetting(this, Settings.STATUS_PLUGIN_TEMPLATE, false);

        //Deactivate any sensors/plugins you activated here
        //e.g.,
        //Aware.stopAccelerometer(this);

        //Stop plugin
        Aware.stopPlugin(this, "com.aware.plugin.template");
    }
}
