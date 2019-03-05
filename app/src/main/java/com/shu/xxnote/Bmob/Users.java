package com.shu.xxnote.Bmob;

import cn.bmob.v3.BmobObject;

public class Users extends BmobObject {
    private String name,password,phonemail,username,specialty;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
