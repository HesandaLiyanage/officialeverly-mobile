package com.hess.everly.dto

data class UserDTO(
    val userId: Int,
    val username: String,
    val fullName: String?,
    val profilePicPath: String?
)

data class FeedPostDTO(
    val postId: Int,
    val textContent: String,
    val mediaUrl: String?,
    val timestamp: String,
    val author: UserDTO
)

data class MemoryDTO(
    val memoryId: Int,
    val title: String,
    val description: String?,
    val date: String,
    val coverMediaUrl: String?,
    val isPublic: Boolean
)
