package com.shiplus.secLine.domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 2015/5/19.
 */
@AVClassName("AVProduct")
public class AVProduct extends AVObject {
    /*
    * Fields:
    * creator,
    * type,
    * content,
    * province,
    * city,
    * district,
    * view count,
    * follow,
    * topic
    * */

    public void setCreator(AVUser2 user){
        put(AVKey.KEY_CREATOR,user);
    }

    public AVUser2 getCreator(){
       return getAVUser(AVKey.KEY_CREATOR,AVUser2.class);
    }

    public void setPdtType(AVPdtType type){
        put(AVKey.KEY_TYPE,type);
    }

    public AVPdtType getPdtType() throws Exception {
        return getAVObject(AVKey.KEY_TYPE,AVPdtType.class);
    }

    public SecContent getContent() {
        return (SecContent) get(AVKey.KEY_CONTENT);
    }

    public void setContent(SecContent content) {
        put(AVKey.KEY_CONTENT,content);
    }

    public void increaseViewCount(){
        increment(AVKey.KEY_COUNT);
    }

    public int getViewCount(){
        return getInt(AVKey.KEY_COUNT);
    }

    public String getProvince() {
        return getString(AVKey.KEY_PROVINCE);
    }

    public void setProvince(String province) {
        put(AVKey.KEY_PROVINCE,province);
    }

    public String getCity() {
        return getString(AVKey.KEY_CITY);
    }

    public void setCity(String city) {
        put(AVKey.KEY_CITY,city);
    }

    public String getDistrict() {
        return getString(AVKey.KEY_DISTRICT);
    }

    public void setDistrict(String district) {
        put(AVKey.KEY_DISTRICT,district);
    }
}
