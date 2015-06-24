package com.shiplus.model.picselector.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiplus.model.R;
import com.shiplus.model.picselector.domain.ImageItem;
import com.shiplus.model.picselector.view.CheckableFrameLayout;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private static final String TAG = "PictureAdapter";
    private List<ImageItem> images;
    private OnItemClickListener onItemClickListener;
    private List<ImageItem> selectedList;

    public PictureAdapter(List<ImageItem> list){
        this(list, null);
    }

    public PictureAdapter(List<ImageItem> list, List<ImageItem> selects){
        images = list;
        initSelectsCache(selects);
    }

    private void initSelectsCache(List<ImageItem> selects){
        selectedList = selects;
        if(selectedList == null) selectedList= Collections.emptyList();
    }

    public void updateSelected(List<ImageItem> selects){
        List<ImageItem> oldList = selectedList;
        selectedList  = selects;
        for(ImageItem item : oldList){
            int position = images.indexOf(item);
            notifyItemChanged(position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public void swapData(List<ImageItem> newList){
        images = newList;
        notifyDataSetChanged();
    }

    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_picture_item, parent, false);

        return new PictureAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PictureAdapter.ViewHolder holder, final int position) {
        ImageItem item = images.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckableFrameLayout frameLayout = (CheckableFrameLayout) v;
                frameLayout.toggle();
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(v,getItem(position), position);
                }
            }
        });

        holder.setChecked(selectedList.contains(item));
        holder.bindItem(item);
    }

    public ImageItem getItem(int position){
        return images == null ? null : images.get(position);
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private CheckableFrameLayout checkableView;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            checkableView = (CheckableFrameLayout) itemView.findViewById(R.id.checkable_framelayout);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
        }

        public void bindItem(ImageItem item){
            checkBox.setVisibility(View.GONE);

            Glide.with(imageView.getContext())
                    .load(item.getThumbnail())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            checkBox.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(imageView);

        }

        public void setChecked(boolean checked){
            checkableView.setChecked(checked);
        }
    }
}
