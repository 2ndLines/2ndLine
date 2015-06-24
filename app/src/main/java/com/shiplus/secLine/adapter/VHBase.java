package com.shiplus.secLine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/6/16.
 */
public abstract class VHBase<Type> extends RecyclerView.ViewHolder {

    public VHBase(View itemView) {
        super(itemView);
    }

    public abstract void bindItem(Type data);

}
