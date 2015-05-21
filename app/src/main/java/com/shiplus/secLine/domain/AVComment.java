package com.shiplus.secLine.domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 2015/5/19.
 */
@AVClassName("AVComment")
public class AVComment extends AVObject {
    /*
    * product
    * commenter
    * content
    * */

    public void setProduct(AVProduct product){
        put(AVKey.KEY_PRODUCT, product);
    }

    public AVProduct getProduct() throws Exception {
        return getAVObject(AVKey.KEY_PRODUCT,AVProduct.class);
    }

    public void setCommenter(AVUser2 commenter){
        put(AVKey.KEY_COMMENTER,commenter);
    }

    public AVUser2 getCommenter(){
        return getAVUser(AVKey.KEY_COMMENTER,AVUser2.class);
    }

    public void setContent(SecContent content){
        put(AVKey.KEY_CONTENT,content);
    }

    public SecContent getContent(){
        return (SecContent) get(AVKey.KEY_CONTENT);
    }
}
