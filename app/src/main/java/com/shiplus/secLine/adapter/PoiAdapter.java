package com.shiplus.secLine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.shiplus.model.picselector.Logger;
import com.shiplus.secLine.R;

import java.util.List;

/**
 * Created by Administrator on 2015/6/16.
 */
public class PoiAdapter extends ListAdapter<PoiItem> {

    private static final String TAG = "PoiAdapter";

    public PoiAdapter(PoiItem[] secLocs) {
        super(secLocs);
    }

    public void swapData(List<PoiItem> data){
        clear();
        addAll(data);
    }

    @Override
    protected PoiViewHolder getVHItem(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
        view.setBackgroundResource(R.drawable.abc_list_selector_holo_light);
        return new PoiViewHolder(view);
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    protected VHBase getVHHeader(ViewGroup parent) {
        View header = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_search,parent,false);
        return new SearchViewHolder(header);
    }

    class PoiViewHolder extends VHBase<PoiItem>{
        private TextView name;
        private TextView address;

        public PoiViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(android.R.id.text1);
            address = (TextView) itemView.findViewById(android.R.id.text2);
        }

        @Override
        public void bindItem(PoiItem data) {
            name.setText(data.getTitle());
            address.setText(data.getSnippet());
        }
    }

    private class SearchViewHolder extends VHBase{
        private View searchView;
        public SearchViewHolder(View itemView) {
            super(itemView);
            searchView = itemView.findViewById(R.id.search_view);
        }

        @Override
        public void bindItem(Object data) {
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d(TAG,"SearchView onClick() ");
                }
            });
        }
    }
}
