package com.shiplus.model.picselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiplus.model.R;
import com.shiplus.model.picselector.domain.ImageFolder;
import com.shiplus.model.picselector.Logger;

import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private static final String TAG = "FolderAdapter";
    private List<ImageFolder> imageFolders;
    private OnItemClickListener onItemClickListener;


    public FolderAdapter(List<ImageFolder> list){
        imageFolders = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public void swapData(List<ImageFolder> data){
        if(imageFolders == data) return;
        Logger.d(TAG," ImageFolder size = " + (data == null? 0: data.size()));
        List old = imageFolders;
        imageFolders = data;

        if(old != null){
            old.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_folder_item, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return imageFolders == null ? 0 : imageFolders.size();
    }

    @Override
    public  void onBindViewHolder(FolderViewHolder holder, final int position) {
        final ImageFolder folder = imageFolders.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(v, folder, position);
                }
            }
        });
        holder.bindItem(folder);
    }

    class FolderViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private Context context;

        public FolderViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.folder_image_view);
            textView = (TextView) itemView.findViewById(R.id.folder_name);
            context = itemView.getContext();
        }

        public void bindItem(ImageFolder folder){

            String imagePath = folder.getCoverImagePath();
            Glide.with(context).load(imagePath).into(imageView);

            int size = folder.getImages().size();
            textView.setText(context.getResources().getString(R.string.text_folder_name, folder.getName(), size));
        }
    }

}
