package com.hess.everly.dto

import com.google.gson.annotations.SerializedName

data class JournalDTO(
    @SerializedName("journalId") val journalId: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("createdAt") val createdAt: String?
)
