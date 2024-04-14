package com.android.nidhiandroidapp.Models;

import java.util.ArrayList;

public class SearchApiResponse {
    public ArrayList<Photo> photos;
    public String next_page;
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
}
