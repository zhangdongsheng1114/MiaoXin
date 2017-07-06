package com.example.tarena.miaoxin.ui.ui.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by tarena on 2017/7/5.
 */

public class MyUser extends BmobUser {

    // 性别，位置，拼音名称和拼音首字母
    Boolean gender; // true 男生 false 女生
    String avatar;
    String nick;

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
