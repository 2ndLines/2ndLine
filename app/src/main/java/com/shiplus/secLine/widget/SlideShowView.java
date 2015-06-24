package com.shiplus.secLine.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.shiplus.secLine.R;
import com.shiplus.secLine.adapter.SlideShowAdapter;
import com.shiplus.secLine.domain.SlideShowData;
import com.shiplus.secLine.handler.SlideShowHandler;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/6/19.
 */
public class SlideShowView extends FrameLayout implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private SlideShowIndicator indicator;
    private ScheduledExecutorService executorService;
    private SlideShowHandler handler;

    public SlideShowView(Context context) {
        super(context);
        initViews();
        handler = new SlideShowHandler(new WeakReference<SlideShowView>(this));
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_slideshow,this,false);
        viewPager = (ViewPager) view.findViewById(R.id.slide_viewpager);
        indicator = (SlideShowIndicator) view.findViewById(R.id.slide_show_indicator);
    }

    public void setData(List<SlideShowData> data){
        SlideShowAdapter adapter = new SlideShowAdapter(data);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        indicator.initialize(adapter.getCount(),0);
    }

    public void next(){
        if(viewPager.getChildCount() != 0){
            int index = (viewPager.getCurrentItem() + 1)%viewPager.getChildCount();
            viewPager.setCurrentItem(index, true);
        }
    }

    public void start(){
        if(executorService == null){
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(slideShow,1,4, TimeUnit.SECONDS);
    }

    public void stop(){
        if(executorService != null && !executorService.isShutdown()){
            executorService.shutdown();
        }
    }

    private Runnable slideShow = new Runnable() {
        @Override
        public void run() {
            handler.obtainMessage().sendToTarget();
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        indicator.updateSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case ViewPager.SCROLL_STATE_IDLE:
                start();
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
            case ViewPager.SCROLL_STATE_SETTLING:
                stop();
                break;
        }
    }
}
