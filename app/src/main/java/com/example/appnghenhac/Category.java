package com.example.appnghenhac;

import androidx.annotation.DrawableRes;

public class Category {
    private int id;
    private String name;
    @DrawableRes
    private int img;
    public Category(int id, String name, @DrawableRes int img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
