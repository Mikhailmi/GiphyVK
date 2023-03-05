package com.vk.giphyvk.ui.main

import androidx.lifecycle.ViewModel
import com.vk.giphyvk.gifPackage.GifPagedRepository
import com.vk.giphyvk.gifPackage.ResponseData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    suspend fun onButtonClicked(request: String, limit: Int, offset: Int): ResponseData {
        _isLoading.value = true
        val response: ResponseData = GifPagedRepository().getListGif(request, limit, offset)
        while (response.data.isEmpty()) {
            delay(50)
        }
        _isLoading.value = false
        return response
    }
}