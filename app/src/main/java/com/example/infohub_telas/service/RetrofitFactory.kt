package com.example.infohub_telas.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

        private val BASE_URL = "http://10.0.2.2:8080/v1/infohub/"

        // Configuração do logging interceptor
        private val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("API_LOG", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY // Loga headers e body completo
        }

        // Cliente OkHttp com interceptor de logging
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        private val retrofitFactory =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        fun getInfoHub_UserService(): InfoHub_User{
            return retrofitFactory.create(InfoHub_User::class.java)
        }

        fun getInfoHub_EstabelecimentoService(): InfoHub_EstabelecimentoService{
            return retrofitFactory.create(InfoHub_EstabelecimentoService::class.java)
        }

        fun getInfoHub_ProdutoService(): InfoHub_ProdutoService{
            return retrofitFactory.create(InfoHub_ProdutoService::class.java)
        }


    private fun viaCepRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getViaCepService(): ViaCepApi {
       return viaCepRetrofit().create(ViaCepApi::class.java)

    }

    private fun brasilApiRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://brasilapi.com.br/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getBrasilApiService(): BrasilApi {
        return brasilApiRetrofit().create(BrasilApi::class.java)
    }


    }
