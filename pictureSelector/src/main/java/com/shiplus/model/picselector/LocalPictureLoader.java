package com.shiplus.model.picselector;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;

import com.shiplus.model.R;
import com.shiplus.model.picselector.domain.ImageFolder;
import com.shiplus.model.picselector.domain.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/6/11.
 */
public class LocalPictureLoader extends AsyncTaskLoader<List<ImageFolder>> {
    private static final String[] THUMB_PROJECTION = {
            MediaStore.Images.Thumbnails.DATA,
            MediaStore.Images.Thumbnails.IMAGE_ID
    };

    private static final String[] ORIG_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE
    };
    private static final String TAG = "PictureLoader";

    private List<ImageFolder> cached;
    private boolean observerRegister = false;
    private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();

    public LocalPictureLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(List<ImageFolder> data) {
        Logger.d(TAG,"deliverResult()");
        if(isReset()){
            cached = null;
            return;
        }
        cached = data;
        if(isStarted()){
            super.deliverResult(data);
        }

    }

    @Override
    protected void onStartLoading() {
        if(cached != null){
            deliverResult(cached);
        }
        if(takeContentChanged() || cached == null){
            forceLoad();
        }
        registerContentObserver();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        cached = null;
        unregisterContentObserver();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        unregisterContentObserver();
    }

    @Override
    public List<ImageFolder> loadInBackground() {
        return queryImages();
    }

    private List<ImageFolder> queryImages(){
        return query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,THUMB_PROJECTION,null,null,null);
    }

    private List<ImageFolder> query(Uri uri,String[] projection, String selection, String[] selectionArgs, String sortOrder){
        final ArrayList<ImageFolder> data = new ArrayList<>();
        Cursor cursor =getContext().getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        if(cursor == null || !cursor.moveToFirst()){
            return data;
        }

        try {
            int dataColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
            int imageIdColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID);

            HashMap<String, Integer> dirCache = new HashMap<>();

            //Create all folder.
            ImageFolder folderNamedAll = new ImageFolder();
            folderNamedAll.setName(getContext().getResources().getString(R.string.text_all_images));
            data.add(folderNamedAll);
            do{
                String thumb = cursor.getString(dataColumnIndex);
                int imageId = cursor.getInt(imageIdColumnIndex);
                ImageItem item = queryImageAndBuildImageItem(thumb, imageId);
                if(item == null) continue;
                //Add every item into all folder.
                folderNamedAll.addImage(item);

                String dir = new File(item.getOriginal()).getParent();
                ImageFolder folder = null;
                if(!dirCache.containsKey(dir)){
                    folder = new ImageFolder();
                    folder.setDir(dir);
                    data.add(folder);
                    //Cache folder index.
                    dirCache.put(dir,data.indexOf(folder));
                }else{
                    folder = data.get(dirCache.get(dir));
                }
                folder.addImage(item);
            }while (cursor.moveToNext());
        } finally {
            cursor.close();
        }

        return data;
    }

    private ImageItem queryImageAndBuildImageItem(String thumb, int imageId){
        final String image_selection = MediaStore.Images.Media._ID +"=" + imageId;
        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ORIG_PROJECTION,image_selection,null,null);
        if(cursor != null && cursor.moveToFirst()){
            try {
                int dataColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                int longitudeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE);
                int latitudeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE);

                String path = cursor.getString(dataColumnIndex);
                long longitude = cursor.getLong(longitudeColumnIndex);
                long latitude = cursor.getLong(latitudeColumnIndex);

                return new ImageItem(thumb,path,longitude,latitude);
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    private void registerContentObserver(){
        if(!observerRegister){
            getContext().getContentResolver().registerContentObserver(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, forceLoadContentObserver);
            observerRegister = true;
        }
    }

    private void unregisterContentObserver(){
        if(observerRegister){
            getContext().getContentResolver().unregisterContentObserver(forceLoadContentObserver);
            observerRegister = false;
        }
    }

}
