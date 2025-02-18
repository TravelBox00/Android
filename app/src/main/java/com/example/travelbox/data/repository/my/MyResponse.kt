package com.example.travelbox.data.repository.my

data class FollowerResponse(
    val followers: List<Follower>
)

//팔로워 수 조회
data class Follower(
    val followerUserId: Int,
    val followerUserTag: String
) {
    override fun toString(): String {
        return "Follower(userId=$followerUserId, userTag=$followerUserTag)"
    }
}

//팔로워 중인 사람 조회
data class FollowerItem(
    val profileImageUrl: String,
    val nickname: String,
    val userId: String,
    val isFollowing: Boolean
)

//팔로잉
data class Following(
    val followingUserId: Int,
    val followingUserTag: String
) {
    override fun toString(): String {
        return "Follower(userId=$followingUserId, userTag=$followingUserTag)"
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

// 사용자(회원) 정보 수정 여부
data class UserInfoResponse(
    val isSuccess: Boolean,
    val userInfo: UserInfo?
)
//사용자 정보
data class UserInfo(
    val userTag: String,
    val userNickname: String,
    val userName: String
)
