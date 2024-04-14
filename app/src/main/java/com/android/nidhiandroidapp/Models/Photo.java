package com.android.nidhiandroidapp.Models;

import java.io.Serializable;

public class Photo implements Serializable {
    public int id;
    public String photographer;
    public Src src;

    public int getId() {
        return id;
    }

    public String getPhotographer() {
        return photographer;
    }

    public Src getSrc() {
        return src;
    }

}
