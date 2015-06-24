package com.shiplus.secLine.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiplus.secLine.R;
import com.shiplus.secLine.domain.SlideShowData;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public class SlideShowAdapter extends PagerAdapter {
    private List<SlideShowData> list;

    public SlideShowAdapter(List<SlideShowData> items){
        list = items == null ? Collections.<SlideShowData>emptyList():items;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    private SlideShowData getItem(int position){
        return list.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_slideshow_item,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.slide_item_iv);
        TextView textView = (TextView) view.findViewById(R.id.slide_item_tv);
        textView.setText(getItem(position).getSummary());
        Glide.with(container.getContext())
                .load(getItem(position).getImageUrl())
                .centerCrop()
                .into(imageView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
