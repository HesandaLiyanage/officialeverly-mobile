package com.hess.everly.dto

import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("groupId") val groupId: Int,
    @SerializedName("groupName") val groupName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("coverImage") val coverImage: String?
)
