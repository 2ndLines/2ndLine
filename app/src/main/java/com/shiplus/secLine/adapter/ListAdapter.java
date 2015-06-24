package com.shiplus.secLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.shiplus.secLine.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/6/16.
 */
public abstract class ListAdapter<DATA> extends RecyclerView.Adapter<VHBase> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private OnItemClickListener listener;

    private ArrayList<DATA> mData;

    public ListAdapter(DATA[] datas){
        this(Arrays.asList(datas));
    }

    public ListAdapter(List<DATA> data){
        mData = data == null? (new ArrayList<DATA>()):(new ArrayList<>(data));
    }

    @Override
    public VHBase onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            return getVHHeader(parent);
        }
        return getVHItem(parent);
    }

    @Override
    public void onBindViewHolder(VHBase holder, final int position) {
        if(hasHeader() && isHeaderPosition(position)){
            bindHeader();
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) listener.onItemClick(v,getItem(position),position);
                }
            });
            bindItem(holder,getItem(position));
        }
    }

    protected void bindHeader(){}

    protected void bindItem(VHBase holder, DATA data){
        holder.bindItem(data);
    }

    private DATA getItem(int position){
        return mData.get(position - 1);
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if(hasHeader()){
            size += 1;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if(isHeaderPosition(position)){
            return TYPE_HEADER;
        }else {
            return TYPE_ITEM;
        }
    }

    protected boolean isHeaderPosition(int position){
        return position == 0;
    }

    protected boolean hasHeader(){
        return false;
    }

    protected VHBase getVHHeader(ViewGroup parent){
        return null;
    };
    protected abstract VHBase getVHItem(ViewGroup parent);

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void swapData(List<DATA> list){
        clear();
        addAll(list);
    }

    public void add(DATA data){
        insert(data,getItemCount());
    }

    public void remove(int position){
        int index = hasHeader()? position+1:position;
        delete(index);
    }

    public void addAll(DATA[] datas){
        if(datas == null) return;
        addAll(Arrays.asList(datas));
    }

    public void addAll(List<DATA> list){
        if(list == null) return;
        int start = getItemCount();
        mData.addAll(list);
        notifyItemRangeInserted(start,getItemCount());

    }

    protected void insert(DATA data, int position){
        mData.add(position,data);
        notifyItemInserted(position);
    }

    protected void delete(int position){
        if(mData.remove(position) != null){
            notifyItemRemoved(position);
        }
    }

    public void clear(){
        int size = mData.size();
        mData.clear();
        int start = hasHeader()? 1:0;
        notifyItemRangeRemoved(start,size);
    }

}

