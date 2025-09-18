package com.example.infohub_telas.service


import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InfoHub_User {

    @POST("/usuarios/cadastro")
    fun cadastrarUsuario(@Body usuario: LoginUsuario): Call<Usuario>

    @POST("/login")
    fun logarUsuario(@Body login : LoginUsuario): Call<LoginUsuario>

}