package com.shiplus.secLine.domain;

/**
 * Created by Administrator on 2015/6/19.
 */
public class SlideShowData {

    /**
     * Feed primary image url
     */
    private String imageUrl;
    /**
     * Feed description summary
     */
    private String summary;
    /**
     * Feed sound url
     */
    private String soundUrl;
    /**
     * Feed object id according to leancloud .
     */
    private String objectId;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public String getObjectId() {
        return objectId;
    }
}
