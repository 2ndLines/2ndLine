package com.shiplus.model.picselector.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shiplus.model.R;

/**
 * Created by Administrator on 2015/6/5.
 */
public class CheckableFrameLayout extends FrameLayout implements Checkable {
    private static final int[] STATE_CHECKED = {android.R.attr.state_checked};
    private boolean mChecked;

    public CheckableFrameLayout(Context context) {
        super(context);
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace);

        if(isChecked()){
            mergeDrawableStates(drawableState,STATE_CHECKED);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        if(this.mChecked != checked){
            mChecked = checked;
            refreshDrawableState();
            for(int i=0; i<getChildCount(); i++){
                View child =  getChildAt(i);
                if(child instanceof Checkable){
                    ((Checkable) child).setChecked(checked);
                }
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

}
