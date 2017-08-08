package com.github.ykrank.edgecentersnaphelper.demo;

import android.util.Log;


public class L {
    public static final String LOG_TAG = "EdgeCenterSnapHelper";

    public static boolean showLog() {
        return true;
    }

    public static void d(String msg) {
        d(LOG_TAG, msg);
    }

    public static void w(String msg) {
        w(LOG_TAG, msg);
    }

    public static void w(Throwable e) {
        w(LOG_TAG, e);
    }

    public static void i(String msg) {
        i(LOG_TAG, msg);
    }

    public static void e(String msg) {
        e(LOG_TAG, msg);
    }

    public static void e(Throwable e) {
        e(LOG_TAG, e);
    }

    public static void d(String msg, Throwable tr) {
        d(LOG_TAG, msg, tr);
    }

    public static void i(String msg, Throwable tr) {
        i(LOG_TAG, msg, tr);
    }

    public static void w(String msg, Throwable tr) {
        w(LOG_TAG, msg, tr);
    }

    public static void e(String msg, Throwable tr) {
        e(LOG_TAG, msg, tr);
    }

    public static void d(String tag, String msg) {
        if (showLog()) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (showLog()) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (showLog()) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (showLog()) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (showLog()) {
            Log.d(tag, msg, tr);
        }
    }


    public static void i(String tag, String msg, Throwable tr) {
        if (showLog()) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (showLog()) {
            Log.w(tag, msg, tr);
        }
    }


    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }
}
