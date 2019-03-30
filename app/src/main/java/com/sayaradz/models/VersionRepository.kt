package com.sayaradz.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


object VersionRepository {

    val modelsList: ArrayList<Version>?
        get() = Gson().fromJson<ArrayList<Version>>(
            ModelItems,
            object : TypeToken<ArrayList<Version>>() {

            }.type
        )

    internal var ModelItems = "[\n" +
            "  {\n" +
            "    \"name\":\"car1\",\n" +
            "    \"image\":\"car1\",\n" +
            "    \"likeCount\":\"car1\",\n" +
            "    \"viewCount\":\"4.2K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Oliver\",\n" +
            "      \"ago\":\"2 hours ago\",\n" +
            "      \"user_image\":\"user_profile_man\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"car2\",\n" +
            "    \"image\":\"car2\",\n" +
            "    \"likeCount\":\"car2\",\n" +
            "    \"viewCount\":\"3.2K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Harry\",\n" +
            "      \"ago\":\"3 hours ago\",\n" +
            "      \"user_image\":\"profile1\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"car3\",\n" +
            "    \"image\":\"car3\",\n" +
            "    \"likeCount\":\"car3\",\n" +
            "    \"viewCount\":\"3.8K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Jacob\",\n" +
            "      \"ago\":\"3 hours ago\",\n" +
            "      \"user_image\":\"profile2\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"car4\",\n" +
            "    \"image\":\"car4\",\n" +
            "    \"likeCount\":\"car4\",\n" +
            "    \"viewCount\":\"3.6K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Oliver\",\n" +
            "      \"ago\":\"5 hours ago\",\n" +
            "      \"user_image\":\"user_profile_man\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"car5\",\n" +
            "    \"image\":\"car5\",\n" +
            "    \"likeCount\":\"car5\",\n" +
            "    \"viewCount\":\"8.2K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Harry\",\n" +
            "      \"ago\":\"5 hours ago\",\n" +
            "      \"user_image\":\"profile1\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"car6\",\n" +
            "    \"image\":\"car6\",\n" +
            "    \"likeCount\":\"car6\",\n" +
            "    \"viewCount\":\"6.2K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Jacob\",\n" +
            "      \"ago\":\"6 hours ago\",\n" +
            "      \"user_image\":\"profile2\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\":\"car7\",\n" +
            "    \"image\":\"car7\",\n" +
            "    \"likeCount\":\"car7\",\n" +
            "    \"viewCount\":\"13.2K\",\n" +
            "    \"user\" : {\n" +
            "      \"user_name\":\"Oliver\",\n" +
            "      \"ago\":\"6 hours ago\",\n" +
            "      \"user_image\":\"user_profile_man\"\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "]"
}
