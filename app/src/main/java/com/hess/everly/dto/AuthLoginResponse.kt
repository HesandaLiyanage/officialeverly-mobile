package com.hess.everly.dto

data class AuthLoginResponse(
    val success: Boolean,
    val errorMessage: String?,
    val redirectUrl: String?
)
