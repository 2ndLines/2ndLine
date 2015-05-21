package com.shiplus.secLine.domain;

import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2015/5/19.
 */
public class AVUser2 extends AVUser {
    /*private String avatar;
    private String nickname;
    private SecLoc location;*/

    public String getAvatar() {
        return getString(AVKey.KEY_AVATAR);
    }

    public void setAvatar(String avatar) {
        put(AVKey.KEY_AVATAR,avatar);
    }

    public void setNickname(String nickname){
        put(AVKey.KEY_NICKNAME,nickname);
    }

    public String getNickname(){
        return getString(AVKey.KEY_NICKNAME);
    }

    public SecLoc getLocation() {
        return (SecLoc) get(AVKey.KEY_LOC);
    }

    public void setLocation(SecLoc location) {
        put(AVKey.KEY_LOC,location);
    }
}
