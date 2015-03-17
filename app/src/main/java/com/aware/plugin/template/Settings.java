package com.aware.plugin.template;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.aware.Aware;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String STATUS_PLUGIN_TEMPLATE = "status_plugin_template";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        syncSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncSettings();
    }

    private void syncSettings() {
        //Make sure to load the latest values
        CheckBoxPreference status = (CheckBoxPreference) findPreference(STATUS_PLUGIN_TEMPLATE);
        status.setChecked(Aware.getSetting(this, STATUS_PLUGIN_TEMPLATE).equals("true"));

        //...
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference setting = findPreference(key);

        if( setting.getKey().equals(STATUS_PLUGIN_TEMPLATE) ) {
            boolean is_active = sharedPreferences.getBoolean(setting.getKey(), false);
            Aware.setSetting(this, setting.getKey(), is_active);
            if( is_active ) {
                Aware.startPlugin(this, getPackageName());
            } else {
                Aware.stopPlugin(this, getPackageName());
            }
        }

        //Apply the new settings
        Intent apply = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(apply);
    }
}
