package com.example.travelbox.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



// 인기 게시물 이미지
class PostSharedViewModel : ViewModel(){
    private val _topImages = MutableLiveData<List<String>>()
    val topImages: LiveData<List<String>> get() = _topImages

    fun setTopImages(images: List<String>) {
        _topImages.value = images
    }



}