package com.example.travelbox.presentation.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelbox.data.repository.search.ThreadPost

class SearchPostViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<ThreadPost>>()
    val posts: LiveData<List<ThreadPost>> get() = _posts

    fun setPosts(posts: List<ThreadPost>) {
        _posts.value = posts
    }
}