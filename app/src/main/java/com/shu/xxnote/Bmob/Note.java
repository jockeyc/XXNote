package com.shu.xxnote.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Note extends BmobObject {
    private String type,comment,title,notebook;
    private BmobFile bmobfile;
    public  void setTitle(String title){
        this.title=title;
    }
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

}
