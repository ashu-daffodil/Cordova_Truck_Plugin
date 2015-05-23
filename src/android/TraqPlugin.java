package com.vashishatashu.traqplugin;

import android.content.Context;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

public class TraqPlugin extends CordovaPlugin {
    public enum ActionType {
        RECEIVE_SMS, STOP_RECEIVE_SMS, IMEI, GET_STRING, SET_STRING, CLEAR, REMOVE
    }

    private SmsReceiver smsReceiver;
    private SharedPreference sharedPref;
    private CallbackContext callback_receive;

    private boolean isReceiving = false;
    private boolean result = false;

    private PluginResult pluginResult;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        action = action.toUpperCase();
        Context context = cordova.getActivity().getApplicationContext();
        sharedPref = new SharedPreference(context);
        switch (ActionType.valueOf(action)) {
            case RECEIVE_SMS:
                // if already receiving (this case can happen if the startReception is called several times
                if (this.isReceiving) {// close the already opened callback ...
                    pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                    pluginResult.setKeepCallback(false);
                    this.callback_receive.sendPluginResult(pluginResult);// before registering a new one to the sms receiver
                }
                this.isReceiving = true;
                if (this.smsReceiver == null) {
                    this.smsReceiver = new SmsReceiver();
                    IntentFilter fp = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
                    fp.setPriority(1000); // fp.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
                    this.cordova.getActivity().registerReceiver(this.smsReceiver, fp);
                }
                this.smsReceiver.startReceiving(callbackContext);
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);
                this.callback_receive = callbackContext;
                result = true;
                break;
            case STOP_RECEIVE_SMS:
                if (this.smsReceiver != null) {
                    smsReceiver.stopReceiving();
                }
                this.isReceiving = false;
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT); // 1. Stop the receiving context
                pluginResult.setKeepCallback(false);
                this.callback_receive.sendPluginResult(pluginResult);
                pluginResult = new PluginResult(PluginResult.Status.OK); // 2. Send result for the current context
                callbackContext.sendPluginResult(pluginResult);
                result = true;
                break;
            case IMEI:
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.getDeviceId();
                String imeiVal = telephonyManager.getDeviceId();
                callbackContext.success(imeiVal);
                result = true;
                break;
            case SET_STRING:
                String setKey = args.getString(0);
                String setVal = args.getString(1);
                sharedPref.setString(setKey, setVal);
                callbackContext.success("OK");
                result = true;
                break;
            case GET_STRING:
                String getKey = args.getString(0);
                this.sharedPref.getString(getKey, callbackContext);
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);
                this.callback_receive = callbackContext;
                result = true;
                break;
            case REMOVE:
                String removeKey = args.getString(0);
                sharedPref.remove(removeKey);
                callbackContext.success("OK");
                result = true;
                break;
            case CLEAR:
                sharedPref.clear();
                callbackContext.success("OK");
                result = true;
                break;
            default:
                result = false;
        }
        return result;
    }
}