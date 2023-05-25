package com.projector.callertracker.model;

public class NumLocModel {
    String name,url;
    String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public NumLocModel(String name, String url, String img) {
        this.name = name;
        this.url = url;
        this.img = img;
    }
}
