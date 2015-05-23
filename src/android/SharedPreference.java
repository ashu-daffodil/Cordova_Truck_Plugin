package com.vashishatashu.traqplugin;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

public class SharedPreference {

    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        sharedPref = context.getSharedPreferences("com.vashishatashu.traqplugin.sharedPref1", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void getString(String key, CallbackContext cbcxt) {
        String getVal = sharedPref.getString(key, "");
        PluginResult result = new PluginResult(PluginResult.Status.OK, getVal);
        result.setKeepCallback(true);
        cbcxt.sendPluginResult(result);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

}

