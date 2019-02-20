package com.shu.xxnote;

import java.util.Random;

import cn.bmob.v3.BmobObject;

public class Notebook extends BmobObject {
    private String notename;//笔记本名称
    private int cover;//笔记本封面
    private Users users;

    public Notebook() {

    }

    public Notebook(String name, int cover) {
        this.notename = name;
        this.cover = cover;
    }


    public int getCover() { return cover; }

    public void setCover(int cover) { this.cover = cover;}

    //获取随机封面
    public static int getRandomCover() {

        int cover = new Random().nextInt(5);

        return cover;
    }


    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getNotename() {
        return notename;
    }

    public void setNotename(String notename) {
        this.notename = notename;
    }
}
