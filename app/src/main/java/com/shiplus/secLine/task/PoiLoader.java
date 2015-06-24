package com.shiplus.secLine.task;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.amap.api.services.core.PoiItem;
import com.shiplus.secLine.utils.PoiSearchHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/16.
 */
public class PoiLoader extends AsyncTaskLoader<List<PoiItem>> {
    private List<PoiItem> cached;
    private PoiSearchHelper poiHelper;

    public PoiLoader(Context context, String queryStr, String city, double latitude, double longitude) {
        super(context);
        poiHelper = new PoiSearchHelper(context,queryStr,city, latitude,longitude);
    }

    @Override
    public List<PoiItem> loadInBackground() {
        List<PoiItem> items = poiHelper.query();
        if(cached != null && items != null){
            ArrayList<PoiItem> tmp = new ArrayList<>(cached);
            tmp.addAll(items);
            return tmp;
        }

        return items;
    }

    @Override
    public void deliverResult(List<PoiItem> data) {
        if(isReset()) {
            cached = null;
            return;
        }
        cached = data;
        if(isStarted()){
            super.deliverResult(data);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        cached = null;
        onStopLoading();
    }

    @Override
    protected void onStartLoading() {
        if(cached != null){
            deliverResult(cached);
        }

        if(takeContentChanged() || cached == null){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(List<PoiItem> data) {
        cancelLoad();
    }
}
