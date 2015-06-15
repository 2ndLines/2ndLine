package com.shiplus.model.picselector.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class ImageFolder implements Parcelable{
    /**
     * Folder name
     */
    private String name;
    /**
     * Folder dir path;
     */
    private String dir;
    /**
     * Images in the folder
     */
    private ArrayList<ImageItem> images;

    public ImageFolder(){
        images = new ArrayList<>();
    }

    /**
     * @param imagePath  Absolute path of a image.
     */
    public ImageFolder(String imagePath){
        this();
        setDir(dir);

    }

    public void setDir(String dir){
        if(dir != null && dir.trim().length() !=0 ){
            this.dir = new File(dir).getParent();
            int lastIndexOf = dir.lastIndexOf("/");
            if(lastIndexOf != -1){
                this.name = dir.substring(lastIndexOf + 1);
            }
        }
    }

    public String getDir(){
        return dir;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name == null? "":name;
    }

    public void addImage(ImageItem item){
        if(!images.contains(item)){
            images.add(item);
        }
    }

    public List<ImageItem> getImages(){
        return images;
    }

    /**
     * @return Return the first image thumbnail in image array.
     */
    public String getCoverImagePath() {
        return images.get(0).getThumbnail();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<ImageFolder> CREATOR = new Creator<ImageFolder>() {
        @Override
        public ImageFolder createFromParcel(Parcel source) {
            return new ImageFolder(source);
        }

        @Override
        public ImageFolder[] newArray(int size) {
            return new ImageFolder[size];
        }
    };

    public ImageFolder(Parcel in){
        in.readString();
        in.readString();
    }
}
