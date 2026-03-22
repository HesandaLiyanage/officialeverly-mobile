package com.hess.everly.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // 10.0.2.2 is the Android Emulator's loopback to the host machine's localhost
    private const val BASE_URL = "http://10.0.2.2:8080/officialeverly/"

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(SessionCookieJar()) // Keeps the user logged in across requests
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // See raw JSON in Logcat
        })
        .build()

    val apiService: EverlyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EverlyApiService::class.java)
    }
}
