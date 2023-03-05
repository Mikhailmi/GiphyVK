package com.vk.giphyvk.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vk.giphyvk.gifPackage.ResponseData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query

object RetrofitServices {

    private const val BASE_URL = "https://api.giphy.com"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder()
           .addInterceptor(HttpLoggingInterceptor().also{ it.level = HttpLoggingInterceptor.Level.BODY })
            .build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val searchGifsApi: SearchGifsApi = retrofit.create(
        SearchGifsApi::class.java
    )

}

interface SearchGifsApi {
       @GET("/v1/gifs/search?api_key=$YOUR_API_KEY")
     suspend fun getResponseData(@Query("q")request:String, @Query("limit")limit:Int, @Query("offset")offset:Int
       ): ResponseData

     companion object {
         const val YOUR_API_KEY = "op8vEA9OCrBkStShfek8vg6rq7zR1wLl"
     }
}