package com.shiplus.secLine.fragment;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

/**
 * Created by Administrator on 2015/5/21.
 */
public class BaseFragment extends Fragment {
    private String title;
    private int iconResId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(int resId){
        if(resId > 0){
            title = getResources().getString(resId);
        }
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
