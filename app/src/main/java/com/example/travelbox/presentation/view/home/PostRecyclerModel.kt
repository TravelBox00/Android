package com.example.travelbox.presentation.view.home

data class PostRecyclerModel(
    val image: Int,
    val id : String,
    val title: String

)


data class CommentRecyclerModel(

    val commentId : String?,
    val content : String?
)
