package com.example.infohub_telas.service


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
    fun logarUsuario(@Body login : LoginUsuario): Call<LoginUsuario>

}