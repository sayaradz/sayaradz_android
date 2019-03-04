package com.elbadri.apps.sayaradz.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Panacea-Soft on 6/30/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


public class WallpaperCategory {

    @SerializedName("name")
    public String name;

    @SerializedName("image")
    public String imageName;

    @SerializedName("count")
    public String count;

    public WallpaperCategory(String name, String imageName, String count) {
        this.name = name;
        this.imageName = imageName;
        this.count = count;
    }
}

