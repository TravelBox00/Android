package com.example.travelbox.data.repository.my

import com.google.gson.annotations.SerializedName

//팔로워 수 조회
data class Follower(
    val followerUserId: Int,
    val followerUserTag: String
) {
    override fun toString(): String {
        return "Follower(userId=$followerUserId, userTag=$followerUserTag)"
    }
}

// 팔로워 목록 API 응답 모델
data class FollowerResponse(
    @SerializedName("followers") val followers: List<FollowerItem>
)

// 개별 팔로워 데이터 모델
data class FollowerItem(
    @SerializedName("followerUserId") val followerUserId: Int,  // 🔹 Swagger에 맞춰 추가
    @SerializedName("userTag") val userId: String,
    @SerializedName("userProfileImage") val profileImageUrl: String,
    @SerializedName("isFollowing") var isFollowing: Boolean,
    val nickname: String = ""  // 기본값 제공
) {
    override fun toString(): String {
        return "FollowerItem(followerUserId=$followerUserId, userId=$userId, profileImageUrl=$profileImageUrl, isFollowing=$isFollowing, nickname=$nickname)"
    }
}


// 팔로잉 목록 API 응답 모델
data class FollowingResponse(
    @SerializedName("followings") val followings: List<FollowingItem>
)

// 개별 팔로잉 데이터 모델
data class FollowingItem(
    @SerializedName("followingUserId") val followingUserId: Int,  // 🔹 Swagger에 맞춰 추가
    @SerializedName("userTag") val userId: String,
    @SerializedName("userProfileImage") val profileImageUrl: String,
    @SerializedName("isFollowedByThem") var isFollowedByThem: Boolean
) {
    override fun toString(): String {
        return "FollowingItem(followingUserId=$followingUserId, userId=$userId, profileImageUrl=$profileImageUrl, isFollowedByThem=$isFollowedByThem)"
    }
}

//게시물
data class Post(
    val threadId: Int,
    val postContent: String,
    val imageUrl: String
)

data class ThreadResponse(
    val posts: List<Post>,
    val totalResults: Int
)

//나의 댓글
data class CommentResponse(
    val isSuccess: Boolean,
    val result: List<Comment>
)

data class Comment(
    val commentId: Int,
    val commentContent: String,
    val commentDate: String,
    val postContent: String,
    val postOwnerNickname: String
)


// 여행 기록 데이터 모델
data class StoryResponse(
    val result: List<ThreadData>,
    val cursor: String?,
    val isSuccess: Boolean
)

data class ThreadData(
    val postContent: String,
    val postDate: String,
    val threadId: Int,
    val imageURL: String
)

//사용자 정보 불러오기
data class UserInfoResponse(
    @SerializedName("result") val userInfo: UserInfo,
    @SerializedName("isSuccess") val isSuccess: Boolean
)

data class UserInfo(
    @SerializedName("userTag") val userTag: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userProfileImage") val userProfileImage: String,
    @SerializedName("email") val email: String?
)