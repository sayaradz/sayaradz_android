package com.elbadri.apps.sayaradz.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Panacea-Soft on 6/30/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


public class WallpaperCategoryRepository {

    public static ArrayList<WallpaperCategory> getWallpaperCategoryList() {
        return new Gson().fromJson(wallpaperCategories, new TypeToken<ArrayList<WallpaperCategory>>() {}.getType());
    }

    static String wallpaperCategories = "[\n" +
            "  {\n" +
            "    \"name\":\"Audi\",\n" +
            "    \"image\":\"audi\",\n" +
            "    \"count\":\"300\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"BMW\",\n" +
            "    \"image\":\"bmw\",\n" +
            "    \"count\":\"240\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"KIA\",\n" +
            "    \"image\":\"kia\",\n" +
            "    \"count\":\"250\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Hyundai\",\n" +
            "    \"image\":\"hyundai\",\n" +
            "    \"count\":\"500\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Ford\",\n" +
            "    \"image\":\"ford\",\n" +
            "    \"count\":\"305\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Suzuki\",\n" +
            "    \"image\":\"suzuki\",\n" +
            "    \"count\":\"450\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Chevrolet\",\n" +
            "    \"image\":\"chevrolet\",\n" +
            "    \"count\":\"450\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Mercedes\",\n" +
            "    \"image\":\"mercedes_benz\",\n" +
            "    \"count\":\"450\"\n" +
            "  },\n" +

            "]";
}
