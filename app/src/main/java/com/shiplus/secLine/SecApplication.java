package com.shiplus.secLine;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2015/5/19.
 */
public class SecApplication extends Application {
    private static final String LC_APP_ID = "prt1dn62emtnqkgj7pcrmpblhw94eub0hp403nh7f2y02uio";
    private static final String LC_APP_KEY = "13hh4rj1088tdyazezcd87wvmdwai0x8zrgx198snv8uy1ck";

    @Override
    public void onCreate() {
        super.onCreate();
        initLeanCloud();
    }

    private void initLeanCloud(){
        AVOSCloud.initialize(this.getApplicationContext(),LC_APP_ID,LC_APP_KEY);
    }

}
