package com.example.openinapp.network

import com.example.openinapp.data.Dashboard
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL =
    "https://api.inopenapp.com/api/v1/"

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DashboardApiService {
    @GET("dashboardNew")
    suspend fun getDashboardData(@Header("Authorization") authHeader: String): Dashboard
}

object dashboardApi {
    val retrofitService: DashboardApiService by lazy {
        retrofit.create(DashboardApiService::class.java)
    }
}