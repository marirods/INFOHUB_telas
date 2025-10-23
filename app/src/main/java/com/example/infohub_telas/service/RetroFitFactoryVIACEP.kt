package com.example.infohub_telas.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroFitFactoryVIACEP {

    private val BASE_URL_USER = "http://seu-servidor-api.com/"
    private val BASE_URL_VIACEP = "https://viacep.com.br/ws/"

    // 🔹 Configuração do OkHttpClient com timeouts
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)  // 🔹 Adicione o client aqui
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInfoHub_UserService(): InfoHub_User {
        return getRetrofit(BASE_URL_USER).create(InfoHub_User::class.java)
    }

    fun getViaCepService(): ViaCepApi {
        return getRetrofit(BASE_URL_VIACEP).create(ViaCepApi::class.java)
    }
}