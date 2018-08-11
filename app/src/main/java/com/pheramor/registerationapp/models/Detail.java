package com.pheramor.registerationapp.models;

public class Detail {
    private String item;
    private String title;

    public Detail(String title, String item) {
        this.title = title;
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
