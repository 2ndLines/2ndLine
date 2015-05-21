package com.shiplus.secLine.domain;

/**
 * Created by Administrator on 2015/5/19.
 */
public class SecContent {
    private String text;
    private String picUrl;
    private String voiceUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }
}
