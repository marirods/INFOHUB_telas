package com.example.infohub_telas.service


import com.example.infohub_telas.model.AtualizarSenhaRequest
import com.example.infohub_telas.model.AtualizarSenhaResponse
import com.example.infohub_telas.model.ChatRequest
import com.example.infohub_telas.model.ChatResponse
import com.example.infohub_telas.model.LoginResponse
import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.model.Usuario
import com.example.infohub_telas.model.ValidarCodigoRequest
import com.example.infohub_telas.model.ValidarCodigoResponse
import com.example.infohub_telas.model.recuperarSenha
import com.example.infohub_telas.model.recuperarSenhaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface InfoHub_User {

    @POST("usuarios/cadastro")
    @Headers("Content-Type: application/json")
    fun cadastrarUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("login")
    @Headers("Content-Type: application/json")
    fun logarUsuario(@Body login : LoginUsuario): Call<LoginResponse>



    @POST("recuperar-senha")
    @Headers("Content-Type: application/json")
    fun recuperarSenha(@Body email: recuperarSenha): Call<recuperarSenhaResponse>

    @POST("auth/login")
    fun loginUsuarios(@Body loginReq: LoginUsuario): Call<LoginResponse>


    @POST("validar-codigo")
    @Headers("Content-Type: application/json")
    fun validarCodigo(@Body request: ValidarCodigoRequest): Call<ValidarCodigoResponse>


    @POST("redefinir-senha")
    @Headers("Content-Type: application/json")
    fun atualizarSenha(@Body request: AtualizarSenhaRequest): Call<AtualizarSenhaResponse>

    @POST("chat")
    fun enviarMensagemChat(
        @Header("Authorization") token: String,
        @Body request: ChatRequest
    ): Call<ChatResponse>
}