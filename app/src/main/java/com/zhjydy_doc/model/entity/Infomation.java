package com.zhjydy_doc.model.entity;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class Infomation {

    private String id;
    private String title;
    private String data;
    private String outline;
    private String url;

    public Infomation(String title, String data, String outline, String url) {
        this.title = title;
        this.data = data;
        this.outline = outline;
        this.url = url;
    }

    public Infomation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
