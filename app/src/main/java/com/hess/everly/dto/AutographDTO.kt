package com.hess.everly.dto

import com.google.gson.annotations.SerializedName

data class AutographDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("targetName") val targetName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("createdAt") val createdAt: String?
)
