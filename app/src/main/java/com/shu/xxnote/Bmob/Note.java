package com.shu.xxnote.Bmob;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

public class Note extends BmobObject {
    private String type,comment,title,notebook;
    private BmobFile bmobfile;
    private String date;
    private Notebook Book;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BmobFile getBmobfile() {
        return bmobfile;
    }

    public void setBmobfile(BmobFile bmobfile) {
        this.bmobfile = bmobfile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setnotebook(String notebook){
        this.notebook=notebook;
    }

    public String getnotebook() {
        return notebook;
    }

    public void setBook(Notebook Notebook) {
        this.Book = Notebook;
    }

    public Notebook getBook() {
        return Book;
    }
}
