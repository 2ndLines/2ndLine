package com.shiplus.secLine.utils;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.shiplus.model.picselector.Logger;

import java.util.List;

/**
 * Created by Administrator on 2015/6/17.
 */
public class PoiSearchHelper {
    private static final String TAG = "PoiSearchHelper";
    private static final double INVALID_LOCATION = 0.0;

    private int curPageIndex = 0;
    private PoiSearch search;


    public PoiSearchHelper(Context context, String queryString, String city, double latitude, double longitude){
        PoiSearch.Query query = new PoiSearch.Query(queryString,"", city);
        search = new PoiSearch(context, query);
        if(latitude != INVALID_LOCATION && longitude != INVALID_LOCATION){
            setBoundCenter(latitude,longitude);
        }
    }

    public void setQueryString(String queryString){
        PoiSearch.Query query = search.getQuery();
        if(!queryString.equals(query.getQueryString())){
            PoiSearch.Query query1 = new PoiSearch.Query(queryString,"", query.getCity());
            search.setQuery(query1);
        }
    }

    public void setCity(String city){
        PoiSearch.Query query = search.getQuery();
        if(!query.getCity().equals(city)){
            PoiSearch.Query query1 = new PoiSearch.Query(query.getQueryString(),"",city);
            search.setQuery(query1);
        }
    }

    public void setQueryStrAndCity(String queryStr, String city){
        PoiSearch.Query query = search.getQuery();
        if(!query.getCity().equals(city) || !query.getQueryString().equals(queryStr)){
            PoiSearch.Query query1 = new PoiSearch.Query(queryStr,"", query.getCity());
            search.setQuery(query1);
        }
    }

    public void setBoundCenter(double latitude, double longitude){
        PoiSearch.SearchBound bound = new PoiSearch.SearchBound(new LatLonPoint(latitude,longitude),2000);
        search.setBound(bound);
    }

    public List<PoiItem> query(){
        return query(null,0);
    }

    public List<PoiItem> query(LatLonPoint point, int radius){
        if(point != null){
            search.setBound(new PoiSearch.SearchBound(point,radius));
        }

        Logger.d(TAG,"Search current page index = " + curPageIndex);
        search.getQuery().setPageNum(curPageIndex++);
        try {
            PoiResult result = search.searchPOI();
            if(result != null){
                return result.getPois();
            }
        } catch (AMapException e) {
            e.printStackTrace();
            Logger.e(TAG," AMapException, message = " + e.getErrorMessage());
        }

        return null;
    }

}
