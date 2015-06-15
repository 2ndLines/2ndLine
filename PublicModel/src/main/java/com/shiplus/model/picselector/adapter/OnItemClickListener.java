package com.shiplus.model.picselector.adapter;

import android.view.View;

import com.shiplus.model.picselector.domain.ImageItem;

/**
 * Created by Administrator on 2015/6/9.
 */
public interface OnItemClickListener<T> {
    void onItemClick(View view, T item, int position);
}
