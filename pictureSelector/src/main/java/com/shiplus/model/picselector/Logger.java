package com.shiplus.model.picselector;

import android.util.Log;

/**
 * Created by Administrator on 2015/6/5.
 */
public class Logger {
    private static final boolean DEBUG = true;

    public static void e(String tag, String message, Throwable throwable){
        if(DEBUG) Log.e(tag,message,throwable);
    }

    public static void e(String tag, String message ){
        if(DEBUG) Log.e(tag, message);
    }

    public static void w(String tag, String message){
        if(DEBUG) Log.w(tag, message);
    }

    public static void d(String tag, String message){
        if(DEBUG) Log.d(tag, message);
    }

}
