package com.sayaradz.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.util.ArrayList


object OptionRepository {

    val modelsList: ArrayList<Option>?
        get() = Gson().fromJson<ArrayList<Option>>(
            ModelItems,
            object : TypeToken<ArrayList<Option>>() {

            }.type
        )

    internal var ModelItems = "[\n" +
    "  {\n" +
    "    \"value\":\"car1\",\n" +
    "    \"option\":\"car1\",\n" +
    "    \"likeCount\":\"car1\",\n" +
    "    \"viewCount\":\"4.2K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Oliver\",\n" +
    "      \"ago\":\"2 hours ago\",\n" +
    "      \"user_option\":\"user_profile_man\"\n" +
    "    }\n" +
    "  },\n" +
    "  {\n" +
    "    \"value\":\"car2\",\n" +
    "    \"option\":\"car2\",\n" +
    "    \"likeCount\":\"car2\",\n" +
    "    \"viewCount\":\"3.2K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Harry\",\n" +
    "      \"ago\":\"3 hours ago\",\n" +
    "      \"user_option\":\"profile1\"\n" +
    "    }\n" +
    "  },\n" +
    "  {\n" +
    "    \"value\":\"car3\",\n" +
    "    \"option\":\"car3\",\n" +
    "    \"likeCount\":\"car3\",\n" +
    "    \"viewCount\":\"3.8K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Jacob\",\n" +
    "      \"ago\":\"3 hours ago\",\n" +
    "      \"user_option\":\"profile2\"\n" +
    "    }\n" +
    "  },\n" +
    "  {\n" +
    "    \"value\":\"car4\",\n" +
    "    \"option\":\"car4\",\n" +
    "    \"likeCount\":\"car4\",\n" +
    "    \"viewCount\":\"3.6K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Oliver\",\n" +
    "      \"ago\":\"5 hours ago\",\n" +
    "      \"user_option\":\"user_profile_man\"\n" +
    "    }\n" +
    "  },\n" +
    "  {\n" +
    "    \"value\":\"car5\",\n" +
    "    \"option\":\"car5\",\n" +
    "    \"likeCount\":\"car5\",\n" +
    "    \"viewCount\":\"8.2K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Harry\",\n" +
    "      \"ago\":\"5 hours ago\",\n" +
    "      \"user_option\":\"profile1\"\n" +
    "    }\n" +
    "  },\n" +
    "  {\n" +
    "    \"value\":\"car6\",\n" +
    "    \"option\":\"car6\",\n" +
    "    \"likeCount\":\"car6\",\n" +
    "    \"viewCount\":\"6.2K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Jacob\",\n" +
    "      \"ago\":\"6 hours ago\",\n" +
    "      \"user_option\":\"profile2\"\n" +
    "    }\n" +
    "  },\n" +
    "  {\n" +
    "    \"value\":\"car7\",\n" +
    "    \"option\":\"car7\",\n" +
    "    \"likeCount\":\"car7\",\n" +
    "    \"viewCount\":\"13.2K\",\n" +
    "    \"user\" : {\n" +
    "      \"user_value\":\"Oliver\",\n" +
    "      \"ago\":\"6 hours ago\",\n" +
    "      \"user_option\":\"user_profile_man\"\n" +
    "    }\n" +
    "  }\n" +
    "\n" +
    "]"
}
