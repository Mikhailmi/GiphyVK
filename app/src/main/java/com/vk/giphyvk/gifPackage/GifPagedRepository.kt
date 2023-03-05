package com.vk.giphyvk.gifPackage

import com.vk.giphyvk.api.RetrofitServices
import kotlinx.coroutines.*

class GifPagedRepository {

    private var scope = CoroutineScope(Job() + Dispatchers.IO)
    private var job: Job? = null
    private lateinit var response: ResponseData

    suspend fun getListGif(request: String, limit: Int, offset: Int): ResponseData {
        var listGif: List<Gif> = listOf()
            job?.cancel()
            job = scope.launch {
                response = RetrofitServices.searchGifsApi.getResponseData(request, limit, offset)
                if (response.data.isNotEmpty()) {
                    listGif = response.data
                }
            }
        while (listGif.isEmpty())
            delay(50)
        return response
    }
}