package com.example.infohub_telas.model

@Deprecated("Use RecuperarSenhaRequest")
typealias recuperarSenha = RecuperarSenhaRequest

data class RecuperarSenhaRequest(
    val email: String
)
