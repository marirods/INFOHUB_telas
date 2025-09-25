package com.example.infohub_telas.service


import com.example.infohub_telas.model.LoginResponse
import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface InfoHub_User {

    @POST("usuarios/cadastro")
    @Headers("Content-Type: application/json")
    fun cadastrarUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("login")
    @Headers("Content-Type: application/json")
    fun logarUsuario(@Body login : LoginUsuario): Call<LoginResponse>

//
//
//        @POST("auth/recuperar-senha")
//        fun recuperarSenha(@Body email: String): Call<Usuario>
//
//        @POST("auth/login")
//        fun logarUsuario(@Body loginReq: LoginUsuario): Call<LoginResponse>


}