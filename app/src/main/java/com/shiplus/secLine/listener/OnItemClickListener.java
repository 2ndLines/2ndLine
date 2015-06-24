package com.shiplus.secLine.listener;

import android.view.View;

/**
 * Created by Administrator on 2015/6/16.
 */
public interface OnItemClickListener<Type> {
    /**
     * @param view
     * @param item
     * @param position
     */
    void onItemClick(View view, Type item, int position);
}
