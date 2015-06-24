package com.shiplus.secLine.view.navibar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiplus.secLine.R;

/**
 * Created by Administrator on 2015/5/21.
 */
public class IconPageIndicator extends LinearLayout implements IPageIndicator {
    private final LinearLayout mTabLayout;
    private Runnable mTabSelector;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private int mSelectedIndex;
    private OnTabReselectedListener onTabReselectedListener;


    public interface OnTabReselectedListener{
        void onTabReselected(int tabIndex);
    }

    private View.OnClickListener onTabClickListener = new OnClickListener(){
        @Override
        public void onClick(View v) {
            TabView tab = (TabView) v;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tab.getIndex();
            mViewPager.setCurrentItem(newSelected,false);

            if(newSelected == oldSelected && onTabReselectedListener != null ){
                onTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };

    public IconPageIndicator(Context context){
        this(context,null);
    }

    public IconPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        mTabLayout = new LinearLayout(context);
        addView(mTabLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    private void addTab(int index, CharSequence title, int iconResId){
        final TabView tabView = new TabView(getContext());
        tabView.setIndex(index);
        tabView.setText(title);
        tabView.setOnClickListener(onTabClickListener);
        if(iconResId > 0){
            tabView.setIcon(iconResId);
        }

        mTabLayout.addView(tabView,index,new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
    }

    private void animateToTab(int tabIndex){
        final View tab = mTabLayout.getChildAt(tabIndex);
        if(mTabSelector != null){
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            @Override
            public void run() {
                final int tabPos = tab.getLeft() - (getWidth() - tab.getWidth())/2;
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener){
        onTabReselectedListener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mTabSelector != null){
            post(mTabSelector);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mTabSelector != null){
            removeCallbacks(mTabSelector);
        }
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        if(mViewPager == viewPager) return;

        if(mViewPager != null){
            mViewPager.setOnPageChangeListener(null);
        }

        final PagerAdapter adapter = viewPager.getAdapter();
        if(adapter == null){
            throw new IllegalStateException("Fail to get ViewPager adapter. ViewPager="+viewPager);
        }

        mViewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        IIconPagerAdapter iconAdapter = null;
        if(adapter instanceof IIconPagerAdapter){
            iconAdapter = (IIconPagerAdapter) adapter;
        }

        final int count = adapter.getCount();
        for(int i=0; i<count; i++){
            CharSequence title = adapter.getPageTitle(i);
            if(title == null){
                title = "";
            }
            int iconResId = 0;
            if(iconAdapter != null){
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i,title,iconResId);
        }
        if(mSelectedIndex > count){
            mSelectedIndex = count - 1;
        }
        setCurrentItem(mSelectedIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager viewPager, int initialPosition) {
        setViewPager(viewPager);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if(mViewPager == null){
            throw new IllegalStateException("ViewPage has not been bound!!");
        }
        mSelectedIndex = item;
        mViewPager.setCurrentItem(item);
        final int tabCount = mTabLayout.getChildCount();
        for(int i=0; i<tabCount; i++){
            final View tab = mTabLayout.getChildAt(i);
            boolean selected = i == item;
            tab.setSelected(selected);
            if(selected){
                animateToTab(item);
            }
        }

    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(onPageChangeListener != null){
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
        if(onPageChangeListener != null){
            onPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(onPageChangeListener != null){
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private class TabView extends TextView{
        private int mmIndex;
        private int iconWidth;
        private int iconHeight;

        public TabView(Context context){
            this(context,null, R.attr.tabView);
        }

        public TabView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            setGravity(Gravity.CENTER_HORIZONTAL);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabView,defStyle,0);
            iconWidth = a.getDimensionPixelSize(R.styleable.TabView_iconWidth,0);
            iconHeight = a.getDimensionPixelOffset(R.styleable.TabView_iconHeight,0);
            a.recycle();
        }

        protected void setIcon(int resId){
            if(resId > 0){
                Resources res = getContext().getResources();
                Drawable icon = res.getDrawable(resId);
                int width = iconWidth == 0 ? icon.getIntrinsicWidth() : iconWidth;
                int height = iconHeight == 0 ? icon.getIntrinsicHeight() : iconHeight;

                icon.setBounds(0,0,width,height);
                setCompoundDrawables(null,icon,null,null);
            }
        }

        protected int getIndex() {
            return mmIndex;
        }

        protected void setIndex(int mmIndex) {
            this.mmIndex = mmIndex;
        }
    }
}
