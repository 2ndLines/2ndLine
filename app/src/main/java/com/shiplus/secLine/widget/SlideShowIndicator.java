package com.shiplus.secLine.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shiplus.secLine.R;

/**
 * Created by Administrator on 2015/6/19.
 */
public class SlideShowIndicator extends LinearLayout{

    public SlideShowIndicator(Context context) {
        super(context);
    }

    public SlideShowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initialize(int totalSize, int defIndex){
        for(int i=0;i <totalSize; i++){
            ImageView dot = new ImageView(getContext());
            if(i == defIndex) {
                dot.setImageResource(R.drawable.dot_selected);
            }else{
                dot.setImageResource(R.drawable.dot_normal);
            }
            addView(dot);
        }
        requestLayout();
    }


    public void updateSelected(int position){
        int count = getChildCount();
        for(int i=0; i<count; i++){
            ImageView iv = (ImageView) getChildAt(i);
            if(i == position){
                iv.setImageResource(R.drawable.dot_selected);
            }else{
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }

}
