package com.shiplus.secLine.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2015/6/16.
 */
public class BounceScrollView extends ScrollView {
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;

    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        int distance = (int) (getResources().getDisplayMetrics().density * MAX_Y_OVERSCROLL_DISTANCE);

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, /*maxOverScrollY*/distance, isTouchEvent);
    }
}
