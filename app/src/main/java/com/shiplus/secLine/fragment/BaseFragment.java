package com.shiplus.secLine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiplus.secLine.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = (TextView) inflater.inflate(R.layout.fragment_base,container,false);
        textView.setText(getTitle());
        return textView;
    }
}
