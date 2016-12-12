package com.zhjydy_doc.model.entity;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class District {

    String id = "";
    String name = "";
    String parentid = "";




    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
