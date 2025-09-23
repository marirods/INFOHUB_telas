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

    }





