package com.shiplus.model.photoview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiplus.model.R;
import com.shiplus.model.picselector.Logger;
import com.shiplus.model.picselector.domain.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/6/9.
 */
public class PhotoActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_LIST = "extra_image_list";
    public static final String EXTRA_ACTION_TYPE = "extra_type";
    public static final int ACTION_VIEW = 0;
    public static final int ACTION_PICK = 1;

    private static final String TAG = "PhotoActivity";
    private ViewPager mViewPager;
    private PhotoPagerAdapter pagerAdapter;
    private List<ImageItem> mData;
    private int actionType = ACTION_VIEW;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initData(getIntent());
        findViews();
    }

    private void initData(Intent intent){
        if(intent == null) return;
        mData = intent.getParcelableArrayListExtra(EXTRA_IMAGE_LIST);
        Logger.d(TAG, ">> image size =  " + mData.size());
        actionType = intent.getIntExtra(EXTRA_ACTION_TYPE,0);
    }

    private void findViews(){
        mViewPager = (ViewPager) findViewById(R.id.photo_view_pager);
        pagerAdapter = new PhotoPagerAdapter(mData);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.view_pager_spacing));

        initBottomBar(actionType);
    }

    private void initBottomBar(int actionType){
        if(actionType != ACTION_VIEW){
            ViewStub stub = (ViewStub) findViewById(R.id.viewstub_photoviewer_bottom_bar);
            View bottom = stub.inflate();
            ImageButton button = (ImageButton) bottom.findViewById(R.id.photo_button_confirm);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(EXTRA_IMAGE_LIST, pagerAdapter.getSelectedImages());
                    Logger.d(TAG,"selected images size = " + pagerAdapter.getSelectedImages().size());
                    PhotoActivity.this.setResult(Activity.RESULT_OK, intent);
                    PhotoActivity.this.finish();
                }
            });
        }
    }

    private class PhotoPagerAdapter extends PagerAdapter{
        private List<ImageItem> imageList;
        private HashMap<Integer, Boolean> positionMarker;

        public PhotoPagerAdapter(List<ImageItem> images){
            init(images);
        }

        private void init(List<ImageItem> data){
            imageList = data;
            markImagePosition();
        }
        private void markImagePosition(){
            if(imageList == null) return;
            if(positionMarker == null) positionMarker = new HashMap<>();
            positionMarker.clear();
            for(ImageItem str : imageList ){
                int index = imageList.indexOf(str);
                positionMarker.put(index,true);
            }
        }

        public void swapData(List<ImageItem> newData){
            if(imageList == newData) return;
            List oldData = imageList;
            init(newData);
            if(oldData != null) oldData.clear();
            notifyDataSetChanged();
        }

        public void setChecked(int position, boolean checked){
            positionMarker.put(position, checked);
        }

        public ArrayList<ImageItem> getSelectedImages(){
            ArrayList<ImageItem> result = new ArrayList<>();
            Iterator<Integer> iterator = positionMarker.keySet().iterator();

            while(iterator.hasNext()){
                int index = iterator.next();
                boolean checked = positionMarker.get(index);
                if(checked){
                    result.add(imageList.get(index));
                }
            }
            return result;
        }

        private String getPath(int position, boolean thumbnail){
            if(imageList == null) return null;
            ImageItem item = imageList.get(position);
            return thumbnail? item.getThumbnail():item.getOriginal();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_photo_item, container,false);

            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.photo_viewer_checkbox);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setChecked(position,isChecked);
                }
            });

            PhotoView photoView = (PhotoView) layout.findViewById(R.id.photo_view);
            final ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
            container.addView(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            String thumbPath = getPath(position,true);
            DrawableRequestBuilder thumb = Glide.with(container.getContext()).load(thumbPath);

            Glide.with(container.getContext())
                    .load(getPath(position, false))
                    .thumbnail(thumb)
                    .fitCenter()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.INVISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.INVISIBLE);
                            return false;
                        }
                    }).into(photoView);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageList == null ? 0 : imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }
    }

}
