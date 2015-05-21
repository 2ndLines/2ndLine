package com.shiplus.secLine.domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 2015/5/19.
 * Store used product type.
 */
@AVClassName("AVPdtType")
public class AVPdtType extends AVObject {

    public String getDescription() {
        return getString(AVKey.KEY_TYPE);
    }

    public void setDescription(String type) {
        put(AVKey.KEY_TYPE, type);
    }

    public int getUsageCount() {
        return getInt(AVKey.KEY_COUNT);
    }

    public void increaseUsageCount(){
        increment(AVKey.KEY_COUNT);
    }

    public void increment(){
        increment(AVKey.KEY_COUNT);
    }
}
