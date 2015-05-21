package com.shiplus.secLine.domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 2015/5/19.
 * Store navigable position.
 */
@AVClassName("AVNavPos")
public class AVNavPos extends AVObject {

    /*private String province;
    private String city;
    private String district;*/

    public void setProvince(String province){
        put(AVKey.KEY_PROVINCE,province);
    }

    public String getProvince(){
        return getString(AVKey.KEY_PROVINCE);
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
