package com.example.infohub_telas.model


import java.util.Date

data class LoginUsuario(
    val email: String,
    val senha: String,
)

data class LoginResponse(
    val status: Boolean,
    val status_code: Int,
    val token: String,
    val usuario: UsuarioResponse
)

data class UsuarioResponse(
    val id: Int,
    val nome: String,
    val email: String,
    val perfil: String
)



