package com.shiplus.secLine.handler;

import android.os.Handler;
import android.os.Message;

import com.shiplus.secLine.widget.SlideShowView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/6/23.
 */
public class SlideShowHandler extends Handler {

    private WeakReference<SlideShowView> slideShowRef;

    public SlideShowHandler(WeakReference<SlideShowView> reference){
        slideShowRef = reference;
    }

    @Override
    public void handleMessage(Message msg) {
        slideShowRef.get().next();
    }
}
