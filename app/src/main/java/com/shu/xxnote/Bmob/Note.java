package com.shu.xxnote.Bmob;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Note extends BmobObject {

   /* public Note (String type,String title,String notebook,String date,BmobFile bmobfile){
        this.type=type;
        this.title = title;
        this.notebook = notebook;
        this.date = date;
        this.bmobfile = bmobfile;
    }*/
    private String type;
    private String comment;
    private String title;
    private String notebook;
    private String date;
    private BmobFile bmobfile;

    public  String getTitle(String title){
        return  title;
    }
    public String getNotebook() {
        return notebook;
    }

    public void setNotebookId(String notebook) {
        this.notebook=notebook;
    }
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
}
