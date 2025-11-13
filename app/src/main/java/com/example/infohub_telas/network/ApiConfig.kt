package com.example.infohub_telas.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configuração centralizada da API do InfoHub
 * Base URL: http://localhost:8080/v1/infohub
 */
object ApiConfig {

    private const val BASE_URL = "http://localhost:8080/v1/infohub/"

    // Logging interceptor para debug
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP com configurações customizadas
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    // Instância do Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Factory methods para criar services
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    // Services específicos
    val authService: AuthService by lazy { createService(AuthService::class.java) }
    val userService: UserService by lazy { createService(UserService::class.java) }
    val infoCashService: InfoCashService by lazy { createService(InfoCashService::class.java) }
}
