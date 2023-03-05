package com.vk.giphyvk.gifPackage

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gif(
    @SerializedName("id") val id:String,
    @SerializedName("url") val url:String,
    @SerializedName("embed_url") val embed_url:String,
    @SerializedName("images") val images:Any,
    @SerializedName("title") val title:String
)

@JsonClass(generateAdapter = true)
data class Pagination(
    @SerializedName("offset") val offset:Int,
    @SerializedName("total_count") val total_count:Int,
    @SerializedName("count") val count:Int
)

@JsonClass(generateAdapter = true)
data class ResponseData(
    @SerializedName("data") val data:List<Gif>,
    @SerializedName("pagination") val pagination:Pagination
)

data class GifImage(
    val photo:String
)
