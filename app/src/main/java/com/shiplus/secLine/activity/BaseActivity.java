package com.shiplus.secLine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shiplus.secLine.R;


/**
 * Created by Administrator on 2015/5/21.
 */
public class BaseActivity extends AppCompatActivity {

    protected void initActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
    }

    protected void enableHomeAsUp(boolean enable){
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(enable);
    }

    protected void showLogo(){
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    protected void showTitle(){
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
