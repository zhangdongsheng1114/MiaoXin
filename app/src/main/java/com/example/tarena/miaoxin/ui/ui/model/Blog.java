package com.example.tarena.miaoxin.ui.ui.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by tarena on 2017/7/6.
 */

public class Blog extends BmobObject {

    MyUser author;  // blog的作者
    String content;   // 文本内容
    String imgUrls;   // Blog的所有配图地址
    Integer love;    // 点赞数量

    public Blog() {
        super();
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }

}
