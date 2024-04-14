package com.android.nidhiandroidapp.Models;

import java.util.ArrayList;

public class APIResponse {
    public int page;
    public ArrayList<Photo> photos;
    public int getPage() {
        return page;
    }
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

}
