package com.hess.everly.dto

data class AuthLoginRequest(
    val username: String,
    val password: String,
    val rememberme: String? = null
)
