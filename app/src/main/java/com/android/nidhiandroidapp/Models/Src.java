package com.android.nidhiandroidapp.Models;

import java.io.Serializable;

public class Src implements Serializable {
    public String original;
    public String large2x;
    public String large;
    public String medium;
    public String small;
    public String portrait;
    public String landscape;
    public String tiny;

    public String getOriginal() {
        return original;
    }

    public String getLarge2x() {
        return large2x;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getSmall() {
        return small;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getLandscape() {
        return landscape;
    }

    public String getTiny() {
        return tiny;
    }
}
