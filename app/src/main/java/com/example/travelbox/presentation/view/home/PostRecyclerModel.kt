package com.example.travelbox.presentation.view.home

data class PostRecyclerModel(
    val image: Int,
    val id : String,
    val title: String

)


data class CommentRecyclerModel(

    val commentNickname : String?,    // user 아이디
    val commentId : Int,   // commentId
    val commentContent : String?,
    val commentVisible : String
)
