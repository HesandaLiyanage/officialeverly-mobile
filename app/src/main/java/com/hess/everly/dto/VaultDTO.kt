package com.hess.everly.dto

import com.google.gson.annotations.SerializedName

data class VaultVerifyRequest(
    @SerializedName("password") val password: String
)

data class VaultVerifyResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("setup_required") val setupRequired: Boolean?,
    @SerializedName("journals") val journals: List<JournalDTO>?,
    @SerializedName("error") val error: String?
)
