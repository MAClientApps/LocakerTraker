package com.projector.callertracker.utils;


import android.util.Log;

import com.projector.callertracker.BuildConfig;


public class UserHelper {


    public static void PrintLog(String key, String log) {
        if (BuildConfig.DEBUG) {
            Log.e(key, log);
        }
    }

}