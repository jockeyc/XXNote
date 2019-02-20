package com.shu.xxnote;

import cn.bmob.v3.BmobObject;

public class Users extends BmobObject {
    private String name,password,phonemail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonemail() {
        return phonemail;
    }

    public void setPhonemail(String phone_mail) {
        this.phonemail = phone_mail;
    }
}
