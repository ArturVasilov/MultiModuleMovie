package ru.arturvasilov.multimodulemovie.api

import com.google.gson.annotations.SerializedName

class Video {

    @SerializedName("key")
    var videoId: String = ""

    @SerializedName("site")
    var site: String = ""

    @SerializedName("name")
    var name: String = ""
}