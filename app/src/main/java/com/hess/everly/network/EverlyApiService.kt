package com.hess.everly.network

import com.hess.everly.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EverlyApiService {
    
    @POST("api/login")
    suspend fun login(@Body request: AuthLoginRequest): Response<AuthLoginResponse>
    
    @GET("api/feed")
    suspend fun getFeed(): Response<List<FeedPostDTO>>

    @GET("api/memories")
    suspend fun getMemories(): Response<List<MemoryDTO>>

    @GET("api/journals")
    suspend fun getJournals(): Response<List<JournalDTO>>

    @GET("api/events")
    suspend fun getEvents(): Response<EventsResponse>

    @GET("api/autographs")
    suspend fun getAutographs(): Response<List<AutographDTO>>

    @GET("api/groups")
    suspend fun getGroups(): Response<List<GroupDTO>>

    @POST("api/vault/verify")
    suspend fun verifyVault(@Body req: VaultVerifyRequest): Response<VaultVerifyResponse>
}
