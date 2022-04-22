package com.example.movieapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Static way to get application context from anywhere.
 * Source: https://stackoverflow.com/a/5114361
 */
public class ApplicationContext {
    private static Context context;
    private static String logTag = "ApplicationContext";

    public static void setContext(Context context) {
        ApplicationContext.context = context;
    }

    public static Context getAppContext() {
        Log.v(logTag, "getAppContext()");
        Log.v(logTag, "context: " + context);
        return ApplicationContext.context;
    }
}
