 package com.shiplus.secLine.activity;

 import android.location.Location;
 import android.os.Bundle;
 import android.os.Handler;
 import android.support.annotation.Nullable;
 import android.support.v4.app.LoaderManager;
 import android.support.v4.content.Loader;
 import android.support.v4.widget.SwipeRefreshLayout;
 import android.support.v7.widget.Toolbar;
 import android.view.MenuItem;
 import android.view.View;

 import com.amap.api.location.AMapLocation;
 import com.amap.api.location.AMapLocationListener;
 import com.amap.api.location.LocationManagerProxy;
 import com.amap.api.location.LocationProviderProxy;
 import com.amap.api.services.core.PoiItem;
 import com.malinskiy.superrecyclerview.OnMoreListener;
 import com.malinskiy.superrecyclerview.SuperRecyclerView;
 import com.shiplus.model.picselector.Logger;
 import com.shiplus.secLine.R;
 import com.shiplus.secLine.adapter.ListAdapter;
 import com.shiplus.secLine.adapter.PoiAdapter;
 import com.shiplus.secLine.task.PoiLoader;

 import java.util.Collections;
 import java.util.List;

/**
 * Created by Administrator on 2015/6/16.
 */
public class LocationActivity extends ListActivity<PoiItem> implements LoaderManager.LoaderCallbacks<List<PoiItem>>, AMapLocationListener,Runnable{
    private static final String EXTRA_CITY = "extra_city";
    private static final String EXTRA_LATITUDE = "extra_latitude";
    private static final String EXTRA_LONGITUDE = "extra_longitude";
    private static final String TAG = "LocationActivity";
    private PoiAdapter adapter;
    private LocationManagerProxy locationManagerProxy;
    private Handler mHandler = new Handler();
    private Loader<List<PoiItem>> loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRefreshListener();
        setMoreListener(1);
    }

    private void updateLocation(){
        if(locationManagerProxy == null){
            locationManagerProxy = LocationManagerProxy.getInstance(this);
        }
        locationManagerProxy.setGpsEnable(true);
        locationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 2000, 10, this);
        //Cancel location ,if out of 10s.
        mHandler.postDelayed(this, 10000);
    }

    private void stopLocation(){
        if(locationManagerProxy != null){
            locationManagerProxy.removeUpdates(this);
            locationManagerProxy.destroy();
        }
        if(mHandler != null){
            mHandler.removeCallbacks(this);
        }
        locationManagerProxy = null;
    }

    @Override
    protected ListAdapter<PoiItem> getAdapter() {
        if(adapter == null){
            adapter = new PoiAdapter(new PoiItem[]{});
        }
        return adapter;
    }

    @Override
    public void onRefresh() {
        updateLocation();
    }

    @Override
    public void onMoreAsked(int i, int i1, int i2) {
        loader.forceLoad();
    }

    @Override
    public Loader<List<PoiItem>> onCreateLoader(int id, Bundle args) {
        String city = args.getString(EXTRA_CITY);
        double latitude = args.getDouble(EXTRA_LATITUDE);
        double longitude = args.getDouble(EXTRA_LONGITUDE);
        return new PoiLoader(LocationActivity.this,"", city, latitude,longitude);
    }

    @Override
    public void onLoadFinished(Loader<List<PoiItem>> loader, List<PoiItem> data) {
        Logger.d(TAG, "onLoadFinished()  data = " + data);
        hideMoreProgress();
        getAdapter().swapData(data);

    }

    @Override
    public void onLoaderReset(Loader<List<PoiItem>> loader) {
        getAdapter().swapData(null);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //Fail to location
        if(aMapLocation == null || aMapLocation.getAMapException().getErrorCode() != 0) return;

        //Stop location.
        stopLocation();

        //Collects location info.
        String city = aMapLocation.getCity();
        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CITY, city);
        bundle.putDouble(EXTRA_LATITUDE, latitude);
        bundle.putDouble(EXTRA_LONGITUDE, longitude);
        Logger.d(TAG,"Location info : " + bundle.toString());
        loader = getSupportLoaderManager().initLoader(0, bundle, this);

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(this);
        stopLocation();
    }

    @Override
    public void run() {
        stopLocation();
    }

    @Override
    public void onItemClick(View view, PoiItem item, int position) {
        Logger.d(TAG,"OnItemClick, position = " + position);
    }
}
