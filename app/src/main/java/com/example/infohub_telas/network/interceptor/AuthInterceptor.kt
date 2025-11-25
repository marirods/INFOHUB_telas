package com.example.infohub_telas.network.interceptor

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor para adicionar automaticamente o token JWT nas requisições
 */
class AuthInterceptor(context: Context) : Interceptor {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Obter token salvo
        val token = sharedPreferences.getString("jwt_token", null)

        val newRequest = if (token != null) {
            // Preservar todos os headers existentes e adicionar Authorization
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}
