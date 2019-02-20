package com.shu.xxnote;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Pointer {
    List<Notebook> list1 = new ArrayList<Notebook>() ;

    //重命名一本笔记本
    public void rename(String id,String name,int cover){
        Notebook book = new Notebook();
        book.setCover(cover);
        book.setNotename(name);
        book.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });
    }
    //删除一本笔记本
    public void delete(String id){
        Notebook book = new Notebook();
        book.setObjectId(id);
        book.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });
    }
    //插入一本笔记本
    public void insert(Notebook book,String userId){
        Users users = new Users();
        users.setObjectId(userId);
        book.setUsers(users);
        book.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){ }else{ }
            }
        });
    }

}
