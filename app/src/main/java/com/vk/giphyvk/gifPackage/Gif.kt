package com.vk.giphyvk.gifPackage

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gif(
   val id:String,
   val url:String,
   val embed_url:String,
   val images:Any,
   val title:String
)

@JsonClass(generateAdapter = true)
data class Pagination(
    val offset:Int,
    val total_count:Int,
    val count:Int
)

@JsonClass(generateAdapter = true)
data class ResponseData(
    val data:List<Gif>,
    val pagination:Pagination
)

data class GifImage(
    val photo:String
)
