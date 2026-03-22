package com.hess.everly.network

import com.hess.everly.dto.AuthLoginRequest
import com.hess.everly.dto.AuthLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EverlyApiService {
    
    @POST("api/login")
    suspend fun login(@Body request: AuthLoginRequest): Response<AuthLoginResponse>
    
}
