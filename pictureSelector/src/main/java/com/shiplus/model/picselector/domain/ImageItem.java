package com.shiplus.model.picselector.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/6/8.
 */
public class ImageItem implements Parcelable {
    private static final String DISK_FILE_PREFIX = "file://" ;

    /**
     * The longitude when the picture captured.
     */
    private double longitude;
    /**
     * The latitude when the picture captured.
     */
    private double latitude;

    /**
     * Image's thumbnail absolute path.
     */
    private String thumbnail;

    /**
     * Image original absolute path.
     */
    private String original;


    public ImageItem(String thumbnail, String original){
        this(thumbnail,original,0,0);
    }

    public ImageItem(String thumbnail, String original, long longitude, long latitude){
        this.thumbnail = thumbnail;
        this.original = original;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getThumbnail(){
        return DISK_FILE_PREFIX + thumbnail;
    }

    public String getOriginal(){
        return DISK_FILE_PREFIX + original;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    @Override
    public String toString() {
        return "ImageItem{"
                +"thumbnail=" + thumbnail
                +",original=" + original
                +",longitude=" + longitude
                +",latitude=" + latitude
                +"}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeString(original);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    public ImageItem(Parcel in){
        thumbnail = in.readString();
        original = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if( !(o instanceof ImageItem)){
            return false;
        }

        ImageItem item = (ImageItem) o;

        if(!(item.original.equals(this.original))){
            return false;
        }

        if(!(item.thumbnail.equals(this.thumbnail))){
            return false;
        }

        if(item.longitude != this.longitude) {
            return false;
        }

        if(item.latitude != this.latitude) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = thumbnail.hashCode();
        result = 22*result + original.hashCode();
        result = 22*result + (int)longitude;
        result = 22*result + (int)latitude;

        return result;
    }
}
