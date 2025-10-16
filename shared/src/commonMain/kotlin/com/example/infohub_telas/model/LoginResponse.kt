package com.example.infohub_telas.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: Boolean,
    val status_code: Int,
    val token: String,
    val usuario: UsuarioResponse
)

@Serializable
data class UsuarioResponse(
    val id: Int,
    val nome: String,
    val email: String,
    val perfil: String
)
