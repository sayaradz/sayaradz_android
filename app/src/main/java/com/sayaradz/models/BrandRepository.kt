package com.sayaradz.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object BrandRepository {

    val brandList: ArrayList<Brand>?
        get() =
            Gson().fromJson<ArrayList<Brand>>(
                wallpaperCategories,
                object : TypeToken<ArrayList<Brand>>() {

                }.type
            )

    internal var wallpaperCategories = ("[" +
            "  {\n" +
            "    \"name\":\"Audi\",\n" +
            "    \"logo\":\"audi\",\n" +
            "    \"count\":\"300\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"BMW\",\n" +
            "    \"logo\":\"bmw\",\n" +
            "    \"count\":\"240\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"KIA\",\n" +
            "    \"logo\":\"kia\",\n" +
            "    \"count\":\"250\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Hyundai\",\n" +
            "    \"logo\":\"hyundai\",\n" +
            "    \"count\":\"500\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Ford\",\n" +
            "    \"logo\":\"ford\",\n" +
            "    \"count\":\"305\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Suzuki\",\n" +
            "    \"logo\":\"suzuki\",\n" +
            "    \"count\":\"450\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Chevrolet\",\n" +
            "    \"logo\":\"chevrolet\",\n" +
            "    \"count\":\"450\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"Mercedes\",\n" +
            "    \"logo\":\"mercedes_benz\",\n" +
            "    \"count\":\"450\"\n" +
            "  }," +

            "]")
}
