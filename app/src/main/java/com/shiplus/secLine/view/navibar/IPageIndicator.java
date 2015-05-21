package com.shiplus.secLine.view.navibar;

import android.support.v4.view.ViewPager;

/**
 * Created by Administrator on 2015/5/21.
 */
public interface IPageIndicator extends ViewPager.OnPageChangeListener{

    void setViewPager(ViewPager viewPager);

    void setViewPager(ViewPager viewPager,int initialPosition);

    void setCurrentItem(int index);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void notifyDataSetChanged();
}
