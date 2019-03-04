package com.elbadri.apps.sayaradz.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Panacea-Soft on 6/30/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


public class WallpaperItem {

    @SerializedName("name")
    public String name;

    @SerializedName("image")
    public String imageName;

    @SerializedName("likeCount")
    public String likeCount;

    @SerializedName("viewCount")
    public String viewCount;

    @SerializedName("user")
    public User user;

    public WallpaperItem(String name, String imageName, String likeCount, String viewCount, User user) {
        this.name = name;
        this.imageName = imageName;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.user = user;
    }

    public class User {
        @SerializedName("user_name")
        public String nameName;

        @SerializedName("ago")
        public String ago;

        @SerializedName("user_image")
        public String user_image;
    }
}
