package com.example.infohub_telas.service

import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create




class RetrofitFactory {

        private val BASE_URL = "http://10.0.2.2:8080/v1/infohub/"


        private val retrofitFactory =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        fun getInfoHub_UserService(): InfoHub_User{
            return retrofitFactory.create(InfoHub_User::class.java)
        }


    private fun viaCepRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getViaCepService(): ViaCepApi {
       return viaCepRetrofit().create(ViaCepApi::class.java)

    }

    private fun brasilApiRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://brasilapi.com.br/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getBrasilApiService(): BrasilApi {
        return brasilApiRetrofit().create(BrasilApi::class.java)
    }


    }









