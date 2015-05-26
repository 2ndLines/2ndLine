package com.shiplus.secLine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by Administrator on 2015/5/21.
 */
public class BaseActivity extends ActionBarActivity {
    private ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
    }

    protected void enableHomeAsUp(boolean enable){
        actionBar.setDefaultDisplayHomeAsUpEnabled(enable);
    }

    protected void showLogo(){
        actionBar.setDisplayUseLogoEnabled(true);
    }

    protected void showTitle(){
        actionBar.setDisplayShowTitleEnabled(true);
    }
}
