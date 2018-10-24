package com.catchzeng.swiftbluetoothandroid.bluetooth.util;

import android.util.Log;

public class LogUtil {
    public static Boolean enableLog = false;

    private static final String TAG = "SwiftBluetooth";

    public static void e(String msg) {
        if (!enableLog) {
            return;
        }
        Log.e(TAG,msg);
    }

    public static void i(String msg) {
        if (!enableLog) {
            return;
        }
        Log.i(TAG,msg);
    }

    public static void v(String msg) {
        if (!enableLog) {
            return;
        }
        Log.v(TAG,msg);
    }
}
